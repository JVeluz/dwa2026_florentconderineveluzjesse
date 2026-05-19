package m1.dwa.cv.daos;

import java.util.HashMap;
import java.util.Map;

import m1.dwa.cv.entities.User;

public class UserDaoMock implements UserDao {
    private int count = 0;
    private Map<Integer, User> storage = new HashMap<>();

    public UserDaoMock() {
        User god = new User();
        god.setId(-42);
        god.setPseudo("dieu");
        god.setAge(-1);
        god.setPassword("ohgod");
        god.setCountryCode("Clouds");
        god.setCredits(9999999);
        storage.put(-42, god);
    }

    @Override
    public User find(int id) {
        if (!storage.containsKey(id))
            return null;
        return storage.get(id);
    }

    @Override
    public User create(User user) {
        user.setId(count);
        storage.put(count, user);
        count++;
        return user;
    }

    @Override
    public User update(User user) {
        return storage.put(user.getId(), user);
    }

    @Override
    public String toString() {
        return "users: " + storage.toString();
    }

    @Override
    public User[] every() {
        return storage.values().toArray(new User[0]);
    }

	@Override
	public User findByPseudo(String pseudo) {
        for (User user : storage.values()) {
            if (user.getPseudo().equals(pseudo))
                return user;
        }
        return null;
    }
}
