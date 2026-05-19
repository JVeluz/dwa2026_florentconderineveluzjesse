package m1.dwa.cv.game.skills;

import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.entities.SkillState;
import m1.dwa.cv.game.events.GameEventSource;

public class PassiveCreditsSkill implements Skill {
    private final int forXTicks;
    private final int eachXTicks;
    private final int amount;

    public PassiveCreditsSkill(int forXTicks, int eachXTicks, int amount) {
        this.forXTicks = forXTicks;
        this.eachXTicks = eachXTicks;
        this.amount = amount;
    }

	@Override public void start(GameState gameState, SkillState skillState, GameEventSource gameEvent, Player player) {
        player.addCreditsPerTick(amount/eachXTicks);
	}

    @Override
    public void apply(GameState gameState, SkillState skillState, GameEventSource gameEvent, Player player) {
        player.addCredits(amount);
    }

	@Override
	public boolean isFinished(GameState gameState, SkillState state) {
	    return state.getCurrentTick() >= forXTicks;
	}

	@Override
	public boolean shouldApply(GameState gameState, SkillState state) {
	    return state.getCurrentTick() % eachXTicks == 0;
	}

	@Override public void end(GameState gameState, SkillState skillState, GameEventSource gameEvent, Player player) {
    	player.removeCreditsPerTick(amount/eachXTicks);
	}
}
