package controllers;

import models.Account;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
  
  public static Result index() {
	  Account newAccount = new Account("group@test.com", "testname");
		newAccount.save();
		
		String newGroup="GROUP1";
		String newGroupDesc="FOR GROUP1";
		
		newAccount.createGroup(newGroup, newGroupDesc);
		Account searchAccount = Account.find.byId(newAccount
				.getAccountId());
		
	 
    return ok(index.render("Your new application is ready."));
  }
  
}