/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RedoLogic;

import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author Omar
 */
public class PointerObserver implements Observer {
    private int pointer;
    
    @Override
    public void update(Observable o, Object arg) {
        
        if (arg instanceof Integer) {
       pointer = (int) arg;
    
        }
    }    

}
