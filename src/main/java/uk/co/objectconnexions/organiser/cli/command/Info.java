package uk.co.objectconnexions.organiser.cli.command;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.Results;
import uk.co.objectconnexions.organiser.cli.CommandContext;
import uk.co.objectconnexions.organiser.cli.Request;

public class Info extends  AbstractCommand<ApplicationRoot> {

	private final String info;

	public Info(String name, String info) {
		super(name);
		this.info = info;
		setInherited(true);
	}

	@Override
	public void process(CommandContext<ApplicationRoot> context, Request parameters, Results result) {
		result.appendLine(info);
	}

}
