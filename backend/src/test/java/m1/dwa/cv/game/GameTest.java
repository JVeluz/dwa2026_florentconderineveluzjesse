package m1.dwa.cv.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.game.actions.ClickAction;
import m1.dwa.cv.game.skills.SkillRegistery;

public class GameTest {

    private Game game;

    @Before
    public void setup() {
        GameState gameState = new GameState();
        SkillRegistery skillRegistery = new SkillRegistery();
        game = new Game(gameState, skillRegistery);
        game.addPlayer(0, new Player(0));
        game.addPlayer(1, new Player(0));
    }

    @Test
    public void twoActionSamePlayer() {
        // Le joueur 0 tente de faire deux actions dans le même tick
        boolean firstAction = game.addAction(0, new ClickAction(1));
        boolean secondAction = game.addAction(0, new ClickAction(1));

        assertTrue("La première action doit être acceptée", firstAction);
        assertFalse("La deuxième action doit être refusée", secondAction);
    }

    @Test
    public void twoActionsTwoPlayers() {
        // Deux joueurs différents font une action
        boolean actionPlayer0 = game.addAction(0, new ClickAction(1));
        boolean actionPlayer1 = game.addAction(1, new ClickAction(1));

        assertTrue("L'action du joueur 0 est acceptée", actionPlayer0);
        assertTrue("L'action du joueur 1 est acceptée", actionPlayer1);
    }

    @Test
    public void queueIsClearedAfterTick() {
        game.addAction(0, new ClickAction(1));
        game.tick(); // L'action est consommée

        // Au tick suivant, le joueur devrait pouvoir refaire une action
        boolean nextAction = game.addAction(0, new ClickAction(1));
        assertTrue("Le joueur doit pouvoir rejouer après un tick", nextAction);
    }

    @Test
    public void cannotAddSameSkillTwice() {
        // Le joueur 0 active le bonus 0
        boolean firstTime = game.addSkill(0, 0);
        // Il tente de le réactiver immédiatement
        boolean secondTime = game.addSkill(0, 0);

        assertTrue("Le premier ajout doit être accepté", firstTime);
        assertFalse("Le deuxième ajout doit être refusé", secondTime);
    }

    @Test
    public void skillIsRemovedAfterExpiration() {
        game.addSkill(0, 0);

        // On vérifie qu'il est bien actif et bloque les doublons
        assertFalse(game.addSkill(0, 0));

        // On simule l'écoulement du temps (on fait 50 ticks pour être sûr de dépasser la durée max du bonus)
        for (int i = 0; i < 50; i++) {
            game.tick();
        }

        // Si le tick() a bien fait son travail de nettoyage, le bonus n'est plus actif,
        // donc on devrait pouvoir l'ajouter à nouveau !
        boolean canAddAgain = game.addSkill(0, 0);
        assertTrue("Le bonus aurait dû expirer et être retiré du GameState", canAddAgain);
    }

    @Test
    public void multipleSkillsAreRemovedCorrectly() {
        // On donne deux bonus différents au joueur 0
        game.addSkill(0, 0);
        game.addSkill(0, 1);

        // On fait passer le temps
        for (int i = 0; i < 50; i++) {
            game.tick();
        }

        // On vérifie que les deux bonus ont bien été retirés
        assertTrue("Le bonus 0 aurait dû expirer", game.addSkill(0, 0));
        assertTrue("Le bonus 1 aurait dû expirer", game.addSkill(0, 1));
    }
}
