package uk.co.objectconnexions.organiser.cli;

public interface Command {

	void process(ContextStack context, Parameters parameters, CommandResults result);

	String getName();

}
