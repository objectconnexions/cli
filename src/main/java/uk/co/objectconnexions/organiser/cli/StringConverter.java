package uk.co.objectconnexions.organiser.cli;

class StringConverter implements OptionConversion<String> {

	@Override
	public String parse(String value) {
		return value;
	}

}
