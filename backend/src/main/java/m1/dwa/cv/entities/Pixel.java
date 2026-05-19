package m1.dwa.cv.entities;

public class Pixel {
    private int id;
	private int x;
    private int y;
    private int price;
    private String colorHexadecimal;
    private int oldness;
    private int ownerId = -1;

    public Pixel(int x, int y, int price) {
        this.x = x;
        this.y = y;
        this.price = price;
    }

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

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
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

	@Override
    public String toString() {
        return String.format("{%d, %d}", x, y);
    }
}
