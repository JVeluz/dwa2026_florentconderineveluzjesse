package m1.dwa.cv.services;

import m1.dwa.cv.daos.UserDao;
import m1.dwa.cv.entities.User;

public class UserService {
    private UserDao userDao;

	public UserService(UserDao userDao) {
	    this.userDao = userDao;
	}

	public User getUser(int id) throws Exception {
        User user = userDao.find(id);
        if (user == null)
            throw new Exception("Utilisateur introuvable, userId invalide");
        return cleanUser(user);
    }

    public User[] getUsers() {
        User[] rawUsers = userDao.every();
        User[] Users = new User[rawUsers.length];
        for (int i = 0; i < Users.length; i++) {
            Users[i] = cleanUser(rawUsers[i]);
        }
        return rawUsers;
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
