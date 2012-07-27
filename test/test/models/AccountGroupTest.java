package test.models;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static org.junit.Assert.*;
import models.Account;
import models.AccountGroup;

import org.junit.Test;

public class AccountGroupTest {

	
	@Test
	public void addAccountGroupTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				
				Account newAccount = new Account("group@test.com", "testname");
				newAccount.save();
				String newGroup="GROUP1";
				String newGroupDesc="FOR GROUP1";
				Account col1 = new Account("col1@test.com", "col1");
				col1.save();
				Account col2 = new Account("col2@test.com", "col2");
				col2.save();
				
				AccountGroup newAccGroup= new AccountGroup(newAccount,newGroup,newGroupDesc);
				newAccGroup.addMember(col1);
				assertNotNull(newAccGroup);
				AccountGroup snewAccGroup=AccountGroup.find.byId(newAccGroup.accountGroupId);
				assertNotNull(snewAccGroup);
				assertEquals(1, snewAccGroup.groupMembers.size());
				snewAccGroup.addMember(col2);
				snewAccGroup=AccountGroup.find.byId(newAccGroup.accountGroupId);
				assertNotNull(snewAccGroup);
				assertEquals(2, snewAccGroup.groupMembers.size());
			}
		});
	}
	
}
