package persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
	public static boolean createFile(String name) {
		boolean success = false;
		
		try {
			File file = new File(name);
			
			if (file.createNewFile()) {
				success = true;
			} else {
				success = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}
		
		return success;
	}
	
	public static boolean writeInFile(String name, String content) {
		boolean success = false;
		
		if (content != null && content.length() > 0) {
			try (BufferedWriter wr = Files.newBufferedWriter(Paths.get(name), StandardCharsets.UTF_8)) {
	            wr.write(content);
	            
	            success = true;
	        } catch (IOException e) {
				e.printStackTrace();
				
				success = false;
			}
			/*try {
				/*FileWriter fileWriter = new FileWriter(name);
				fileWriter.write(content);
				fileWriter.close();
	
				success = true;
			} catch (IOException e) {
				e.printStackTrace();
				
				success = false;
			}*/
		}
		
		return success;
	}
	
	public static boolean deleteFile(String name) {
		boolean success = false;
		
		File fileToDelete = new File(name);
		
		if(fileToDelete.delete()) {
			success = true;
		} else {
			success = false;
		}
		
		return success;
	}
}
