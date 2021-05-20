package uk.co.objectconnexions.organiser.cli.app;

import uk.co.objectconnexions.organiser.cli.ActivityContext;
import uk.co.objectconnexions.organiser.cli.ActivityContextFactory;
import uk.co.objectconnexions.organiser.cli.Command;
import uk.co.objectconnexions.organiser.cli.CommandException;
import uk.co.objectconnexions.organiser.cli.CommandResults;
import uk.co.objectconnexions.organiser.cli.ContextStack;
import uk.co.objectconnexions.organiser.cli.Parameters;

public class ActivityFactory<T> extends ActivityContextFactory {
	
	private Class<? extends Object> forClass;

	public ActivityFactory(Class<T> forClass) {
		this.forClass = forClass;
	}
	
	public ActivityContext<T> createContext(T object, String name) {
		return new ActCont<T>(object, this, name);
	}
	
	@Override
	protected Class<? extends Object> getForClass() {
		return forClass;
	}
	
	@Override
	public String toString() {
		return "ActivityContextFactory/" + forClass.getName();
	}

}

class ActCont<T> implements ActivityContext<T> {

	private final ActivityContextFactory factory;
	private final T target;
	private final String name;
	
	ActCont(T target, ActivityContextFactory factory, String name) {
		this.target = target;
		this.factory = factory;
		this.name = name;
	}

	@Override
	public T getTarget() {
		return target;
	}

	@Override
	public String title() {
		return "no passed through yet!"; //target.title();
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
		}
	}
	
	// TODO end common
	
	@Override
	public void prompt(CommandResults result) {
		result.setType(name);
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
