package uk.co.objectconnexions.organiser.cli;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class DateConverter implements OptionConversion<LocalDate> {

	@Override
	public LocalDate parse(String value) {
		try {
			return LocalDate.parse(value);
		} catch (DateTimeParseException e) {
			int offset = Integer.parseInt(value);
			return LocalDate.now().minusDays(offset);
		}
	}

}
