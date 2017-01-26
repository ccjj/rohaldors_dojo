/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easykampf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author Omar
 */
public class Version {
    private static String version;
    
    public static String getVersion(){
        version = "v 1." + new SimpleDateFormat("MMddyyHHmm").format(Calendar.getInstance().getTime());
        return version;
    }

}
