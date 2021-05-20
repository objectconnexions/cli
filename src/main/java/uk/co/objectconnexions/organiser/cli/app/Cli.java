package uk.co.objectconnexions.organiser.cli.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import uk.co.objectconnexions.organiser.cli.ActivityContext;
import uk.co.objectconnexions.organiser.cli.ActivityContextFactory;
import uk.co.objectconnexions.organiser.cli.CommandException;
import uk.co.objectconnexions.organiser.cli.CommandResults;
import uk.co.objectconnexions.organiser.cli.ContextStack;
import uk.co.objectconnexions.organiser.cli.Parameters;

public class Cli {
	
	private final ContextStack context = new ContextStack();
	ActivityContextFactory rootContext;

	public ActivityContextFactory getRootContext() {
		return rootContext;
	}
	

	public void addFactory(ActivityContextFactory factory) {
		context.add(factory);
		rootContext = factory;
		
	}
	public <T> void run(ActivityContext<T> initialContext) {
		context.initialise(initialContext);
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				System.out.print("> ");
				String input = br.readLine();
				
				try {
					String result = processInput(input);
					if (result != null) {
						System.out.print(result);
					}
				} catch(CommandException e) {
					System.err.println("WARNING: " + e.getMessage());
				} catch(RuntimeException e) {
					System.err.println("ERROR: " + e.getMessage());
					e.printStackTrace(System.err);
				}
				System.out.flush();
				System.err.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private String processInput(String input) {
		Parameters parameters;
		parameters = new Parameters(input);

		CommandResults result = new CommandResults();
		context.process(result, parameters);
		return result.toString();
	}

}
