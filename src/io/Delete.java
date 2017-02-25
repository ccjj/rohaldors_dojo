/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Omar
 */
public class Delete {

    public static void delete(String filename) throws FileNotFoundException, IOException {

        File f = new File(filename);
        // Make sure the file or directory exists and isn't write protected
        if (!f.exists()) {
            throw new IllegalArgumentException(
                    "Delete: no such file or directory: " + filename);
        }

        if (!f.canWrite()) {
            throw new IllegalArgumentException("Delete: write protected: "
                    + filename);
        }

        // If it is a directory, make sure it is empty
        if (f.isDirectory()) {
            String[] files = f.list();
            if (files.length > 0) {
                throw new IllegalArgumentException(
                        "Delete: directory not empty: " + filename);
            }
        }

        // Attempt to delete it
        boolean success = f.delete();

        if (!success) {
            throw new IllegalArgumentException("Delete: deletion failed");
        }
    }

}
