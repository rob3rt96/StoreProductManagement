package theStore.main;

import java.util.Scanner;

import theStore.converting.JavaToJsonJsonToJava;
import theStore.logic.TheStore;
import theStore.logic.TheStoreManager;
import theStore.ui.TextUI;

/**
 * 
 * This class has the role to initialize the Store with the data from the JSON file and then to start the Text UserInterface
 *
 */

public class TheStoreProgram {

	public static void main(String[] args) {
		TheStore store = new TheStore();
		
		// To be able to run this program from an IDE, enter the address to the JSON file, like you can see beneath this.
		JavaToJsonJsonToJava convert = new JavaToJsonJsonToJava("D:\\Robert\\PROGRAMARE JAVA\\INTERNSHIP StarStorage\\store.json", store);
		store = convert.ConvertJsonToJavaObject();
		
				
		
//		try {																						// This block receives the parameter from the args array which is passed to the "main" function,
//			JavaToJsonJsonToJava convert = new JavaToJsonJsonToJava(args[0], store);				//  which represents the location to the JSON File. Then the file is converted to the java TheStore object
//			store = convert.ConvertJsonToJavaObject();
//		} catch (Exception e) {
//			System.out.println("You forgot to import the JSON file. It needs to be (e.g. \"store.json\"");
//		}
		
		TheStoreManager manager = new TheStoreManager(store);		
		
		Scanner scanner = new Scanner(System.in);
		new TextUI(scanner, manager).start();
	}
}
