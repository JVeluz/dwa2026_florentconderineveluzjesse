package m1.dwa.cv.orms;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PixelJpa implements Serializable {
    @Id private int id;
    private int x;
    private int y;
    private int price;
    private String colorHexadecimal;
    private int oldness;
    private int ownerId;
	@ManyToOne private transient UserJpa owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColorHexadecimal() {
        return colorHexadecimal;
    }

    public void setColorHexadecimal(String colorHexadecimal) {
        this.colorHexadecimal = colorHexadecimal;
    }

    public int getOldness() {
        return oldness;
    }

    public void setOldness(int oldness) {
        this.oldness = oldness;
    }

    public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

    public UserJpa getOwner() {
        return owner;
    }

    public void setOwner(UserJpa owner) {
        this.owner = owner;
    }

    public String toString() {
        return String.format("{%d; %d}", x, y);
    }
}
