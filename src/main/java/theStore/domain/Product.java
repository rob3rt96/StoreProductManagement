package theStore.domain;

import java.util.Objects;

//import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
	
	private String category;
	private String name;
	private int quantity;
	private int price;
	private int maxQuantity;
	
	
	
	public Product(String category, String name, int quantity, int price, int maxQuantity) {
		this.category = category;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.maxQuantity = maxQuantity;
	}
	
	public Product() {
		
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	@Override
	public String toString() {
		return this.name + " " + this.quantity + " " + this.price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, maxQuantity, name, price, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(category, other.category) && maxQuantity == other.maxQuantity
				&& Objects.equals(name, other.name) && price == other.price && quantity == other.quantity;
	}
	
	
}