package theStore.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import theStore.converting.JavaToJsonJsonToJava;
import theStore.logic.TheStoreManager;

/**
 * 
 * This class it's a text kind of User Interface, and represents the way that the user can input commands in the program. 
 *
 */

public class TextUI {

	private Scanner scanner;
	private TheStoreManager storeManager;
	private List<String> categoriesBuffer;
	private String displayMode;
	private boolean writingMode;

	public TextUI(Scanner scanner, TheStoreManager storeManager) {
		this.scanner = scanner;
		this.storeManager = storeManager;
		this.categoriesBuffer = new ArrayList<>();				// This variable stores the categories that have been added, until a product belonging to that category is added
		this.displayMode = "CONSOLE";							// This variable has the purpose to remember the way that the command results will be printed, in CONSOLE or in FILE ${CALE_CATRE_FISIER}
		this.writingMode = true;
	}

	public void start() {
		System.out.println("Type HELP to show available actions");
		while (true) {
			System.out.println("");			
			System.out.print("> ");

			String command = this.scanner.nextLine();

			if (command.equalsIgnoreCase("X")) {				// Implementing the command that exits the program
				break;
			}

			if (command.equalsIgnoreCase("HELP")) {				// Implementing the command that prints the HELP menu with all the commands available and their description
				System.out.println(getHelp());
				continue;
			}			

			String commandNormal = new String(command);					// Implementing the import of the normal entered by keyboard commands, used to mantain the original form of the
			String[] partsCommandNormal = commandNormal.split(" ");		// address to the files in the computer.

			command = command.toUpperCase();							// Implementing the import of the commands entered with/without Uppercase, because they will all be converted
			String[] partsOfInput = command.split(" ");					// to uppercase.

			String theCommand = "";				// Saving the command in a String format, to be sent to the FILE when DisplayMode is switched to FILE
			for (String temp : partsOfInput) {
				theCommand += temp + " ";
			}
			theCommand = theCommand.trim();

			String pathForFile = this.displayMode.substring(5);		// Trimming the "FILE " from the entered command "FILE ${CALE_CATRE_FISIER}", and keeping only the real path to the file	

			if (!command.contains(" ")) {
				String information = "Unknown command!";
				storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
				continue;
			}

			//----------------------------Separating the commands for DisplayMode: CONSOLE---------------------------------------------------------------------------------

			if (partsOfInput[0].equals("PRINT")) {										
				if (partsOfInput[1].equals("PRODUCTS") && partsOfInput.length >= 3 && partsOfInput.length <= 4) {
					if (partsOfInput[2].equals("CATEGORY") && partsOfInput.length == 4) {	// Checking the command "PRINT PRODUCTS CATEGORY ${CATEGORY_NAME}"
						storeManager.printProductsFromCategory(partsOfInput[3], pathForFile, theCommand, categoriesBuffer, writingMode);
					} else if (partsOfInput[2].equals("ALL") && partsOfInput.length == 3) { // Checking the command "PRINT PRODUCTS ALL"
						storeManager.printProductsAll(pathForFile, theCommand, writingMode);
					} else if (partsOfInput.length == 3) {									// Checking the command "PRINT PRODUCTS ${PRODUCTS_NAME}"
						storeManager.printSpecificProduct(partsOfInput[2], pathForFile, theCommand, writingMode);
					} else {
						String information = "Unknown command! The word \"" + partsOfInput[2] + "\" seems to be misspelled.";	//* In case of a mistake, the program will try to point out a possible misspelled word.
						storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
					}
				} else if (partsOfInput[1].equals("CATEGORIES") && partsOfInput.length == 2) {			// Checking the command "PRINT CATEGORIES"
					storeManager.printCategories(categoriesBuffer, pathForFile, theCommand, writingMode);
				} else if (partsOfInput[1].equals("DISPLAY_MODE") && partsOfInput.length == 2) {	// Checking the command "PRINT DISPLAY_MODE "
					if (this.displayMode.equals("CONSOLE")) {
						String information = "The printing is done in " + this.displayMode;
						storeManager.printDisplayMode(pathForFile, theCommand, information, writingMode);
					} else {
						String information = "The printing is done in " + this.displayMode;
						storeManager.printDisplayMode(pathForFile, theCommand, information, writingMode);
					}
				} else {
					String information = "Unknown command! The word \"" + partsOfInput[1] + "\" seems to be misspelled.";	//* In case of a mistake, the program will try to point out a possible misspelled word.
					storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
				}
			} else if (partsOfInput[0].equals("BUY") && partsOfInput.length == 5) {					// Checking the command "BUY ${PRODUCT} ${QUANTITY} FOR ${USERNAME}"
				if (partsOfInput[3].equals("FOR")) {
					try {
						if (Integer.valueOf(partsOfInput[2]) > 0 & isNumeric(partsOfInput[2])) {
							int quantity = Integer.valueOf(partsOfInput[2]);
							storeManager.buyProductQuantityForUser(partsOfInput[1], quantity, partsOfInput[4].toLowerCase(), pathForFile, theCommand, writingMode);
						} else {
							String information = "The quantity needs cannot be ZERO. Please enter a value greater than zero.";
							storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
						}
					} catch (Exception e) {
						String information = "The quantity needs to be positive and a whole numerical value. Please try again.";
						storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
					}
				} else {
					String information = "Unknown command! The word \"" + partsOfInput[3] + "\" seems to be misspelled.";		//* In case of a mistake, the program will try to point out a possible misspelled word.
					storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
				}

			} else if (partsOfInput[0].equals("REPLENISH") && partsOfInput.length == 3) {					// Checking the command "REPLENISH ${PRODUCT} ${QUANTITY}"
				try {
					if (Integer.valueOf(partsOfInput[2]) > 0 & isNumeric(partsOfInput[2])) {
						int quantity = Integer.valueOf(partsOfInput[2]);
						storeManager.replenishProductQuantity(partsOfInput[1], quantity, pathForFile, theCommand, writingMode);
					}
				} catch (Exception e) {
					String information = "The entered quantity needs to be positive and a whole numerical value. Please try again";
					storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
				}
			} else if (partsOfInput[0].equals("ADD") & partsOfInput[1].equals("NEW") & partsOfInput.length >= 3) {			// Checking the command "ADD NEW CATEGORY ${NAME}"
				if (partsOfInput[2].equals("CATEGORY") && partsOfInput.length == 4) {
					if (storeManager.isCategoryAttribuitedToAnyProduct(partsOfInput[3]) == false) {
						this.categoriesBuffer.add(partsOfInput[3]);
						String information = "The new category was added successfully";
						storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
					} else {
						String information = "The entered category already exists in the shop. Please check the name and try again";
						storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
					}
				} else if (partsOfInput[2].equals("PRODUCT") && partsOfInput.length == 7) {					// Checking the command "ADD NEW PRODUCT ${NAME} ${CATEGORY} ${QUANTITY} ${PRICE}"
					try {
						if (storeManager.isCategoryAttribuitedToAnyProduct(partsOfInput[4]) == false) {
							if (!categoriesBuffer.contains(partsOfInput[4])) {
								String information = "The product \"" + partsOfInput[3]
										+ "\" couldn't be added. You need first to create the category \"" + partsOfInput[4] + "\" for it.";
								storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
							} else {
								storeManager.addNewProduct(partsOfInput[3], partsOfInput[4], Integer.valueOf(partsOfInput[5]), Integer.valueOf(partsOfInput[6]), pathForFile, theCommand, writingMode);
							}
						} else {
							storeManager.addNewProduct(partsOfInput[3], partsOfInput[4], Integer.valueOf(partsOfInput[5]), Integer.valueOf(partsOfInput[6]), pathForFile, theCommand, writingMode);
						}
					} catch (Exception e) {
						String information = "The QUANTITY and PRICE need to be positive and a whole numerical value. Please try again";
						storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
					}
				} else if (partsOfInput.length == 5) {																			//* In case of a mistake, the program will try to point out a possible misspelled word.
					String information = "The category name is too long. Try to enter a category name made of only one word";
					storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
				} else {
					String information = "Unknown command! You might have missed entering the category name.";
					storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
				}
			} else if (partsOfInput[0].equals("REMOVE") && partsOfInput[1].equals("PRODUCT") && partsOfInput.length == 3) {		// Checking the command "REMOVE PRODUCT ${NAME}"
				storeManager.removeProduct(partsOfInput[2], pathForFile, theCommand, writingMode);
			} else if (partsOfInput[0].equals("EXPORT") && partsOfInput.length == 2) {							// Checking the command "EXPORT ${CALE_CATRE_FISIER}"
				String path = partsCommandNormal[1];

				//String path = "G:\\PROGRAMARE\\INTERNSHIP StarStorage\\export_json\\exportedJson.json";
				JavaToJsonJsonToJava convertorToJson = new JavaToJsonJsonToJava(path, storeManager.getStore());					
				convertorToJson.ConvertJavaObjectToJson();
			} else if (partsOfInput[0].equals("SWITCH") && partsOfInput[1].equals("DISPLAY_MODE") && partsOfInput[2].equals("FILE") && partsOfInput.length == 4) {   // Checking the command "SWITCH DISPLAY_MODE CONSOLE" or "SWITCH DISPLAY_MODE FILE ${CALE_CATRE_FISIER}" \'/
				this.displayMode = "FILE" + " " + partsCommandNormal[3];					
				String information = "The command was successful. Now you are printing in " + "FILE" + " " + partsCommandNormal[3];
				storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
				this.writingMode = false;
			} else if (partsOfInput[0].equals("SWITCH") && partsOfInput[1].equals("DISPLAY_MODE") && partsOfInput[2].equals("FILE") && partsOfInput.length > 4) {
				String information = "Command couldn't be processed. Try to enter a adress without any \" \" (space) between the name of any directory";
				storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
			} else if (partsOfInput[0].equals("SWITCH") && partsOfInput[1].equals("DISPLAY_MODE") && partsOfInput[2].equals("CONSOLE") && partsOfInput.length == 3) {  // Checking the command "SWITCH DISPLAY_MODE CONSOLE" or "SWITCH DISPLAY_MODE FILE ${CALE_CATRE_FISIER}"
				this.displayMode = partsOfInput[2];
				this.writingMode = true;
				String information = "The command was successful. Now you are printing in CONSOLE";
				storeManager.printingInFile(information, pathForFile, theCommand, writingMode);					
			} else if (partsOfInput[0].equals("SWITCH") && partsOfInput[1].equals("DISPLAY_MODE") && partsOfInput[2].equals("CONSOLE") && partsOfInput.length > 3) {
				String information = "Command couldn't be processed. Try to enter just \"CONSOLE\", and nothing after.";
				storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
			} else {
				String information = "Unknown command! The word \"" + partsOfInput[0] + "\", " + "\"" + partsOfInput[1] + "\" or something else, seems to be misspelled.";
				storeManager.printingInFile(information, pathForFile, theCommand, writingMode);
			}			
		}
		
		System.out.println("\nThank you for utilizing the program.");
	}


	public static String getHelp() {										// Implementing the HELP command, which provides information about available command and their description
		StringBuffer output = new StringBuffer();

		output.append("Available commands:\n" 
				+ "\n1.\tPRINT PRODUCTS CATEGORY ${CATEGORY_NAME}"
				+ "\n\t(Se vor afisa produsele de la categoria respectiva)" 
				+ "\n\n2.\tPRINT PRODUCTS ALL"
				+ "\n\t(Se vor afisa toate produsele din gestiunea magazinului, insotite de categorie, pret si cantitate)"
				+ "\n\n3.\tPRINT PRODUCTS ${PRODUCTS_NAME}"
				+ "\n\t(Se va afisa produsul indicat, urmat de cantitata disponibila si pret)"
				+ "\n\n4.\tPRINT CATEGORIES"
				+ "\n\t(Se vor afisa toate categoriile din gestiunea magazinului)"
				+ "\n\n5.\tBUY ${PRODUCT} ${QUANTITY} FOR ${USERNAME}"
				+ "\n\t(Se va cumpara un produs din magazin, pentru un utilizator, daca sold-ul si stoc-ul permite)"
				+ "\n\n6.\tREPLENISH ${PRODUCT} ${QUANTITY}"
				+ "\n\t(Se va actualiza cantitatea produsului respectiv, daca maxQuantity permite)"
				+ "\n\n7.\tADD NEW CATEGORY ${NAME}"
				+ "\n\t(Se va adauga o noua categorie, doar daca aceasta nu exista deja in sistem)"
				+ "\n\n8.\tADD NEW PRODUCT ${NAME} ${CATEGORY} ${QUANTITY} ${PRICE}"
				+ "\n\t(Se va adauga un nou tip de produs, doar daca acesta nu exista deja in sistem, la o categorie existenta)"
				+ "\n\n9.\tREMOVE PRODUCT ${NAME}"
				+ "\n\t(Se va elimina produsul respectiv din lista de produse dispobibile, doar cand cantitatea este 0)"
				+ "\n\n10.\tPRINT DISPLAY_MODE"
				+ "\n\t(Se va indica modul selectat de afisare a rezultatelor, initial in CONSOLA, ori in FILE ${CALE_CATRE_FISIER})"
				+ "\n\n11.\tSWITCH DISPLAY_MODE CONSOLE sau FILE ${CALE_CATRE_FISIER}"
				+ "\n\t(Se va selecta modul de afisare a rezultatelor comenzilor, in CONSOLA ori in FILE ${CALE_CATRE_FISIER})"
				+ "\n\n12.\tEXPORT ${FILE_NAME}"
				+ "\n\t(Se vor exporta toate datele despre gestiunea magazinului in un fisier \"store.json\" aflat in directorul principal"
				+ "\n\n13.\tX"
				+ "\n\t(Tastati X si apasati ENTER pentru a iesi din program)");

		return output.toString();
	}

	public static boolean isNumeric(String strNum) {		// Checking if the introduced number is numerical and an Integer 
		if (strNum == null) {
			return false;
		}
		try {
			@SuppressWarnings("unused")
			int number = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}