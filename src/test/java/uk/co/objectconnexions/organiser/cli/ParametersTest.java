package uk.co.objectconnexions.organiser.cli;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ParametersTest {

	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void tokensForCommandWithParameters() {
		Parameters p = new Parameters("test action one");
		p.setIsCommand();
		
		assertThat(p.getFirstToken(), is("test"));
		assertThat(p.getNextToken(), is(equalTo("action")));
		assertThat(p.getNextToken(), is(equalTo("one")));
	}

	@Test
	public void stringForCommandWithParameters() {
		Parameters p = new Parameters("test action one");
		p.setIsCommand();
		
		assertThat(p.getFirstToken(), is(equalTo("test")));
		assertThat(p.getAll(), is(equalTo("action one")));
	}

	@Test
	public void noTokensForCommandWithNoParameters() {
		Parameters p = new Parameters("list");
		
		assertThat(p.getFirstToken(), is(equalTo("list")));
		assertThat(p.getNextToken(), is(nullValue()));
	}

	@Test
	public void stringForCommandWithNoParameters() {
		Parameters p = new Parameters("list");
		
		assertThat(p.getFirstToken(), is(equalTo("list")));
		//assertThat(p.get(0), is(equalTo("list")));
		assertThat(p.getAll(), is(equalTo("list")));
	}

	@Test
	public void tokensAreTrimmed() {
		Parameters p = new Parameters("  list  everything  else ");
		
		assertThat(p.getFirstToken(), is(equalTo("list")));
		assertThat(p.getNextToken(), is(equalTo("everything")));
		assertThat(p.getNextToken(), is(equalTo("else")));
	}

	@Disabled
	@Test
	public void stringIsTrimmed() {
		Parameters p = new Parameters("  list  everything  else ");
		
		assertThat(p.getAll(), is(equalTo("list everything else")));
	}

	
	
	
	@Test
	void selectOption() throws Exception {
		Parameters p = new Parameters("test -a first -b second tail entries");	
		
		assertThat(p.getOption(String.class, "b"), is(equalTo("second")));
		assertThat(p.getOption(String.class, "a"), is(equalTo("first")));
		assertThat(p.getOption(String.class, "c"), is(nullValue()));
		assertThat(p.getRemainderOr(null), is("tail entries"));
	}
	
	@Test
	void dateNoOption() throws Exception {
		Parameters p = new Parameters("test -a 2021-01-01 -b 2021-03-19 tail entries");	
		
		assertThat(p.getOption(LocalDate.class, "e"), is(nullValue()));
		assertThat(p.getOption(LocalDate.class, "f"), is(nullValue()));
	}
	
	@Test
	void dateOption() throws Exception {
		Parameters p = new Parameters("test -a 2021-01-01 -b 2021-03-19 tail entries");	
		
		assertThat(p.getOption(LocalDate.class, "b"), is(equalTo(LocalDate.of(2021, 3, 19))));
		assertThat(p.getOption(LocalDate.class, "a"), is(equalTo(LocalDate.of(2021, 1, 1))));
	}
	
	@Test
	void timeOption() throws Exception {
	Parameters p = new Parameters("test -a first -b 15:45 tail entries");	
	
	assertThat(p.getOption(LocalTime.class, "b"), is(equalTo(LocalTime.of(15, 45, 0))));
	}

}
