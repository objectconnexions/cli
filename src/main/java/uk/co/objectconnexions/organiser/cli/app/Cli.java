package uk.co.objectconnexions.organiser.cli.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import uk.co.objectconnexions.organiser.cli.Command;
import uk.co.objectconnexions.organiser.cli.CommandContext;
import uk.co.objectconnexions.organiser.cli.CommandException;
import uk.co.objectconnexions.organiser.cli.CommandSet;
import uk.co.objectconnexions.organiser.cli.ContextManager;
import uk.co.objectconnexions.organiser.cli.Request;
import uk.co.objectconnexions.organiser.cli.Results;
import uk.co.objectconnexions.organiser.cli.command.ApplicationRoot;
import uk.co.objectconnexions.organiser.cli.command.Close;
import uk.co.objectconnexions.organiser.cli.command.Debug;
import uk.co.objectconnexions.organiser.cli.command.Help;
import uk.co.objectconnexions.organiser.cli.command.Info;
import uk.co.objectconnexions.organiser.cli.command.Quit;
import uk.co.objectconnexions.organiser.cli.command.Root;
import uk.co.objectconnexions.organiser.cli.command.ShowContext;
import uk.co.objectconnexions.organiser.cli.command.ShowError;

public class Cli {
	
	private final ContextManager contextManager = new ContextManager();
	private CommandSet<ApplicationRoot> rootCommands;
	private final Class<? extends ApplicationRoot> cls;
	
	public Cli(String info) {
		this(ApplicationRoot.class, info);
	}

	public Cli(Class<? extends ApplicationRoot> cls, String info) {
		this.cls = cls;
		rootCommands = createCommandSet(cls);
		
		rootCommands.addCommand(new Debug());
		rootCommands.addCommand(new Root());
		rootCommands.addCommand(new Close());
		
		rootCommands.addCommand(new ShowContext("context ctx ??"));
		rootCommands.addCommand(new ShowError());
		
		
		
		addCommand(cls, new Quit());
		addCommand(cls, new Help());
		addCommand(cls, new Info("info", info));
	}
	
	public <T> void addCommand(Class<? extends T> cls, Command<? extends T> command) {
		contextManager.addCommand(cls, command);
	}

	public CommandSet<ApplicationRoot> getRootCommands() {
		return rootCommands;
	}
	
	public <T> CommandSet<T> createCommandSet(Class<? extends T> cls) {
		CommandSet<T> commands = new CommandSet<>(cls);
		contextManager.add(cls, commands);
		return commands;
	}

	public void run() {
		try {
			ApplicationRoot root = cls.getConstructor().newInstance();
			run(root);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Failed to create root object", e);
		}
		
	}
	
	public void run(ApplicationRoot root) {
		CommandContext<ApplicationRoot> context = rootCommands.createContext(contextManager, null, root, root.getName());
		contextManager.initialise(context);
		
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
		Request request;
		request = new Request(contextManager.getActiveContext(), input);

		Results result = new Results();
		contextManager.process(request, result);
		return result.toString();
	}

}
