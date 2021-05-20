package uk.co.objectconnexions.organiser.cli.command;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.Results;
import uk.co.objectconnexions.organiser.cli.CommandContext;
import uk.co.objectconnexions.organiser.cli.Request;

public class ShowContext extends AbstractCommand<ApplicationRoot> {

	public ShowContext(String name) {
		super(name, "Show the curent context");
	}

	@Override
	public void process(CommandContext<ApplicationRoot> context, Request parameters, Results result) {
		context.show(result);
	}

}
