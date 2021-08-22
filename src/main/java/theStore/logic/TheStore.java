package theStore.logic;

import java.util.ArrayList;
import java.util.List;

import theStore.domain.Person;
import theStore.domain.Product;

/**
 * Clasa aceasta reprezinta clasa obisnuita (POJO), care va fi populata cu datele din fisierul JSON.
 * In alta instanta, este folosita pentru a exporta datele convertite in format JSON.
 *
 */

public class TheStore {
	private List<Product> stock;
	private List<Person> clients;

	public TheStore() {
		this.stock = new ArrayList<>();
		this.clients = new ArrayList<>();
	}
	
	public List<Product> getStock() {
		return stock;
	}

	public void setStock(List<Product> stock) {
		this.stock = stock;
	}

	public List<Person> getClients() {
		return clients;
	}

	public void setClients(List<Person> clients) {
		this.clients = clients;
	}
}
	
	