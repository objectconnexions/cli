package uk.co.objectconnexions.organiser.cli;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandSet<T> {

	private static final Logger LOG = LoggerFactory.getLogger(CommandSet.class);

	private final Map<String, Command<? extends T>> commands = new HashMap<>();
	private Class<? extends T> forClass;
	
	public CommandSet(Class<? extends T> forClass) {
		this.forClass = forClass;
	}
	
	public CommandContext<T> createContext(ContextManager manager, CommandContext<?> parent, T object, String name) {
		return new GenericCommandContext<T>(manager, parent, this, name, object);
	}
	
	protected Class<? extends T> getForClass() {
		return forClass;
	}
	
	public String toString() {
		return "CommandSet/" + forClass.getName();
	}

	public void addCommand(Command<? extends T> command) {
		String name = command.getName();
		if (commands.containsKey(name)) {
			throw new IllegalArgumentException("Command with name " + name + " already exists, can't add");
		}
		
		for (String nm : name.split(" ")) {			
			commands.put(nm, command);
			LOG.debug("added command {} to {}", name, this);
		}
	}
	
	public void replaceCommand(Command<T> command) {
		String name = command.getName();
		if (!commands.containsKey(name)) {
			throw new IllegalArgumentException("Command with name " + name + " does not exists, can't replace");
		}
		commands.put(name, command);
		LOG.debug("replaced command {} in {}", name, this);
	}
	
	public Command<T> getCommand(String name) {
		return (Command<T>) commands.get(name.toLowerCase());
	}	

	public void debug(Results result) {
		for (Entry<String, Command<? extends T>> entry : commands.entrySet()) {
			result.appendLine("    " + entry.getValue().getName());
		}
	}

	public Collection<Command<? extends T>> getCommands() {
		return commands.values();
	}
	
}
