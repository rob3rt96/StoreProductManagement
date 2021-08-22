package theStore.logic;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import theStore.domain.Person;
import theStore.domain.Product;

/**
 * Clasa aceasta are rolul de a administra peste datele din clasa TheStore, separand clasa simpla cu date de cea cu metode care modifica datele.
 * In acest fel se vrea a fi mai simplu de urmarit si de a se face depanare in cazul aparitiei vreunei erori.
 *
 */

public class TheStoreManager {
	private TheStore store;
	
	public TheStoreManager(TheStore store) {
		this.store = store;
	}
	
	public TheStore getStore() {
		return store;
	}

	public void setStore(TheStore store) {
		this.store = store;
	}	
	
	
	//------------------------------------------- Separating the methods used for economy of code and for checking/printing ---------------------------------------

	public boolean isCategoryAttribuitedToAnyProduct(String category) {						// This method checks for the TextUI "interface" if a new category which needs to be added
		List<String> categoryNamesInStockList = includedCategoryNamesInStock();				// isn't already in the shop's categories list, or if the category exists when a product needs 
																							// to be added.
		return categoryNamesInStockList.contains(category);
	}

	public ArrayList<String> includedProductNamesInStock() {								// This method stores in a list the name of products from the store's stock, and returns it.
		ArrayList<String> names = new ArrayList<>();

		for (Product productTesting : this.store.getStock()) {
			if (!names.contains(productTesting.getName())) {
				names.add(productTesting.getName());
			}
		}
		return names;
	}

	public ArrayList<String> includedCategoryNamesInStock() {								// This method stores in a list the name of categories from the store's stock, and returns it.
		ArrayList<String> names = new ArrayList<>();

		for (Product productTesting : this.store.getStock()) {
			if (!names.contains(productTesting.getCategory())) {
				names.add(productTesting.getCategory());
			}
		}
		return names;
	}

	public ArrayList<String> includedPersonNamesInClientsList() {							// This method stores in a list the name of clients from the store's clients, and returns it.
		ArrayList<String> names = new ArrayList<>();

		for (Person clientTemp : this.store.getClients()) {
			if (!names.contains(clientTemp.getUsername())) {
				names.add(clientTemp.getUsername());
			}
		}
		return names;
	}

	public void printingInFile(String information, String filePath, String command, boolean writingMode) {		// This method helps the TextUI "interface" to print in the file some errors that may appear
		if (writingMode == true) {
			try {																					// only when the user switches the display mode from CONSOLE to FILE ${CALE_CATRE_FISIER}
				this.printingTheInformation(information, filePath, writingMode);
			} catch (Exception e) {
				System.out.println("An error happened. The program couldn't write to the file " + filePath);
				//e.printStackTrace();
			}
		} else {
			try {																					// only when the user switches the display mode from CONSOLE to FILE ${CALE_CATRE_FISIER}
				this.printingTheInformation(command, filePath, writingMode);
				this.printingTheInformation(information, filePath, writingMode);
			} catch (Exception e) {
				System.out.println("An error happened. The program couldn't write to the file " + filePath);
				//e.printStackTrace();
			}
		}		
	}	

	public void printingTheInformation(String information, String filePath, boolean writingMode) throws Exception {		// This method prints in the file the information about results of entered  
		if (writingMode == true) {
			System.out.println(information);
		} else {																		// Separating the commands for DisplayMode: FILE ${CALE_CATRE_FISIER}
			FileWriter fw = new FileWriter(new File(filePath), true);											// commands or the errors that may appear, only when the user switches the 
			LocalDateTime timeNow = LocalDateTime.now();														// display mode from CONSOLE to FILE ${CALE_CATRE_FISIER}
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss, SSS");
			String formatedDateTime = timeNow.format(formater);	

			fw.write("[" + formatedDateTime + "] " + information);
			fw.write(System.lineSeparator());
			fw.write(System.lineSeparator());

			fw.close();
			//System.out.println("The printing in the file was successful. You can check the file");
		}		
	}

	//---------------------------------------------------- Separating the commands for DisplayMode: CONSOLE (from here, downward) ---------------------------------

	public void printProductsFromCategory(String category, String filePath, String command, List<String> categoriesBuffer, boolean writingMode) {			// This method has the job to print all the products from a specific category
		try {
			if (writingMode == false) {
				this.printingTheInformation(command, filePath, writingMode);
			}			
			List<String> listOfAttributedCategories = includedCategoryNamesInStock();			

			if (!listOfAttributedCategories.contains(category)) {										// This method verifies if the category exists or not in the categories of shop's stock products
				if (categoriesBuffer.contains(category)) {												// This method checks if the category is added, but if it has any product attached to it
					String information = "The category \"" + category + "\" has been added but has not yet any product attached to it. Try to add a new product first to this category.";
					this.printingTheInformation(information, filePath, writingMode);
					return;
				}
				String information = "The category \"" + category + "\" couldn't be found. Please try again";
				this.printingTheInformation(information, filePath, writingMode);
				return;
			}

			int temp = 1;																				// This is a variable that is keeping the count of the rows to be printed	
			for (int i = 0; i < this.store.getStock().size(); i++) {
				if (this.store.getStock().get(i).getCategory().equalsIgnoreCase(category)) {
					String information = temp + " " + this.store.getStock().get(i);
					this.printingTheInformation(information, filePath, writingMode);
					temp++;
				} 
			}							
		} catch (Exception e) {
			System.out.println("An error happened. The program couldn't write to the file " + filePath);
			//e.printStackTrace();
		}
	}					


	public void printProductsAll(String filePath, String command, boolean writingMode) {												// This method has the job to print all the details of the store's products
		try {
			if (writingMode == false) {
				this.printingTheInformation(command, filePath, writingMode);
			}

			int temp = 1;																			// This is a variable that is keeping the count of the rows to be printed
			StringBuffer strBuffer = new StringBuffer();
			// This for loop stores in a StringBuffer variable all the information it needs to print about every product, and prints it in the console
			for (int i = 0; i < this.store.getStock().size(); i++) {								
				strBuffer.append(temp + " " + this.store.getStock().get(i).getName() + " " + this.store.getStock().get(i).getQuantity() + " "
						+ this.store.getStock().get(i).getCategory() + " " + this.store.getStock().get(i).getPrice());				
				String products = strBuffer.toString();
				this.printingTheInformation(products, filePath, writingMode);

				temp++;
				strBuffer.delete(0, strBuffer.length());											// This method helps to empty the StringBuffer variable at the end of every cycle of the loop
			}					
		} catch (Exception e) {
			System.out.println("An error happened. The program couldn't write to the file " + filePath);
			//e.printStackTrace();
		}
	}		
	
	public void printSpecificProduct(String productName, String filePath, String command, boolean writingMode) {										// This method has the job to print some details of a specific product from the store
		try {
			if (writingMode == false) {
				this.printingTheInformation(command, filePath, writingMode);
			}
			List<String> productNames = includedProductNamesInStock();											// This method stores in a list all the names of every product from the store's stock.

			if (!productNames.contains(productName)) {															// This method prints an error message and stops the program if the searched productName couldn't be found.
				String information = "The product \"" + productName + "\" couldn't be found. Please try again";
				this.printingTheInformation(information, filePath, writingMode);
				return;
			}					
			for (Product temp : this.store.getStock()) {
				if (temp.getName().equals(productName)) {											
					String information = temp.getName() + " " + temp.getQuantity() + " " + temp.getPrice();
					this.printingTheInformation(information, filePath, writingMode);
				} 
			}									
		} catch (Exception e) {
			System.out.println("An error happened. The program couldn't write to the file " + filePath);
			//e.printStackTrace();
		}
	}

	public void printCategories(List<String> categoriesBuffer, String filePath, String command, boolean writingMode) {				// This method has the job to print all the categories of the store's products and the newly added categories
		try {
			if (writingMode == false) {
				this.printingTheInformation(command, filePath, writingMode);
			}
			
			List<String> categoryNamesInStockList = includedCategoryNamesInStock();		// This method stores in a list all the names of every category from the shop

			for (String newCategory : categoriesBuffer) {								// This for loop stores in the already created list, the newly added categories
				if (!categoryNamesInStockList.contains(newCategory)) {
					categoryNamesInStockList.add(newCategory);
				}				
			}

			StringBuffer output = new StringBuffer();
			for (String tempStr : categoryNamesInStockList) {							// This FOR loop stores every category name in a string, followed by a comma (,)
				output.append(tempStr + ",");
			}
			output = output.deleteCharAt(output.length() - 1);							// This method deletes the last comma (,) of the string
			String outputAsString = output.toString();			

			this.printingTheInformation(outputAsString, filePath, writingMode);						
		} catch (Exception e) {
			System.out.println("An error happened. The program couldn't write to the file " + filePath);
			//e.printStackTrace();
		}
	}	
	
	public void printDisplayMode(String filePath, String command, String displayMode, boolean writingMode) {			// This method has the role to write in the file the current Display Mode, only when the display mode is switched to FILE
		if (writingMode == true) {
			try {
				this.printingTheInformation(displayMode, filePath, writingMode);									
			} catch (Exception e) {
				System.out.println("An error happened. The program couldn't write to the file " + filePath);
				//e.printStackTrace();
			}
		} else {
			try {
				this.printingTheInformation(command, filePath, writingMode);					

				this.printingTheInformation(displayMode, filePath, writingMode);									
			} catch (Exception e) {
				System.out.println("An error happened. The program couldn't write to the file " + filePath);
				//e.printStackTrace();
			}
		}		
	}

	public void buyProductQuantityForUser (String productName, int quantity, String userName, String filePath, String command, boolean writingMode) {			// This method creates the possibility for a client to buy a product from the shop
		try {
			if (writingMode == false) {
				this.printingTheInformation(command, filePath, writingMode);
			}			

			List<String> productNamesInStockList = includedProductNamesInStock();							// This method stores in a list all the names of every product from the shop
			List<String> personNamesInClientsList = includedPersonNamesInClientsList();						// This method stores in a list all the names of every client of the shop

			if (!personNamesInClientsList.contains(userName)) {															// This method prints an error message and stops the program if the searched usertName couldn't be found.
				String information = "The username \"" + userName + "\" can't be found in the store's clients list.";
				this.printingTheInformation(information, filePath, writingMode);
				return;
			}

			if (!productNamesInStockList.contains(productName)) {														// This method prints an error message and stops the program if the searched productName couldn't be found.
				String information = "The product \"" + productName + "\" cannot be found in the store's stock."
						+ " Please check the name and try again.";		
				this.printingTheInformation(information, filePath, writingMode);
				return;
			}

			int totalPrice = 1;																						// This is a variable where the program calculates the total price that the client will have to pay for all the products.
			for (Product tempProduct : this.store.getStock()) {
				if (tempProduct.getName().equals(productName)) {
					totalPrice = quantity * tempProduct.getPrice();
					break;
				}
			}

			for (Person tempPerson : this.store.getClients()) {													// This for loop searches between every client of the shop
				if (tempPerson.getUsername().equals(userName)) {												// This method identifies only the client which has the searched name
					if (!(tempPerson.getBalance() >= totalPrice)) {												// This method compares the customer's balance with the total price that he will have to pay. 
						String information = "The balance of user " + tempPerson.getUsername() + " has insufficient money to complete "		// If the customer does not have enough credits, the program will stop and it will print an error.
								+ "the transaction. The balance is: " + tempPerson.getBalance();
						this.printingTheInformation(information, filePath, writingMode);
						break;
					} else {
						for (Product tempProduct2 : this.store.getStock()) {									// This for loop searches between every product from the shop
							if (tempProduct2.getName().equals(productName)) {									// This method identifies only the product which has the searched name
								if (tempProduct2.getQuantity() < quantity) {									// This method verifies if the store has enough quantity for the desired product to be bought
									String information = "User " + tempPerson.getUsername() + " cannot buy " + quantity + " " + productName + " because there is only "		// If not, the program will stop and it will print an error message.
											+ tempProduct2.getQuantity() + " " + productName + " left.";

									this.printingTheInformation(information, filePath, writingMode);																			
									break;
								} else {																		// In this block the program modifies the desired product's quantity and the 
									tempProduct2.setQuantity(tempProduct2.getQuantity() - quantity);			// customer's balance when the a product is bought.
									tempPerson.setBalance(tempPerson.getBalance() - totalPrice);					

									String information = "User " + tempPerson.getUsername() + " has bought " + quantity + " " + productName + ".";
									this.printingTheInformation(information, filePath, writingMode);
									break;
								}
							}
						}
					}
					break;
				}
			}
			//System.out.println("The printing in the file was successful. You can check the file");
		} catch (Exception e) {
			System.out.println("An error happened. The program couldn't write to the file " + filePath);
			//e.printStackTrace();
		}
	}						
	
	public void replenishProductQuantity(String productNameToReplenish, int quantityRepl, String filePath, String command, boolean writingMode) {				// This method has the job to replenish the store's stock with products in a desired quantity
		try {
			if (writingMode == false) {
				this.printingTheInformation(command, filePath, writingMode);
			}			

			List<String> productNamesInStockList = includedProductNamesInStock();

			if (!productNamesInStockList.contains(productNameToReplenish)) {											// This method prints an error message and stops the program if the searched productName couldn't be found.
				String information = "The product \"" + productNameToReplenish + "\" cannot be found in the store's "
						+ "stock. Please check the name and try again.";
				this.printingTheInformation(information, filePath, writingMode);
				return;
			}

			for (Product temp : this.store.getStock()) {															// This for loop searches between every product from the shop
				if (temp.getName().equals(productNameToReplenish)) {
					if (temp.getQuantity() + quantityRepl > temp.getMaxQuantity()) {								// This method prints an error and then stops the program, if the quantity that needs to be replenished with
						String information = "The entered quantity exceeds the \"MaxQuantity\" parameter, which "	// is bigger than the max quantity that the shop can hold.
								+ "is " + temp.getMaxQuantity();
						this.printingTheInformation(information, filePath, writingMode);
						break;
					} else {																						// This else block increases the quantity of the searched product and prints a message.
						temp.setQuantity(temp.getQuantity() + quantityRepl);							
						String information = "The replenish was succesfull";							
						this.printingTheInformation(information, filePath, writingMode);
						break;
					}
				}
			}				
		} catch (Exception e) {
			System.out.println("An error happened. The program couldn't write to the file " + filePath);
			//e.printStackTrace();
		}
	}		
	
	public void addNewProduct(String productName, String categoryName, int quantity, int price, String filePath, String command, boolean writingMode) {		// This method makes possible for a new product to be added in the store's stock of products
		try {
			if (writingMode == false) {
				this.printingTheInformation(command, filePath, writingMode);
			}

			while (true) {
				List<String> temporaryProductNamesList = includedProductNamesInStock();
				if (temporaryProductNamesList.contains(productName)) {										// This method prints an error message and stops the program if the searched productName couldn't be found in the list.
					String information = "The product \"" + productName + "\" already is in the stock. "
							+ "Please try with another name.";		
					this.printingTheInformation(information, filePath, writingMode);					
					break;
				}				
				this.store.getStock().add(new Product(categoryName, productName, quantity, price, 100));

				String information = "The product was added successfully.";
				this.printingTheInformation(information, filePath, writingMode);
				break;				
			}			
		} catch (Exception e) {
			System.out.println("An error happened. The program couldn't write to the file " + filePath);
			//e.printStackTrace();
		}
	}		
	

	public void removeProduct (String productName, String filePath, String command, boolean writingMode) {					// This method creates the possibility for a product to be removed from the store's stock
		try {
			if (writingMode == false) {
				this.printingTheInformation(command, filePath, writingMode);
			}
			List<String> productNamesInStockList = includedProductNamesInStock();			// This method stores in a list all the names of every product from the shop

			if (!productNamesInStockList.contains(productName)) {							// This method prints an error message and stops the program if the searched productName couldn't be found in the list.
				String information =  "The product \"" + productName + "\" cannot be found in the store's stock. "
						+ "Please check the name and try again.";
				this.printingTheInformation(information, filePath, writingMode);
				return;
			}

			for (Product tempProduct : this.store.getStock()) {								// This for loop searches between every product from the shop
				if (tempProduct.getName().equals(productName)) {
					if (tempProduct.getQuantity() > 0) {									// If the quantity of the searched product is not zero, the program stops and prints an error message.
						String information = "Cannot remove \"" + tempProduct.getName() + "\" because quantity is "
								+ "not zero. " + "Quantity currently is " + tempProduct.getQuantity();
						this.printingTheInformation(information, filePath, writingMode);				
						break;
					} else {																// In this else block, the desired product is removed from the list of shop's products
						this.store.getStock().remove(tempProduct);
						String information =  "The remove of the product \"" + tempProduct.getName() + "\" was successful";						
						this.printingTheInformation(information, filePath, writingMode);
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("An error happened. The program couldn't write to the file " + filePath);
			//e.printStackTrace();
		}
	}					
	
}
