package uk.co.objectconnexions.organiser.cli;

public abstract class AbstractCommand implements Command {
	
	private final String name;
	
	public AbstractCommand() {
		String n = this.getClass().getName();
		int pos = n.lastIndexOf(".");
		if (pos > 0) {
			n = n.substring(pos + 1);
		}
		name = n.toLowerCase();
	}
	
	public AbstractCommand(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
