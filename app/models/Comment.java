package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Comment extends Model {
 
	@Id
	@GeneratedValue
	public Long commentId;
	
    public Account commenter;
    public Date postedAt;
     
  
    
    public String content;
    
    @ManyToOne
    public Post post;
    
    public Comment(Post post, Account commenter, String content) {
        this.post = post;
        this.commenter = commenter;
        this.content = content;
        this.postedAt = new Date();
    }
 
    
    public static Finder<Long,Comment> find = new Finder<Long,Comment>(
    		Long.class, Comment.class
    	  ); 
}
