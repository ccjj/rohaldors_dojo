package easykampf;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class Save {
	
	/**
	 * Saves a given String to a given Filename
	 * @param filename the destination location of the file
	 * @param saveString the string to save
	 * @throws IOException if the computer is not ready
	 */
	public static void saveAsTxtFile(String filename, String saveString) throws IOException{
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
		out.write(saveString);
		out.close();
	}
	
	public static void saveAsFile(String filename, Object object) throws FileNotFoundException, IOException{
		ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(
				new FileOutputStream(filename)));
		objOut.writeObject(object);
		objOut.close();
	}


}
