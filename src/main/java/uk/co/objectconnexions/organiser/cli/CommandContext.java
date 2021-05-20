package uk.co.objectconnexions.organiser.cli;


public interface CommandContext<T> {
	
	CommandContext<?> getParent();

	/**
	 * Process the contained in the request and save the responses to the result.
	 * Returns true if the requests was processed.
	 */
	boolean process(CommandContext<T> context, Request request, Results results);

	void prompt(Results results);

	String title();
	
	T getTarget();

	<S> S getTarget(Class<S> class1);

	void close();

	void debug(Results result);

	void show(Results result);
	
	public <S> void extend(S object, String name);

	public ContextManager getContextManager();

	CommandSet<?> getCommandSet();
	
}