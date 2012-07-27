package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
	
		
	 @ManyToMany(cascade={CascadeType.ALL})
	 @JoinTable(name="account_colleague",joinColumns={@JoinColumn(name="account_Id")},inverseJoinColumns={@JoinColumn(name="colleague_Id")})
	 public Set<Account> colleagues = new HashSet<Account>();
	 
	 @ManyToMany(targetEntity=models.AccountGroup.class, mappedBy="groupMembers",cascade=CascadeType.ALL)
	 public Set<AccountGroup> accountGroups = new HashSet<AccountGroup>();
	 
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
		this.refresh();
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
