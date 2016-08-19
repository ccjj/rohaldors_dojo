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
    
    private static ArrayList<Command> undoActions = new ArrayList<>();
    private static ArrayList<Command> redoActions = new ArrayList<>();
    
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
		undoActions.add(command);
	}
	
	public void executeCommand(Command c) {
            //int len = actions.size();
            //Command c = actions.get(len - 1);
            c.execute();
            //actions.remove(len - 1);
	}
    
       
        public void undo(Command c){
           c.undo();
            
        }
        
        public void undoLast(){
            Command c = undoActions.get(undoActions.size() - 1);
            c.undo();
            undoActions.add(c);
            redoActions.remove(c);
            
        }
        
        public void redo(Command c){
            
        }
        
        public void addAndExec(Command c){
            c.execute();
            undoActions.add(c);
        }

}
