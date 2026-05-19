package m1.dwa.cv.game.skills;

import m1.dwa.cv.entities.SkillState;
import m1.dwa.cv.game.events.GameEventSource;
import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Player;

public interface Skill {
    public void start(GameState gameState, SkillState skillState, GameEventSource gameEvent, Player player);
    public void apply(GameState gameState, SkillState skillState, GameEventSource gameEvent, Player player);
    public void end(GameState gameState, SkillState skillState, GameEventSource gameEvent, Player player);
    public boolean isFinished(GameState gameState, SkillState skillState);
    public boolean shouldApply(GameState gameState, SkillState skillState);
}
