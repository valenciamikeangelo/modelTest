package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;

import play.db.ebean.Model;


@Entity
public class Account extends Model {

	@Id
	@GeneratedValue
	public Long accountId;
	
	public String email;
	
	public String name;
	
	@OneToMany(mappedBy="author",cascade=CascadeType.ALL)
	public List<Post> posts;
	
	@OneToMany(mappedBy="creator",cascade=CascadeType.ALL)
	public List<AccountGroup> createdGroups;
	
	
	@OneToMany(mappedBy="organizer",cascade=CascadeType.ALL)
	public List<Event> createdEvents;
		
	 @ManyToMany(cascade={CascadeType.ALL})
	 @JoinTable(name="account_colleague",joinColumns={@JoinColumn(name="account_Id")},inverseJoinColumns={@JoinColumn(name="colleague_Id")})
	 public Set<Account> colleagues = new HashSet<Account>();
	 
	 @ManyToMany(targetEntity=models.AccountGroup.class, mappedBy="groupMembers",cascade=CascadeType.ALL)
	 public Set<AccountGroup> accountGroups = new HashSet<AccountGroup>();
	 
	 @ManyToMany(targetEntity=models.Event.class, mappedBy="attendees",cascade=CascadeType.ALL)
	 public Set<Event> accountEvents = new HashSet<Event>();
	 
	public Account(){
		
	}
	
	public Account(String email,String name){
		this.email=email;
		this.name=name;
	}
	
	public void addPost(String title,String content){
		Post newPost= new Post(this,title,content);
		this.posts.add(newPost);
		this.save();
	}
	
	public void addColleague(Account colleague){
		this.colleagues.add(colleague);
		this.save();
		this.refresh();
	}
	
	public void createGroup(String groupName,String groupDescription){
		AccountGroup newGroup= new AccountGroup(this, groupName, groupDescription);
		newGroup.save();
		this.createdGroups.add(newGroup);
		this.save();
	}
	
	
	public void createEvent(String title,String description,DateTime eventDateAndTime){
		Event newEvent= new Event(this, title, description, eventDateAndTime);
		newEvent.save();
		this.createdEvents.add(newEvent);
		this.save();
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static Finder<Long,Account> find = new Finder<Long,Account>(
    		Long.class, Account.class
    	  );

	

}
