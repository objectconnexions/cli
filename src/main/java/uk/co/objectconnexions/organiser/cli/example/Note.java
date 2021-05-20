package uk.co.objectconnexions.organiser.cli.example;

import java.time.LocalDateTime;

public class Note {

	private LocalDateTime timeStamp;
	private String text;

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
