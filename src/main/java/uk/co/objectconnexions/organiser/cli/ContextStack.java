package uk.co.objectconnexions.organiser.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import uk.co.objectconnexions.organiser.cli.app.ActivityFactory;

public class ContextStack {
	private Map<Class<? extends ActivityContextFactory>, ActivityContextFactory> contextFactories = new HashMap<>();
	private Stack<ActivityContext<?>> context = new Stack<>();


	public void add(ActivityContextFactory basicProcess) {
		contextFactories.put(basicProcess.getClass(), basicProcess);
	}

	public <E extends ActivityContextFactory> E getContextFactory(Class<E> cls) {
		return (E) contextFactories.get(cls);
	}
	
	public <E> ActivityContext<E> getActivityContext(E object) {
		Class<? extends Object> cls = object.getClass();
		
		ActivityContextFactory factory2 = contextFactories.values().stream().
			filter(factory -> factory.getForClass().equals(cls)).
			findFirst().
			get();
		
		return ((ActivityFactory<E>) factory2).createContext(object, "????");
	}

	
	
	
	public <T> void initialise(ActivityContext<T> root) {
		context.push(root);
	}
	
	public ActivityContext<?> getCurrent() {
		return context.peek();
	}
	
	public void close() {
		if (context.size() == 1) {
			throw new CommandException("can't close context, no more layers in stack");
		}
		context.pop();
	}
	
	public <T> void extend(ActivityContext<?> newContext) {
		context.push(newContext);
	}

	public String process(CommandResults result, Parameters parameters) {
		getCurrent().process(this, parameters, result);
		getCurrent().prompt(result);
		return result.toString();
	}

	public <T> T getTarget(Class<T> cls) {
		Object target = context.stream().filter(c -> c.getTarget().getClass() == cls).findFirst().get().getTarget();
		if (cls.isAssignableFrom(target.getClass())) {
			return (T) target;
		} else {
			throw new RuntimeException(target + " is not an instance of " + cls.getName());
		}
	}
	
	public Object getTarget() {
		return getCurrent().getTarget();
	}

	/**
	 * Return to the root element
	 * */
	public void root() {
		ActivityContext<?> top;
		do {
			top = context.pop();
		} while (!context.isEmpty());
		context.push(top);
	}

	public void show(CommandResults result) {
		for (ActivityContext<?> element : context) {
			result.append(" |", element.title());
		}
		result.appendLine("");
	}

	public void debug(CommandResults result) {
		result.appendLine("Factories");
		for (Entry<Class<? extends ActivityContextFactory>, ActivityContextFactory> entry : contextFactories.entrySet()) {
			result.append("  ");
			result.appendLine(entry.getKey().getName());
			result.appendLine("  (for " + entry.getValue().getForClass().getName() + ")");
			
			entry.getValue().debug(result);
		}
		
		result.appendLine("Stack");
		for (ActivityContext<?> activityContext : context) {
			result.append("  ");
			result.appendLine(activityContext + " -> " + activityContext.getTarget());
		}
	}

}
