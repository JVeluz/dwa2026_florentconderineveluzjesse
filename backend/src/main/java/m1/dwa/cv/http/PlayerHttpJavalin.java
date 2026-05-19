package m1.dwa.cv.http;

import io.javalin.http.Context;
import m1.dwa.cv.entities.Bonus;
import m1.dwa.cv.services.BonusService;

public class PlayerHttpJavalin {
    private static class PostBonusBody {
        public int userId;
    }
    private static class PostPurchaseBonusBody {
        public int userId;
        public int bonusId;
    }

    private BonusService bonusService;

    public PlayerHttpJavalin(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    public void postBonus(Context context) throws Exception {
        PostBonusBody body = context.bodyAsClass(PostBonusBody.class);
        Bonus[] bonuses = bonusService.getBonuses(body.userId);
        context.status(200);
        context.json(bonuses);
    }

    public void postPurchaseBonus(Context context) throws Exception {
        PostPurchaseBonusBody body = context.bodyAsClass(PostPurchaseBonusBody.class);
        bonusService.buyBonus(body.userId, body.bonusId);
        context.status(200);
    }
}
