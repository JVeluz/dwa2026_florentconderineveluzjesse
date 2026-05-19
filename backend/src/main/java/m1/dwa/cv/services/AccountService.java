package m1.dwa.cv.services;

import m1.dwa.cv.daos.UserDao;
import m1.dwa.cv.entities.User;

public class AccountService {
    private UserDao userDao;

    public AccountService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String pseudo, String password) throws Exception {
        User user = userDao.findByPseudo(pseudo);
        if (user == null || !user.getPassword().equals(password))
            return null;
        return cleanUser(user);
    }

    public boolean register(String pseudo, String password) throws Exception {
        if (userDao.findByPseudo(pseudo) != null)
            return false;
        User user = new User();
        user.setPseudo(pseudo);
        user.setPassword(password);
        userDao.create(user);
        return true;
    }

    private User cleanUser(User user) {
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setPseudo(user.getPseudo());
        safeUser.setAge(user.getAge());
        safeUser.setCountryCode(user.getCountryCode());
        safeUser.setCredits(user.getCredits());
        safeUser.setPassword(null);
        return user;
    }
}
