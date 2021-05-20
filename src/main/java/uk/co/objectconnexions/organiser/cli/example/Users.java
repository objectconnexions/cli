package uk.co.objectconnexions.organiser.cli.example;

import uk.co.objectconnexions.organiser.cli.AbstractCommand;
import uk.co.objectconnexions.organiser.cli.CommandContext;
import uk.co.objectconnexions.organiser.cli.Request;
import uk.co.objectconnexions.organiser.cli.Results;

public class Users extends AbstractCommand<MyApplicationRoot> {

	public Users() {
		super("users", false);
	}
	
	@Override
	public boolean appliesTo(CommandContext<?> context, Request request) {
		return !context.getTarget(MyApplicationRoot.class).getUsers().isEmpty();
	}
	
	
	@Override
	public void process(CommandContext<MyApplicationRoot> context, Request request, Results results) {
		results.appendLine("Users:-");
		for (User user : context.getTarget().getUsers()) {
			results.appendIndexedLine(user.getUserId() + " " + user.getName());
		}
	}

}
