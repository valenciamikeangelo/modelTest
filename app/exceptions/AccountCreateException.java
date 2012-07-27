package exceptions;

public class AccountCreateException extends Exception {
	
	public AccountCreateException(){
		super();
	}

	
	public AccountCreateException(Exception e){
		super(e);
	}
}
