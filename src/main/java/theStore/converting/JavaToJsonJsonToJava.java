package theStore.converting;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.annotation.JsonInclude;

import theStore.logic.TheStore;

public class JavaToJsonJsonToJava {
	private String path;
	private TheStore store;
	
	public JavaToJsonJsonToJava(String pathToFile, TheStore store) {
		this.path = pathToFile;
		this.store = store;
	}
	
	
	public void ConvertJavaObjectToJson() {											// This method has the role to convert the "TheStore " java objects to JSON type of file
		
		ObjectMapper mapper = new ObjectMapper();
        				
        try {
        	// Java objects to JSON file
            mapper.writeValue(new File(path), this.store);
            
            // Java objects to JSON string - pretty-print
//            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.store);
//            System.out.println(jsonString);
            
            System.out.println("Export succeded!");
        } catch (IOException e) {
        	System.out.println("The file couldn't be converted. Please try again\n");
            //e.printStackTrace();
        }
	}
	
	public TheStore ConvertJsonToJavaObject() {										// This method had the role to convert the JSON type of file to the "TheStore" java object
				
		ObjectMapper mapper = new ObjectMapper();		
		
		try {
            // JSON file to Java object
			
			this.store = mapper.readValue(new File(path), TheStore.class);
			
            /*TheStore newStoreObject = mapper.readValue(new File(path), TheStore.class);
            this.store = newStoreObject;*/
            			
            System.out.println("Initialization succeded!");            

        } catch (IOException e) {
        	System.out.println("The file couldn't be found. Please try again\n");
            //e.printStackTrace();
        }
		return this.store;
	}
}
