package m1.dwa.cv.entities;

import java.util.HashSet;
import java.util.Set;
public class User {
    private int id;
    private String pseudo;
    private String password;
    private int age;
    private String countryCode;
    private int credits;
    private Set<Pixel> pixels = new HashSet<>();
    private Set<Bonus> bonuses = new HashSet<>();

    public void addCredits(int credits) {
        this.credits += credits;
    }

    public void removeCredits(int credits) {
        this.credits -= credits;
    }

    public int pixelsCount() {
        return pixels.size();
    }

    public void addPixel(Pixel pixel) {
        pixels.add(pixel);
    }

    public void removePixel(Pixel pixel) {
        pixels.remove(pixel);
    }

    public void setBonuses(Set<Bonus> bonuses) {
        this.bonuses = bonuses;
    }

    public Bonus[] getBonuses() {
        return bonuses.toArray(new Bonus[0]);
    }

    public boolean hasBonus(Bonus bonus) {
        return bonuses.contains(bonus);
    }

    public void addBonus(Bonus bonus) {
        bonuses.add(bonus);
    }

    @Override
    public String toString() {
        return "pseudo: " + pseudo + " password: " + password;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public Set<Pixel> getPixels() {
		return pixels;
	}

	public void setPixels(Set<Pixel> pixels) {
		this.pixels = pixels;
	}
}
