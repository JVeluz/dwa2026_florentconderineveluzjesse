package m1.dwa.cv.daos;

import m1.dwa.cv.entities.User;

public interface UserDao {
    public User find(int id);
    public User create(User user);
    public User update(User user);
    public User[] every();
    public User findByPseudo(String pseudo);
}
