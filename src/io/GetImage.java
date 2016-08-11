/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io;

import java.io.FileNotFoundException;
import java.net.URL;


public class GetImage {
    public static URL getImage(String iName) {
        URL imgURL = ClassLoader.getSystemResource(iName);
        if (imgURL == null){
             throw new RuntimeException("File not found");
        }
        return imgURL; 
    }
}
