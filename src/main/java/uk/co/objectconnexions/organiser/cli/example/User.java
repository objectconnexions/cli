package uk.co.objectconnexions.organiser.cli.example;

import java.util.ArrayList;
import java.util.List;

public class User {

	private final String userId;
	private String name;
	private List<Note> notes = new ArrayList<Note>();

	public User(String userId) {
		this.userId = userId;
		this.name = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}
	
	public List<Note> getNotes() {
		return notes;
	}

	public void addNote(Note note) {
		notes.add(note);
	}

}
