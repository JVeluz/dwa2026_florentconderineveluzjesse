package m1.dwa.cv.game.actions;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.game.events.GameEventSource;


public class ClickActionTest {

    private GameState gameState;
    private final GameEventSource gameEvent = new GameEventSource();

    @Before
    public void reset() {
        // Une grille sans pixels
        gameState = new GameState();
        // Un joueur avec 13 crédits
        gameState.addPlayer(0, new Player(13));
    }

    @Test
    public void clickingOk() {
        // 8 clicks par secondes
        Player player = gameState.getPlayer(0);
        new ClickAction(8).execute(gameState, gameEvent, player);
        assertTrue(player.getCredits() == 21);
    }

    @Test
    public void clickingTooFast() {
        // 200 clicks par secondes
        Player player = gameState.getPlayer(0);
        new ClickAction(200).execute(gameState, gameEvent, player);
        assertTrue(player.getCredits() == 13);
    }
}
