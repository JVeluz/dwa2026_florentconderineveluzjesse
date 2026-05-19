package m1.dwa.cv.game.actions;

import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.game.events.GameEventSource;

public interface Action {
    // Etat du jeu + l'objet pour emmettre des evenements + le joueur qui initie l'action
    public void execute(GameState gameState, GameEventSource gameEvent, Player player);
}
