package m1.dwa.cv.game.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.game.events.GameEventSource;

public class PurchasePixelsActionTest {

    private GameState gameState;
    private final GameEventSource gameEvent = new GameEventSource();

    @Before
    public void reset() {
        // Une grille de 3x3
        List<Pixel> pixels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pixels.add(new Pixel(i, j, 5));
            }
        }
        gameState = new GameState();
        gameState.setPixel(pixels);
        gameState.setGridSize(3);
    }

    @Test
    public void enoughCredits() {
        // Un joueur avec 12 crédits,
        Player player = new Player(12);
        player.setUserId(0);
        gameState.addPlayer(0, player);
        Pixel pixel1 = gameState.getPixel(1, 0);
        Pixel pixel2 = gameState.getPixel(1, 1);
        // Voulait acheter ces pixels...
        int[] pixelsX = {1, 1};
        int[] pixelsY = {0, 1};
        new PaintPixelsAction(pixelsX, pixelsY, "red").execute(gameState, gameEvent, player);
        // Et il a réussit.
        assertTrue("Le joueur n'a pas était débité", player.getCredits() == 2);
        assertTrue("Le joueur n'a pas pris la possession du pixel1", pixel1.getOwnerId() == 0);
        assertTrue("Le joueur n'a pas pris la possession du pixel2", pixel2.getOwnerId() == 0);
    }

    @Test
    public void notEnoughCredits() {
        // Un joueur avec 9 crédits,
        Player player = new Player(9);
        player.setUserId(0);
        gameState.addPlayer(0, player);
        Pixel pixel1 = gameState.getPixel(1, 0);
        Pixel pixel2 = gameState.getPixel(1, 1);
        // Voulait acheter ces pixels...
        int[] pixelsX = {1, 1};
        int[] pixelsY = {0, 1};
        System.err.println("notEnoughCredits " + pixel1.getPrice());
        System.err.println("notEnoughCredits " + pixel2.getPrice());
        new PaintPixelsAction(pixelsX, pixelsY, "red").execute(gameState, gameEvent, player);
        // Et il a échoué.
        assertTrue("Le joueur a était débité", player.getCredits() == 9);
        assertFalse("Le joueur a pris possession du pixel1", pixel1.getOwnerId() == 0);
        assertFalse("Le joueur a pris possession du pixel2", pixel2.getOwnerId() == 0);
    }

    @Test
    public void stealOwnership() {
        // Ces pixels appartenait au joueur 1
        Pixel pixel1 = gameState.getPixel(1, 0);
        Pixel pixel2 = gameState.getPixel(1, 1);
        pixel1.setOwnerId(1);
        pixel2.setOwnerId(1);
        // Le joueur 0 avait 20 crédits,
        Player player = new Player(20);
        player.setUserId(0);
        gameState.addPlayer(0, player);
        // Joueur 0 essai de racheter les pixels de joueur 1
        int[] pixelsX = {1, 1};
        int[] pixelsY = {0, 1};
        new PaintPixelsAction(pixelsX, pixelsY, "red").execute(gameState, gameEvent, player);
        // Et il a réussit.
        assertTrue("Le joueur n'a pas était débité", player.getCredits() == 10);
        assertTrue("Le joueur n'a pas pris la possession du pixel1", pixel1.getOwnerId() == 0);
        assertTrue("Le joueur n'a pas pris la possession du pixel2", pixel2.getOwnerId() == 0);
    }
}
