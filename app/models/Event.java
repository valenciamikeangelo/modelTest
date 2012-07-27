package models;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import play.db.ebean.Model;

@Entity
public class Event extends Model {

	@Id
	@GeneratedValue
	public Long eventId;
	
	public String title;
	public String description;
	public DateTime eventDateAndTime;
	
	
	@ManyToOne
	public Account organizer;
	
	 @ManyToMany(targetEntity=models.Account.class,cascade=CascadeType.ALL)
	 public Set<Account> attendees = new HashSet<Account>();
	
	public Event(){
		super();
	}
	
	
	public Event(Account organizer,String title,String description,DateTime  eventDateAndTime){
		this.organizer=organizer;
		this.eventDateAndTime=eventDateAndTime;
		this.title=title;
		this.description=description;
	}
	
	public void addAttendee(Account attendee){
		this.attendees.add(attendee);
		this.save();
	}
	
	public static List<Event> getEventsByAuthor(Account organizer){
		return find.fetch("organizer").where().eq("organizer.accountId", organizer.accountId).findList();
	}
	
	public static Finder<Long,Event> find = new Finder<Long,Event>(
    		Long.class, Event.class
    	  );
}
