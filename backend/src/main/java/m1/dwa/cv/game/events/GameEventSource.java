package m1.dwa.cv.game.events;

import java.util.HashSet;
import java.util.Set;

import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.entities.SkillState;

// %% https://codemia.io/knowledge-hub/path/create_a_custom_event_in_java

public class GameEventSource {
    private Set<GameEventListener> listeners = new HashSet<>();

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameEventListener listener) {
        listeners.remove(listener);
    }

    public void pixelsUpdated(Set<Pixel> pixels) {
        for (GameEventListener listener : listeners) {
            listener.onPixelsUpdate(pixels);
        }
    }

    public void playerUpdated(int userId, Player player) {
        for (GameEventListener listener : listeners) {
            listener.onPlayerUpdate(userId, player);
        }
    }

    public void actionFailed(int userId, String message) {
        for (GameEventListener listener : listeners) {
            listener.onActionFail(userId, message);
        }
    }

    public void skillUpdated(SkillState skill) {
        for (GameEventListener listener : listeners) {
            listener.onSkillUpdated(skill);
        }
    }

    public void skillStarted(int userId, SkillState skill) {
        for (GameEventListener listener : listeners) {
            listener.onSkillStarted(userId, skill);
        }
    }

    public void skillEnded(int userId, SkillState skill) {
        for (GameEventListener listener : listeners) {
            listener.onSkillEnded(userId, skill);
        }
    }
}
