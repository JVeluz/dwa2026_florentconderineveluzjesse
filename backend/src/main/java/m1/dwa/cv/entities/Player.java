package m1.dwa.cv.entities;

public class Player {
    private int credits;
    private int userId = -1;
    private int clickMultiplier = 1;
    private int creditsPerTick = 0;

	public Player(int credits) {
	    this.credits = credits;
    }

    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

    public int getCredits() {
        return credits;
    }

    public int getClickMultiplier() {
        return clickMultiplier;
    }

    public void setClickMultiplier(int clickMultiplier) {
        this.clickMultiplier = clickMultiplier;
    }

    public void addCredits(int credits) {
        this.credits += credits;
    }

    public void removeCredits(int credits) {
        this.credits -= credits;
    }

    public int getCreditsPerTick() {
        return creditsPerTick;
    }

    public void resetCreditsPerTick() {
        creditsPerTick = 0;
    }

	public void addCreditsPerTick(int credits) {
    	creditsPerTick += credits;
	}

	public void removeCreditsPerTick(int credits) {
        creditsPerTick -= credits;
	}
}
