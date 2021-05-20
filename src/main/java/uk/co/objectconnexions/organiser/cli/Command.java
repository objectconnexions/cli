package uk.co.objectconnexions.organiser.cli;

public interface Command<T> {

	boolean isInherited();
	
	boolean appliesTo(CommandContext<?> context, Request request);

	void process(CommandContext<T> context, Request request, Results results);

	String getName();
	
	String getDescription();
	
	String getHelp();

}
