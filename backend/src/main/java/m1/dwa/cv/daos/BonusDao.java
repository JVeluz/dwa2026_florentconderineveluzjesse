package m1.dwa.cv.daos;

import m1.dwa.cv.entities.Bonus;

public interface BonusDao {
    public Bonus find(int id);
    public Bonus[] every();
}
