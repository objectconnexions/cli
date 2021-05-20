package uk.co.objectconnexions.organiser.cli;

public class GenericCommandContext<T> implements CommandContext<T> {

	private final ContextManager manager;
	private final CommandContext<?> parent;
	private final CommandSet<T> commands;
	private final T target;
	private final String name;
	
	GenericCommandContext(ContextManager manager, CommandContext<?> parent, CommandSet<T> commands, String name, T target) {
		this.manager = manager;
		this.parent = parent;
		this.target = target;
		this.commands = commands;
		this.name = name;
	}

	@Override
	public CommandContext<?> getParent() {
		return parent;
	}
	
	@Override
	public T getTarget() {
		return target;
	}

	@Override	
	public String title() {
		return name;
	}
	
	@Override
	public boolean process(CommandContext<T> context, Request request, Results results) {
		String command = request.getFirstToken();
		if (command == null) {
			return true;
		}
		
		boolean includeInherited = context == request.getContext();
		Command<T> cmd = commands.getCommand(command);
		if (cmd != null && (cmd.isInherited() || includeInherited) && cmd.appliesTo(context, request)) {
			request.setIsCommand();
			cmd.process(context, request, results);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void prompt(Results result) {
		result.setType(name);
	}

	public ContextManager getContextManager() {
		return manager;
	}	
	
	@Override
	public <S> S getTarget(Class<S> cls) {
		return manager.getTarget(cls);
	}

	@Override
	public void close() {
		manager.close();
	}

	@Override
	public void debug(Results result) {
		manager.debug(result);
	}

	@Override
	public void show(Results result) {
		manager.show(result);
	}
	
	public <S> void extend(S object, String name) {
		manager.extend(object, name);
	}
	
	public CommandSet<?> getCommandSet() {
		return commands;
	}
}
