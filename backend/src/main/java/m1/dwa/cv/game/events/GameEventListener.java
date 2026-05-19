package m1.dwa.cv.game.events;

import java.util.Set;

import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.entities.SkillState;

// %% https://codemia.io/knowledge-hub/path/create_a_custom_event_in_java

public interface GameEventListener {
    public void onPixelsUpdate(Set<Pixel> pixels);
    public void onPlayerUpdate(int userId, Player player);
    public void onSkillUpdated(SkillState skill);
    public void onSkillStarted(int userId, SkillState skill);
    public void onSkillEnded(int userId, SkillState skill);
    public void onActionFail(int userId, String message);
}
