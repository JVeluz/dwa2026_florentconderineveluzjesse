package m1.dwa.cv.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.entities.SkillState;
import m1.dwa.cv.game.actions.Action;
import m1.dwa.cv.game.events.GameEventListener;
import m1.dwa.cv.game.events.GameEventSource;
import m1.dwa.cv.game.skills.Skill;
import m1.dwa.cv.game.skills.SkillRegistery;

public class Game {
    private GameState gameState;
    private SkillRegistery skillRegistery;

    // FIFO pour les actions des joueurs
    private Queue<Action> actions = new LinkedList<>();
    private Queue<Integer> hasPendingAction = new LinkedList<>();

    // Evénements consommer seulement par GameService
    private GameEventSource gameEvent = new GameEventSource();

    private int currentTick = 0;

    public Game(GameState gameState, SkillRegistery skillRegistery) {
        this.gameState = gameState;
        this.skillRegistery = skillRegistery;
    }

    public void addListener(GameEventListener listener) {
        gameEvent.addListener(listener);
    }

    public void addPlayer(int userId, Player player) {
        gameState.addPlayer(userId, player);
    }

    public void removePlayer(int userId) {
        gameState.removePlayer(userId);
    }

    public boolean addAction(int userId, Action action) {
        if (hasPendingAction.contains(userId))
            return false;
        actions.add(action);
        hasPendingAction.add(userId);
        return true;
    }

    public boolean addSkill(int userId, int bonusId) {
        if (gameState.isSkillActivated(userId, bonusId))
            return false;
        gameState.addSkill(userId, bonusId);
        return true;
    }

    public void tick() {
        handlePixels();
        handlePlayers();
        handleActions();
        handleSkills();
        currentTick++;
    }

    private void handlePixels() {
        for (Pixel pixel : gameState.getPixels()) {
        }
    }

    private void handlePlayers() {
        for (Player player : gameState.getPlayers().values()) {
            player.resetCreditsPerTick();
        }
    }

    private void handleActions() {
        while (!actions.isEmpty() && !hasPendingAction.isEmpty()) {
            Action action = actions.poll();
            int userId = hasPendingAction.poll();
            Player player = gameState.getPlayer(userId);
            action.execute(gameState, gameEvent, player);
        }
    }

    private void handleSkills() {
        Map<Integer, List<Integer>> skillsToRemove = new HashMap<>();
        for (Map.Entry<Integer, List<SkillState>> entry : gameState.getSkills().entrySet()) {
            int userId = entry.getKey();
            List<SkillState> skills = entry.getValue();

            for (SkillState skillState : skills) {
                int bonusId = skillState.getBonusId();
                Skill skill = skillRegistery.get(bonusId);
                Player player = gameState.getPlayer(userId);

                if (skillState.getCurrentTick() == 0) {
                    skill.start(gameState, skillState, gameEvent, player);
                    gameEvent.skillStarted(userId, skillState);
                }

                if (skill.shouldApply(gameState, skillState)) {
                    skill.apply(gameState, skillState, gameEvent, player);
                }

                skillState.tick();

                if (skill.isFinished(gameState, skillState)) {
                    skill.end(gameState, skillState, gameEvent, player);
                    gameEvent.skillEnded(userId, skillState);

                    if (skillsToRemove.get(userId) == null)
                        skillsToRemove.put(userId, new LinkedList<>());
                    skillsToRemove.get(userId).add(bonusId);
                }
            }
        }
        for (Map.Entry<Integer, List<Integer>> entry : skillsToRemove.entrySet()) {
            int userId = entry.getKey();
            for (Integer bonusId : entry.getValue()) {
                gameState.removeSkill(userId, bonusId);
            }
        }
    }
}
