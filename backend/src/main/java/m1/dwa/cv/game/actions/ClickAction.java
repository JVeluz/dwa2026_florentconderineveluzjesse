package m1.dwa.cv.game.actions;

import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.game.GameConfig;
import m1.dwa.cv.game.events.GameEventSource;

public class ClickAction implements Action {
    private int clicks;

    public ClickAction(int clicks) {
        this.clicks = clicks;
    }

    @Override
    public void execute(GameState state, GameEventSource event, Player player) {
        int userId = player.getUserId();
        boolean isIllegalClicks = clicks > GameConfig.CLICKS_PER_SECOND_MAX;
        if (isIllegalClicks) {
            event.actionFailed(userId, "Le joueur clique trop vite, triche ?");
            return;
        }
        int credits = calculateCredits();
        player.addCredits(credits);
        player.addCreditsPerTick(credits);
        event.playerUpdated(userId, player);
    }

    private int calculateCredits() {
        return clicks;
    }
}
