package m1.dwa.cv.orms;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserJpa implements Serializable {
    @Id private int id;
    private String pseudo;
    private String password;
    private int age;
    private String countryCode;
    private int credits;

    @OneToMany private Set<PixelJpa> pixels = new HashSet<>();
    @OneToMany private Set<BonusJpa> bonuses = new HashSet<>();

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
	public Set<PixelJpa> getPixels() {
		return pixels;
	}
	public void setPixels(Set<PixelJpa> pixels) {
		this.pixels = pixels;
	}
	public Set<BonusJpa> getBonuses() {
		return bonuses;
	}
	public void setBonuses(Set<BonusJpa> bonuses) {
		this.bonuses = bonuses;
	}
}
