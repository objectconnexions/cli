package uk.co.objectconnexions.organiser.cli.command;

import java.util.ArrayList;
import java.util.List;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.Command;
import uk.co.objectconnexions.organiser.cli.CommandContext;
import uk.co.objectconnexions.organiser.cli.Request;
import uk.co.objectconnexions.organiser.cli.Results;

public class Help extends AbstractCommand<ApplicationRoot> {
	
	public Help() {
		super("Help Hlp ?", "Show this help text");
	}
	
	@Override
	public void process(CommandContext<ApplicationRoot> context, 
			Request request, Results result) {
		List<Command<?>> commands = new ArrayList<>();
		findCommands(commands, context.getContextManager().getActiveContext(), request);
		printCommands(result, commands);
	}

	private void findCommands(List<Command<?>> commands, CommandContext<?> context, Request request) {
		boolean includeInherited = context == request.getContext();
		for (Command<?> command : context.getCommandSet().getCommands()) {
			if ((command.isInherited() || includeInherited) && command.appliesTo(context, request)) {
				commands.add(command);
			}
		}		
		if (context.getParent() != null) {
			findCommands(commands, context.getParent(), request);
		}
	}
	
	private void printCommands(Results result, List<Command<?>> commands) {
		commands.stream().
		map(c -> c.getName().toUpperCase() +
				(c.getDescription() == null ? " " : (" - " + c.getDescription()))).
		distinct().
		sorted().
		forEach(name -> result.appendLine(name));
	}
}
