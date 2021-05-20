package uk.co.objectconnexions.organiser.cli;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Request {

	private static Map<Class<?>, OptionConversion<?>> converters = new HashMap<>();
	
	static {
		add(String.class, new StringConverter());
		add(LocalDate.class, new DateConverter());
		add(LocalTime.class, new TimeConverter());
	}
	
	private static <T> void add(Class<T> cls, OptionConversion<T> conversion) {
		converters.put(cls, conversion);
	}
	
	private final Map<String, String> options = new HashMap<>(); 
	private final CommandContext<?> context;
	private final String firstToken;
	private String parameters;
	private boolean isCommand;

	public Request(CommandContext<?> context, String input) {
		this.context = context;
		parameters = input;
		firstToken = getNextToken();
	}
	
	public CommandContext<?> getContext() {
		return context;
	}
	
	public void setIsCommand() {
		isCommand = true;
	}

	public String getFirstToken() {
		return firstToken;
	}
	
	public String getFirstTokenOr(String defaultValue) {
		String token = getFirstToken();
		return token == null ? defaultValue : token;
	}

	public String getAll() {
		if (parameters.length() == 0) {
			return firstToken;
		} else if (isCommand) {
			return parameters;
		} else {
			return firstToken + " " + parameters;
		}
	}

	public String getAllOr(String defaultValue) {
		String token = getAll();
		return token == null ? defaultValue : token;
	}
	
	public String getNextToken() {
		String token;
		parameters = parameters.trim();
		int breakAt = parameters.indexOf(' ');
		if (breakAt == -1) {
			token = parameters;
			parameters = "";
			if (token.length() == 0) {
				token = null;
			}
		} else {
			token = parameters.substring(0, breakAt).trim();
			parameters = parameters.substring(breakAt + 1).trim();
		}
		return token;
	}
	
	public String getNextTokenOr(String defaultValue) {
		String token = getNextToken();
		return token == null ? defaultValue : token;
	}


	public <T> T getOption(Class<T> cls, String flag) {
		processOptions();
		
		// TODO parse and convert
		String input = options.get(flag);
		if (input == null) {
			return null;
		} else {
			return (T) converters.get(cls).parse(input);
		}
	}

	public <T> T getOptionOr(Class<T> cls, String flag, T defaultValue) {
		T value = getOption(cls, flag);
		return value == null ? defaultValue : value;
	}
	
	public String getRemainder() {
		return parameters;
	}

	public String getRemainderOr(String defaultValue) {
		return parameters == null || parameters.isEmpty() ? defaultValue : parameters;
	}
	
	
	private void processOptions() {
		while (parameters.length() != 0) {
			String token = getNextToken();
			if (token.startsWith("-")) {
				String flag = token.substring(1);
				String content = getNextToken();
				options.put(flag, content);
			} else {
				parameters = token + " " + parameters;
				break;
			}
		}
	}

}
