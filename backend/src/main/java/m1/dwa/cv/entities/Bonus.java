package m1.dwa.cv.entities;

public class Bonus {
    private int id;
    private String name;
    private String description;
	private int price;

	public int getId() {
		return id;
	}
	public Bonus setId(int id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Bonus setName(String name) {
		this.name = name;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Bonus setDescription(String description) {
		this.description = description;
		return this;
	}
	public int getPrice() {
		return price;
	}
	public Bonus setPrice(int price) {
		this.price = price;
		return this;
	}
}
