package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;



@Entity
public class Post extends Model {

	@Id
	@GeneratedValue
	public Long postId;

	public String title;
	public Date postedAt;
	public String content;

	@ManyToOne
	public Account author;
	
	@OneToMany(mappedBy="post",cascade=CascadeType.ALL)
	public List<Comment> comments;

	public Post(Account author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
	}

	
	public void addComment(Account commenter, String content){
		Comment newComment = new Comment(this,commenter,content);
		this.comments.add(newComment);
		this.save();
	}
	
	public static List<Post> getPostByAuthor(Account author){
		return find.fetch("author").where().eq("author.accountId", author.accountId).findList();
	}
	
	public static Finder<Long,Post> find = new Finder<Long,Post>(
    		Long.class, Post.class
    	  ); 
	
}
