package uk.co.objectconnexions.organiser.cli.command;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.CommandResults;
import uk.co.objectconnexions.organiser.cli.ContextStack;
import uk.co.objectconnexions.organiser.cli.Parameters;

public class Quit extends AbstractCommand {

	@Override
	public void process(ContextStack context, Parameters parameters, CommandResults result) {
		System.out.println("Goodbye");
		System.exit(0);
	}

}
