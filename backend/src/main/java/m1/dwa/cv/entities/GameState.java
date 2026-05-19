package m1.dwa.cv.entities;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameState {
    private int gridSize = 50;
    private List<Pixel> pixels;
    private Map<Integer, Player> userToPlayer = new Hashtable<>();
    private Map<Integer, List<SkillState>> userToSkill = new Hashtable<>();

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int value) {
        gridSize = value;
    }

    public List<Pixel> getPixels() {
        return pixels;
    }

    public void setPixel(List<Pixel> pixels) {
        this.pixels = pixels;
    }

    public Pixel getPixel(int x, int y) {
        return pixels.get(getPixelIndex(x, y));
    }

    public Player getPlayer(int userId) {
        return userToPlayer.get(userId);
    }

    public Map<Integer, Player> getPlayers() {
        return userToPlayer;
    }

    public void addPlayer(int userId, Player player) {
        userToPlayer.put(userId, player);
    }

    public void removePlayer(int userId) {
        userToPlayer.remove(userId);
    }

    public Map<Integer, List<SkillState>> getSkills() {
        return userToSkill;
    }

    public void setSkills(Map<Integer, List<SkillState>> skills) {
        userToSkill = skills;
    }

    public void addSkill(int userId, int bonusId) {
        if (userToSkill.get(userId) == null)
            userToSkill.put(userId, new LinkedList<>());
        userToSkill.get(userId).add(new SkillState(bonusId));
    }

    public void removeSkill(int userId, int bonusId) {
        userToSkill.get(userId).removeIf(skill -> skill.getBonusId() == bonusId);
    }

    public boolean isSkillActivated(int userId, int bonusId) {
        List<SkillState> playerSkills = userToSkill.get(userId);
        if (playerSkills == null)
            return false;
        for (SkillState skillState : playerSkills) {
            if (skillState.getBonusId() == bonusId)
                return true;
        }
        return false;
    }

    private int getPixelIndex(int x, int y) {
        return y * gridSize + x;
    }
}
