package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	
    @Autowired
	private static Facebook facebook;
  
  
  
  public static Result index() {
	   
	  List<Reference> friends = facebook.friendOperations().getFriends(); 
    return ok(index.render("Your new application is ready."));
  }
  
}