package uk.co.objectconnexions.organiser.cli.command;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.CommandResults;
import uk.co.objectconnexions.organiser.cli.ContextStack;
import uk.co.objectconnexions.organiser.cli.Parameters;

public class Info extends  AbstractCommand {

	
	public Info(String name) {
		super(name);
	}

	@Override
	public void process(ContextStack context, Parameters parameters, CommandResults result) {
		result.appendLine("Organiser, Object Connexions Ltd, 2019");
	}

}
