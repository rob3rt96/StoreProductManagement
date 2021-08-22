package theStore.domain;

public class Person {
	private String username;
	private int balance;
		
	public Person(String username, int balance) {
		this.username = username;
		this.balance = balance;
	}
	
	public Person() {
		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
}
