package uk.co.objectconnexions.organiser.cli.command;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.CommandException;
import uk.co.objectconnexions.organiser.cli.Results;
import uk.co.objectconnexions.organiser.cli.CommandContext;
import uk.co.objectconnexions.organiser.cli.Request;

public class ShowError extends AbstractCommand<ApplicationRoot> {

	public ShowError() {
		setInherited(false);
	}

	@Override
	public void process(CommandContext<ApplicationRoot> context, Request parameters, Results result) {
		throw new CommandException("this is your error!");
	}

}
