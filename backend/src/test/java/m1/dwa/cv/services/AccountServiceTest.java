package m1.dwa.cv.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import m1.dwa.cv.daos.UserDaoMock;

public class AccountServiceTest {
    private UserDaoMock userDoa;
    private AccountService accountService;

    @Before
    public void reset() {
        userDoa = new UserDaoMock();
        accountService = new AccountService(userDoa);
    }

    @Test
    public void register() throws Exception {
        assertTrue(accountService.register("delkez", "123"));
    }

    @Test
    public void registerSamePseudo() throws Exception {
        accountService.register("delkez", "123");
        assertFalse(accountService.register("delkez", "456"));
    }

    @Test
    public void loginWithoutRegister() throws Exception {
		assertTrue(accountService.login("delkez","123") == null);
    }

    @Test
    public void loginWithRegister() throws Exception {
        accountService.register("delkez", "123");
        assertTrue(accountService.login("delkez","123") != null);
    }
}
