package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class AccountGroup extends Model{
	
	@Id
	@GeneratedValue
	public Long groupId;
	
	@ManyToOne
	public Account creator;
	
	public String groupName;
	
	public String groupDescription;
	
	
	 @ManyToMany(fetch=FetchType.EAGER)
	 public Set<Account> members = new HashSet<Account>();
	
	public AccountGroup(){
		
	}
	
	
	public AccountGroup(Account creator,String groupName,String groupDescription){
		this.creator=creator;
		this.groupName=groupName;
		this.groupDescription=groupDescription;
	}
	
	public static List<AccountGroup> getAccountGroupByAuthor(Account creator){
		return find.fetch("creator").where().eq("creator.accountId", creator.accountId).findList();
	}

	public static Finder<Long,AccountGroup> find = new Finder<Long,AccountGroup>(
    		Long.class, AccountGroup.class
    	  );
}
