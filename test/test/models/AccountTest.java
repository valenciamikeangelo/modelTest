package test.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import models.Account;
import models.AccountGroup;
import models.Comment;
import models.Event;
import models.Post;

import org.joda.time.DateTime;
import org.junit.Test;

import com.avaje.ebean.Ebean;

public class AccountTest {

	@Test
	public void accountCRUDTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Account newAccount = new Account("test@test.com", "testname");
				newAccount.save();
				assertNotNull(newAccount.getAccountId());
				Account searchAccount = Account.find.byId(newAccount
						.getAccountId());
				assertNotNull(searchAccount);
				assertEquals("testname", searchAccount.getName());
				searchAccount.setName("newname");
				Ebean.update(searchAccount);
				Account updateAccount = Account.find.byId(newAccount
						.getAccountId());
				assertEquals("newname", updateAccount.getName());
				Ebean.delete(searchAccount);
				Account delAccount = Account.find.byId(newAccount
						.getAccountId());
				assertNull(delAccount);
			}
		});
	}

	@Test
	public void addPostToAccountTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Account newAccount = new Account("test@test.com", "testname");
				newAccount.save();
				assertNotNull(newAccount.getAccountId());
				Account searchAccount = Account.find.byId(newAccount
						.getAccountId());
				assertNotNull(searchAccount);
				String title = "TITLE";
				String content = "CONTENT";
				searchAccount.addPost(title, content);
				Account searchAccountWithPost = Account.find.byId(newAccount
						.getAccountId());
				assertNotNull(searchAccountWithPost);
				assertEquals(1, searchAccountWithPost.posts.size());
				assertEquals(searchAccountWithPost,
						searchAccountWithPost.posts.get(0).author);
				assertEquals(title, searchAccountWithPost.posts.get(0).title);
				assertEquals(content,
						searchAccountWithPost.posts.get(0).content);

				String title2 = "TITLE2";
				String content2 = "CONTENT2";
				searchAccountWithPost.addPost(title2, content2);

				Account searchAccountWithPost2 = Account.find.byId(newAccount
						.getAccountId());
				assertNotNull(searchAccountWithPost2);
				assertEquals(2, searchAccountWithPost2.posts.size());
				assertEquals(searchAccountWithPost,
						searchAccountWithPost.posts.get(1).author);
				assertEquals(title2, searchAccountWithPost.posts.get(1).title);
				assertEquals(content2,
						searchAccountWithPost.posts.get(1).content);
				assertEquals(2, Post.getPostByAuthor(searchAccountWithPost2)
						.size());
				Ebean.delete(searchAccountWithPost2);
				Account delAccountWithPost2 = Account.find.byId(newAccount
						.getAccountId());
				assertNull(delAccountWithPost2);
				assertEquals(0, Post.getPostByAuthor(searchAccountWithPost2)
						.size());
			}
		});
	}

	@Test
	public void addCommentToPostTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Account postAuthor = new Account("postAuthor@test.com",
						"postAuthor");
				postAuthor.save();
				assertNotNull(postAuthor.getAccountId());
				Account searchPostAuthor = Account.find.byId(postAuthor
						.getAccountId());
				assertNotNull(searchPostAuthor);
				String title = "TITLE";
				String content = "CONTENT";
				searchPostAuthor.addPost(title, content);
				Account searchAccountWithPost = Account.find.byId(postAuthor
						.getAccountId());
				assertNotNull(searchAccountWithPost);
				assertEquals(1, searchAccountWithPost.posts.size());
				Post post = searchAccountWithPost.posts.get(0);

				Account postCommenter = new Account("postCommenter@test.com",
						"postCommenter");
				postCommenter.save();
				String commentContent = "COMMENTCONTENT";
				post.addComment(postCommenter, commentContent);
				post.save();

				Account searchAccountWithPostAndComment = Account.find
						.byId(postAuthor.getAccountId());

				assertNotNull(searchAccountWithPostAndComment);
				assertEquals(1, searchAccountWithPost.posts.size());
				Post commentPost = searchAccountWithPost.posts.get(0);
				assertEquals(1, commentPost.comments.size());
				Comment comment = commentPost.comments.get(0);
				assertNotNull(comment);
				assertEquals(postCommenter, comment.commenter);
				assertEquals(commentContent, comment.content);

				Ebean.delete(searchAccountWithPostAndComment);
				Account delAccountWithPost2 = Account.find.byId(postAuthor
						.getAccountId());
				assertNull(delAccountWithPost2);
				assertEquals(0,
						Post.getPostByAuthor(searchAccountWithPostAndComment)
								.size());
			}
		});
	}

	@Test
	public void addAccountConnectionTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {

				Account newAccount = new Account("test@test.com", "testname");
				newAccount.save();
				assertNotNull(newAccount.getAccountId());
				Account col1 = new Account("col1@test.com", "col1");
				col1.save();
				Account col2 = new Account("col2@test.com", "col2");
				col2.save();
				newAccount.addColleague(col1);
				newAccount.addColleague(col2);
				
				Account searchAccount = Account.find.byId(newAccount
						.getAccountId());
				assertNotNull(searchAccount);
				assertEquals(2, searchAccount.colleagues.size());

				Account col3 = new Account("col3@test.com", "col3");
				col3.save();
				searchAccount.addColleague(col3);
				searchAccount = Account.find.byId(newAccount.getAccountId());
				assertNotNull(searchAccount);
				assertEquals(3, searchAccount.colleagues.size());
				Ebean.delete(searchAccount);
				Account delAccountWithPost2 = Account.find.byId(searchAccount
						.getAccountId());
				assertNull(delAccountWithPost2);
			}

		});
	}
 
	@Test
	public void addAccountGroupTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				
				Account newAccount = new Account("group2@test.com", "testname");
				newAccount.save();
				
				String newGroup="GROUP1";
				String newGroupDesc="FOR GROUP1";
				
				newAccount.createGroup(newGroup, newGroupDesc);
				assertEquals(1, AccountGroup.getAccountGroupByAuthor(newAccount).size());
				
				AccountGroup newGroupPers=AccountGroup.getAccountGroupByAuthor(newAccount).get(0);
				assertNotNull(newGroupPers);
				assertEquals(newGroup, newGroupPers.groupName);
				assertEquals(newGroupDesc, newGroupPers.groupDescription);
				assertEquals(newAccount, newGroupPers.creator);
				Account searchAccount = Account.find.byId(newAccount
						.getAccountId());
				
				assertNotNull(searchAccount);
				assertNotNull(searchAccount.createdGroups);
				assertEquals(1, searchAccount.createdGroups.size());
				
			}
		});
	}
	
	@Test
	public void createEventTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				
				Account newAccount = new Account("event@test.com", "testname");
				newAccount.save();
				
				String title="EventTitle";
				String desc="Sample Event";
				DateTime  date = new DateTime();
				newAccount.createEvent(title,desc,date);
				
				assertEquals(1, Event.getEventsByAuthor(newAccount).size());
				
				Event newEventPers=Event.getEventsByAuthor(newAccount).get(0);
				assertNotNull(newEventPers);
				assertEquals(title, newEventPers.title);
				assertEquals(desc, newEventPers.description);
				assertEquals(date, newEventPers.eventDateAndTime);
				
				Account searchAccount = Account.find.byId(newAccount
						.getAccountId());
				
				assertNotNull(searchAccount);
				assertNotNull(searchAccount.createdEvents);
				assertEquals(1, searchAccount.createdEvents.size());
				
			}
		});
	}
}
