package m1.dwa.cv.entities;

import java.util.HashMap;
import java.util.Map;

public class SkillState {
    private int id;
    private int bonusId;
	private int currentTick;
	private int userId;
	private User user;
	private Map<String, Object> memory = new HashMap<>();

	public SkillState(int bonusId) {
	    this.bonusId = bonusId;
	}

	public SkillState(int bonusId, int currentTick) {
	    this.bonusId = bonusId;
		this.currentTick = currentTick;
	}

	public void tick() {
        currentTick++;
	}

	public void putMemory(String key, Object value) {
        memory.put(key, value);
	}

	public Object getMemory(String key) {
    	return memory.get(key);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBonusId() {
		return bonusId;
	}

	public void setBonusId(int bonusId) {
		this.bonusId = bonusId;
	}

	public int getCurrentTick() {
		return currentTick;
	}

	public void setCurrentTick(int currentTick) {
		this.currentTick = currentTick;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Map<String, Object> getMemory() {
		return memory;
	}

	public void setMemory(Map<String, Object> memory) {
		this.memory = memory;
	}
}
