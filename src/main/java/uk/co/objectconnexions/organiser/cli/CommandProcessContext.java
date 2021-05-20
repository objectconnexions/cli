package uk.co.objectconnexions.organiser.cli;

public interface CommandProcessContext {
	
	abstract public void prompt(CommandResults result);

	abstract public boolean process(ContextStack context, Parameters parameters, CommandResults result);

}
