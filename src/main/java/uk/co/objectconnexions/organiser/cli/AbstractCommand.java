package uk.co.objectconnexions.organiser.cli;

public abstract class AbstractCommand<T> implements Command<T> {
	
	private final String name;
	private final String description;
	private final String help;
	private boolean isInherited;
	
	public AbstractCommand() {
		String n = this.getClass().getName();
		int pos = n.lastIndexOf(".");
		if (pos > 0) {
			n = n.substring(pos + 1);
		}
		name = n.toLowerCase();
		description = null;
		help = null;
		isInherited = true;
	}
	
	public AbstractCommand(String name) {
		this(name, null, null, true);
	}
	
	public AbstractCommand(String name, boolean isInherited) {
		this(name, null, null, isInherited);
	}
	
	public AbstractCommand(String name, String description) {
		this(name, description, null, true);
	}	
	
	public AbstractCommand(String name, String description, boolean isInherited) {
		this(name, description, null, isInherited);
	}	
	
	public AbstractCommand(String name, String description, String help) {
		this(name, description, help, true);
	}
	
	public AbstractCommand(String name, String description, String help, boolean isInherited) {
		this.name = name.toLowerCase();
		this.description = description;
		this.help = help;
		this.isInherited = isInherited;
	}
	
	@Override
	public boolean isInherited() {
		return isInherited;
	}

	protected void setInherited(boolean isInherited) {
		this.isInherited = isInherited;
	}
	
	@Override
	public boolean appliesTo(CommandContext<?> context, Request request) {
		return true;
	}	

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getHelp() {
		return help;
	}
}
