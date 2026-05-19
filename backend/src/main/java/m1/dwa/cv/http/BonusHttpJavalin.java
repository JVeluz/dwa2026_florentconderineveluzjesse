package m1.dwa.cv.http;

import io.javalin.http.Context;
import m1.dwa.cv.daos.BonusDao;

public class BonusHttpJavalin {
    private BonusDao bonusDao;

    public BonusHttpJavalin(BonusDao bonusDao) {
        this.bonusDao = bonusDao;
    }

	public void getBonuses(Context context) {
        context.json(bonusDao.every());
	}
}
