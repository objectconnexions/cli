package uk.co.objectconnexions.organiser.cli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActivityContextFactory {

	private static final Logger LOG = LoggerFactory.getLogger(ActivityContextFactory.class);

	private Map<String, Command> commands = new HashMap<>();
	
	public void addCommand(Command command) {
		String name = command.getName();
		if (commands.containsKey(name)) {
			throw new IllegalArgumentException("Command with name " + name + " already exists");
		}
		commands.put(name, command);
		LOG.debug("added command {} to {}", name, this);
	}
	
	public Command getCommand(String name) {
	//	parameters.setIsCommand();
		return commands.get(name.toLowerCase());
	}

	
	
	private List<String> names = new ArrayList<>();

	public void help(CommandResults result) {
		if (names.isEmpty()) {
			for (Command command : commands.values()) {
				names.add(command.getName().toUpperCase());
			}
			Collections.sort(names);			
		}
		for (String name : names) {
			result.appendLine(name);
		}
	}

	public void debug(CommandResults result) {
		for (Entry<String, Command> entry : commands.entrySet()) {
			result.appendLine("    " + entry.getValue().getName());
		}

		
	}

	protected abstract Class<? extends Object> getForClass();
	
}
