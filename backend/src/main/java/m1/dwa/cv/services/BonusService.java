package m1.dwa.cv.services;

import m1.dwa.cv.daos.BonusDao;
import m1.dwa.cv.daos.UserDao;
import m1.dwa.cv.entities.Bonus;
import m1.dwa.cv.entities.User;

public class BonusService {

    private BonusDao bonusDao;
    private UserDao userDao;

    public BonusService(BonusDao bonusDao, UserDao userDao) {
        this.bonusDao = bonusDao;
        this.userDao = userDao;
    }

    public Bonus[] getBonuses(int userId) throws Exception {
        User user = userDao.find(userId);
        if (user == null)
            throw new Exception("Utilisateur introuvable, userId invalide");
        return user.getBonuses();
    }

    public void buyBonus(int userId, int bonusId) throws Exception {
        User user = userDao.find(userId);
        Bonus bonus = bonusDao.find(bonusId);

        if (user == null)
            throw new Exception("Utilisateur introuvable, userId invalide");
        if (user.hasBonus(bonus))
            throw new Exception("le joueur possède déjà ce bonus");
        if (user.getCredits() < bonus.getPrice())
            throw new Exception("Le joueur n'a pas assez de crédits");

        int price = bonus.getPrice();
        user.removeCredits(price);
        user.addBonus(bonus);
        userDao.update(user);
    }
}
