package uk.co.objectconnexions.organiser.cli.command;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.CommandResults;
import uk.co.objectconnexions.organiser.cli.ContextStack;
import uk.co.objectconnexions.organiser.cli.Parameters;

public class ShowContext extends AbstractCommand {

	public ShowContext(String name) {
		super(name);
	}

	@Override
	public void process(ContextStack context, Parameters parameters, CommandResults result) {
		context.show(result);
	}

}
