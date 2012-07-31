package controllers;


import play.api.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import beans.AutowiredBean;

public class Application extends Controller {
	

  
  
  
  public static Result index() {
	   
		AutowiredBean bean = Spring .getBeanOfType(AutowiredBean.class);

		if (bean == null)
		notFound("Unable to load MyBean from the Spring Context.");

		return ok("bean.getAutowiredProperty().getBeanName() = "
		+ bean.getAutowiredProperty().getBeanName());
	  
	 // return ok("");
  }
  
}