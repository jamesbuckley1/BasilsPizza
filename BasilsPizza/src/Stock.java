
public class Stock {
	
	String item;
	double price;
	int quantity;
	
	public Stock(String item, double price, int quantity) {
		this.item = item;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "Item: " + this.getItem() + ", Price: " + 
				this.getPrice() + ", Quantity: " + this.getQuantity();
	}
}
