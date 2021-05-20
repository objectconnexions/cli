package uk.co.objectconnexions.organiser.cli;

public interface OptionConversion<T> {

	T parse(String value);
}
