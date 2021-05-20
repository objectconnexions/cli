package uk.co.objectconnexions.organiser.cli.app;

import uk.co.objectconnexions.organiser.cli.ActivityContext;
import uk.co.objectconnexions.organiser.cli.ActivityContextFactory;
import uk.co.objectconnexions.organiser.cli.Command;
import uk.co.objectconnexions.organiser.cli.CommandException;
import uk.co.objectconnexions.organiser.cli.CommandResults;
import uk.co.objectconnexions.organiser.cli.ContextStack;
import uk.co.objectconnexions.organiser.cli.Parameters;

public class GenericActivityContext<T> implements ActivityContext<T> {
	
	public static class Factory extends ActivityContextFactory {
		public <T> GenericActivityContext<T> createContext(T item) {
			return new GenericActivityContext<T>(item, this);
		}

		@Override
		protected Class<? extends Object> getForClass() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	
	
	private final ActivityContextFactory factory;
	private final T target;
	
	private GenericActivityContext(T target, ActivityContextFactory factory) {
		this.target = target;
		this.factory = factory;
	}

	@Override
	public T getTarget() {
		return target;
	}

	@Override
	public String title() {
		return "title??"; //target.title();
	}
	
	@Override
	public void process(ContextStack context, Parameters parameters, CommandResults result) {
		String command = parameters.getFirstToken();
		if (command == null) {
			return;
		}
		
		Command cmd = factory.getCommand(command);
		if (cmd == null) {
			throw new CommandException("unrecognised command " + command.toUpperCase() + ". Use help to list commands");
			
		} else {
			parameters.setIsCommand();
			cmd.process(context, parameters, result);
			// return " command is \"" + command + "\"\n parameters are \"" + parameters +
			// "\"\n results were: \n" + result;
		}
	}
	
	// TODO end common
	
	@Override
	public void prompt(CommandResults result) {
		result.setType("basic");
	}
//	
//	// TODO abstract method
//	void doProcess(ContextStack context, Parameters parameters, CommandResults result) {
//	
//	}

	
	@Override
	public void help(CommandResults result) {
		factory.help(result);
	}
		
}
