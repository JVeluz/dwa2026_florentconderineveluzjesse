package m1.dwa.cv.orms;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class SkillJpa {

    @Id private int id;

    @ManyToOne
    private UserJpa user;

    private int bonusId;
    private int currentTick;

    @Column(columnDefinition = "TEXT")
    private String memoryJson;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserJpa getUser() {
		return user;
	}

	public void setUser(UserJpa user) {
		this.user = user;
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

	public String getMemoryJson() {
		return memoryJson;
	}

	public void setMemoryJson(String memoryJson) {
		this.memoryJson = memoryJson;
	}
}
