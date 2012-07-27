package test.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import models.Account;
import models.Event;

import org.joda.time.DateTime;
import org.junit.Test;

public class EventTest {

	@Test
	public void addAccountGroupTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Account newAccount = new Account("event@test.com", "testname");
				newAccount.save();
				
				String title="EventTitle";
				String desc="Sample Event";
				DateTime  date = new DateTime();
				newAccount.createEvent(title,desc,date);
				
				Account col1 = new Account("col1@test.com", "col1");
				col1.save();
				Account col2 = new Account("col2@test.com", "col2");
				col2.save();
				
				Event newEvent=Account.find.byId(newAccount.getAccountId()).createdEvents.get(0);
				assertNotNull(newEvent);
				newEvent.addAttendee(col1);
				assertEquals(1, Event.getEventsByAuthor(newAccount).size());
				newEvent=Event.getEventsByAuthor(newAccount).get(0);
				assertEquals(1, newEvent.attendees.size());
				newEvent.addAttendee(col2);
				newEvent=Event.getEventsByAuthor(newAccount).get(0);
				assertEquals(2, newEvent.attendees.size());
			}
		});
	}
	
	
}
