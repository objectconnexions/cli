package uk.co.objectconnexions.organiser.cli;


public interface ActivityContext<T> {

	T getTarget();

	void process(ContextStack context, Parameters parameters, CommandResults result);

	void prompt(CommandResults result);

	String title();

	void help(CommandResults result);

}