package m1.dwa.cv.orms;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BonusJpa implements Serializable {
    @Id private int id;
    private String name;
    private String description;
	private int price;

    public int getId() {
        return id;
    }

    public BonusJpa setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BonusJpa setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BonusJpa setDescription(String description) {
		this.description = description;
		return this;
	}

    public int getPrice() {
        return price;
    }

    public BonusJpa setPrice(int price) {
        this.price = price;
        return this;
    }
}
