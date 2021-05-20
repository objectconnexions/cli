package uk.co.objectconnexions.organiser.cli;

import java.time.LocalDate;
import java.time.LocalTime;

class TimeConverter implements OptionConversion<LocalTime> {

	@Override
	public LocalTime parse(String value) {
		return LocalTime.parse(value);
	}

}
