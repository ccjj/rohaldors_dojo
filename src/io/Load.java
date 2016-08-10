package io;

import java.io.*;

public class Load {

	public static Object load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream objIn = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(filename)));
		Object returned = objIn.readObject();
		objIn.close();
		return returned;
	}

}
