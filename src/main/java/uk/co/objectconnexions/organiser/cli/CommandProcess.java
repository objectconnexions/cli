package uk.co.objectconnexions.organiser.cli;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandProcess {
	private Map<String, Command> commands = new HashMap<>();

	protected void addCommand(Command command) {
		String name = command.getName();
		if (commands.containsKey(name)) {
			throw new IllegalArgumentException("Command with name " + name + " already exists");
		}
		commands.put(name, command);
	}

}
