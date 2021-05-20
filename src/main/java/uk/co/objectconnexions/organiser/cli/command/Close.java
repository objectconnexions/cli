package uk.co.objectconnexions.organiser.cli.command;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.Results;
import uk.co.objectconnexions.organiser.cli.CommandContext;
import uk.co.objectconnexions.organiser.cli.Request;

public class Close extends AbstractCommand<ApplicationRoot> {

	public Close() {
	}
	
	public Close(String name) {
		super(name);
	}

	@Override
	public void process(CommandContext<ApplicationRoot> context, Request request, Results result) {
		request.getContext().close();
	}

	@Override
	public boolean appliesTo(CommandContext<?> context, Request request) {
		return request.getContext().getParent() != null;
	}
}
