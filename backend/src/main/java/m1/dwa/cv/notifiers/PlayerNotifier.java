package m1.dwa.cv.notifiers;

import java.util.Set;

import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.entities.SkillState;

public interface PlayerNotifier {
    public void playerUpdated(int userId, Player player);
    public void pixelsUpdated(Set<Pixel> pixels);
    public void skillStarted(int userId, SkillState skill);
    public void skillEnded(int userId, SkillState skill);
    public void actionFailed(int userId, String message);
}
