package uk.co.objectconnexions.organiser.cli;

public class Results {
	private static final String NEWLINE = System.lineSeparator();
	
	private StringBuffer buffer = new StringBuffer();
	private int lineCount = 1;
	private String type = null;

	public void appendIndexedLine(String text) {
		appendIndexed(text);
		appendTo(NEWLINE);
	}

	public void appendLine(String text) {
		appendTo(prefix(text));
		appendTo(NEWLINE);
	}

	public void appendIndexed(String text) {
		appendTo("[" + lineCount + "] " + prefix(text));
		lineCount++;
	}

	public void append(String text) {
		appendTo(prefix(text));
	}
	
	public void append(String joiner, String text) {
		if (text != null && text.trim().length() > 0) {
			if (buffer.length() == 0) {
				appendTo(text);
			} else {
				appendTo(joiner + " " + text);
			}
		}
	}
	
	public void appendLine(String joiner, String text) {
		append(joiner, text);
		appendTo(NEWLINE);
	}

	private void appendTo(String text) {
		buffer.append(text);
	}

	@Override
	public String toString() {
		return buffer.toString();
	}

	public void setType(String type) {
		this.type = type;
	}
	
	private String prefix(String text) {
		if (type != null) {
			String spaces = "                                    ";
			return type + ": " + spaces.substring(20 + type.length() + 2) + text;
		} else {
			return text;
		}
	}
	
	public void debug(String text) {
		appendLine("[" + text + "]");
		
	}

}
