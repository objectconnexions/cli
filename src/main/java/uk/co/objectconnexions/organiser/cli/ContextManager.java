package uk.co.objectconnexions.organiser.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Stack;

public class ContextManager {
	private Map<Class<?>, CommandSet<?>> commandSets = new HashMap<>();
	private Stack<CommandContext<?>> contexts = new Stack<>();


	public <T> void add(Class<? extends T> cls, CommandSet<T> commands) {
		commandSets.put(cls, commands);
	}

	public <E> CommandContext<E> createContext(ContextManager manager, E object, String name) {
		Class<? extends Object> cls = object.getClass();
		
		CommandSet<?> commands = commandSets.values().stream().
			filter(factory -> factory.getForClass().equals(cls)).
			findFirst().
			get();
		
		return ((CommandSet<E>) commands).createContext(manager, getActiveContext(), object, name);
	}

	public <T> void addCommand(Class<? extends T> cls, Command<? extends T> command) {
		CommandSet<T> set;
		if (commandSets.containsKey(cls)) {
			set = (CommandSet<T>) commandSets.get(cls);
		} else {
			set = new CommandSet<T>(cls);
			add(cls, set);
		}
		
		set.addCommand(command);
	}
	
	public <T> void initialise(CommandContext<T> root) {
		contexts.push(root);
	}
	
	public CommandContext<?> getActiveContext() {
		return contexts.peek();
	}
	
	public void close() {
		if (contexts.size() == 1) {
			throw new CommandException("can't close context, no more layers in stack");
		}
		contexts.pop();
	}
	
	public <T> void extend(CommandContext<?> newContext) {
		contexts.push(newContext);
	}

	public <T> void extend(T object, String name) {
		CommandContext<T> activityContext = createContext(this, object, name);
		extend(activityContext);
	}

	public String process(Request request, Results result) {
		if (process(getActiveContext(), request, result)) {
			getActiveContext().prompt(result);
			return result.toString();			
		} else {
			String command = request.getFirstToken();
			throw new CommandException("unrecognised command " + command.toUpperCase() + ". Use help to list commands");
		}
	}

	private <T> boolean process(CommandContext<T> context, Request request, Results result) {
		boolean complete = context.process(context, request, result);	
		if (complete) {
			return true;
		} else {
			CommandContext<?> parent = context.getParent();
			if (parent != null) {
				return process(parent, request, result);
			} else {
				return false;
			}
		}
	}

	public <T> T getTarget(Class<T> cls) {
		Optional<CommandContext<?>> find = contexts.stream().
				filter(c -> c.getTarget() != null && c.getTarget().getClass().isAssignableFrom(cls)).findFirst();
		if (find.isPresent()) {
			Object target = find.get().getTarget();
			return (T) target;
		} else {
			throw new RuntimeException("can't find instance of " + cls.getName());
		}
	}
	
	@Deprecated
	public Object getTarget() {
		return getActiveContext().getTarget();
	}

	/**
	 * Return to the root element
	 * */
	public void root() {
		CommandContext<?> top;
		do {
			top = contexts.pop();
		} while (!contexts.isEmpty());
		contexts.push(top);
	}

	public void show(Results result) {
		for (CommandContext<?> element : contexts) {
			result.append(" |", element.title());
		}
		result.appendLine("");
	}

	public void debug(Results result) {
		result.appendLine("Command Sets");
		for (Entry<Class<?>, CommandSet<?>> entry : commandSets.entrySet()) {
			result.append("  ");
			result.appendLine(entry.getKey().getSimpleName());
			result.appendLine("  (for " + entry.getValue().getForClass().getName() + ")");
			
			entry.getValue().debug(result);
		}
		
		result.appendLine("Stack");
		for (CommandContext<?> activityContext : contexts) {
			result.append("  ");
			result.appendLine(activityContext + " -> " + activityContext.getTarget());
		}
	}

}
