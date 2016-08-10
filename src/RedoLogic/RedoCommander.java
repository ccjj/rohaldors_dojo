/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedoLogic;

import java.util.ArrayList;

/**
 *
 * @author Omar
 */
public class RedoCommander {

    private static RedoCommander instance = null;
    
    private ArrayList<Command> actions = new ArrayList<>();
    
    
    private RedoCommander() {
    }

    /**
     *
     * @return
     */
    public RedoCommander getInstance() {
        if (instance == null) {
            instance = new RedoCommander();
        }
        return instance;
    }
    
    
    
    	public void addCommand(Command command) {
		actions.add(command);
	}
	
	public void executeCommand(Command c) {
            //int len = actions.size();
            //Command c = actions.get(len - 1);
            c.execute();
            //actions.remove(len - 1);
	}
    
       
        public void undo(Command c){
            //c.
        }
        
        public void redo(Command c){
            
        }

}
