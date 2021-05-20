package uk.co.objectconnexions.organiser.cli.example;

import java.util.ArrayList;
import java.util.List;

import uk.co.objectconnexions.organiser.cli.command.ApplicationRoot;

public class MyApplicationRoot extends ApplicationRoot {

	private final List<User> users = new ArrayList<User>();
	
	@Override
	public String getName() {
		return "EXAMPLE APP";
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void addToUsers(User user) {
		users.add(user);
	}

	
}
