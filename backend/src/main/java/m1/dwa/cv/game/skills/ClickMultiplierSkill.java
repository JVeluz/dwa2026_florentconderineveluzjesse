package m1.dwa.cv.game.skills;

import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.entities.SkillState;
import m1.dwa.cv.game.events.GameEventSource;

public class ClickMultiplierSkill implements Skill {
    private final int forXTicks;
    private final int eachXTicks;
    private final int clickMultiplier;

    public ClickMultiplierSkill(int forXTicks, int eachXTicks, int clickMultiplier) {
        this.forXTicks = forXTicks;
        this.eachXTicks = eachXTicks;
        this.clickMultiplier = clickMultiplier;
    }

	@Override
	public void start(GameState gameState, SkillState skillState, GameEventSource gameEvent, Player player) {
	    int oldMultiplier = player.getClickMultiplier();
		skillState.putMemory("oldMultiplier", oldMultiplier);
		player.setClickMultiplier(clickMultiplier);
		gameEvent.playerUpdated(player.getUserId(), player);
	}

	@Override
	public void end(GameState gameState, SkillState skillState, GameEventSource gameEvent, Player player) {
        int oldMultiplier = (int) skillState.getMemory("oldMultiplier");
        player.setClickMultiplier(oldMultiplier);
        gameEvent.playerUpdated(player.getUserId(), player);
	}

	@Override
	public boolean isFinished(GameState gameState, SkillState skillState) {
	    return skillState.getCurrentTick() >= forXTicks;
	}

	@Override
	public boolean shouldApply(GameState gameState, SkillState skillState) {
	    return skillState.getCurrentTick() % eachXTicks == 0;
	}

	@Override public void apply(GameState gameState, SkillState skillState, GameEventSource gameEvent, Player player) {}
}
