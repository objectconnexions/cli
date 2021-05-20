package uk.co.objectconnexions.organiser.cli;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Parameters {

	private static Map<Class<?>, OptionConversion<?>> converters = new HashMap<>();
	
	static {
		add(String.class, new StringConverter());
		add(LocalDate.class, new DateConverter());
		add(LocalTime.class, new TimeConverter());
	}
	
	private static <T> void add(Class<T> cls, OptionConversion<T> conversion) {
		converters.put(cls, conversion);
	}
	
	
	private Map<String, String> options = new HashMap<>(); 
	
	private String firstToken;
	private String parameterString;
	private boolean isCommand;

	public Parameters(String input) {
		parameterString = input;
		firstToken = getNextToken();
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
		if (parameterString.length() == 0) {
			return firstToken;
		} else if (isCommand) {
			return parameterString;
		} else {
			return firstToken + " " + parameterString;
		}
	}

	public String getAllOr(String defaultValue) {
		String token = getAll();
		return token == null ? defaultValue : token;
	}
	
	public String getNextToken() {
		String token;
		parameterString = parameterString.trim();
		int breakAt = parameterString.indexOf(' ');
		if (breakAt == -1) {
			token = parameterString;
			parameterString = "";
			if (token.length() == 0) {
				token = null;
			}
		} else {
			token = parameterString.substring(0, breakAt).trim();
			parameterString = parameterString.substring(breakAt + 1).trim();
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
		return parameterString;
	}

	public String getRemainderOr(String defaultValue) {
		return parameterString == null ? defaultValue : parameterString;
	}
	
	
	private void processOptions() {
		while (parameterString.length() != 0) {
			String token = getNextToken();
			if (token.startsWith("-")) {
				String flag = token.substring(1);
				String content = getNextToken();
				options.put(flag, content);
			} else {
				parameterString = token + " " + parameterString;
				break;
			}
		}
	}
	
	
/*
	public boolean hasNoParameters() {
		return parameterString.length() == 0;
	}
*/

}
