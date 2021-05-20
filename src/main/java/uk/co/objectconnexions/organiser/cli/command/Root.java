package uk.co.objectconnexions.organiser.cli.command;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.Results;
import uk.co.objectconnexions.organiser.cli.CommandContext;
import uk.co.objectconnexions.organiser.cli.Request;

public class Root extends AbstractCommand<ApplicationRoot> {

	@Override
	public void process(CommandContext<ApplicationRoot> context, Request parameters, Results result) {
		closeAll(context.getContextManager().getActiveContext());
	}
	
	private void closeAll(CommandContext<?> context) {
		CommandContext<?> parent = context.getParent();
		if (parent != null) {
			context.close();
			closeAll(parent);
		}
	}

}
