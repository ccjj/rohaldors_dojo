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
    
    private static ArrayList<ICommand> undoActions;
    private static ArrayList<ICommand> redoActions;
    private static ArrayList<CommandTuple> actions;
    private static int pointer;
    
    private RedoCommander() {
    }


    public static RedoCommander getInstance() {
        if (instance == null) {
            instance = new RedoCommander();
            undoActions = new ArrayList<ICommand>();
            redoActions = new ArrayList<ICommand>();
            actions = new ArrayList<CommandTuple>();
            pointer = 0;
        }
        return instance;
    }
    
    public void undoLast(){
        if(pointer < 1) return;
        actions.get(pointer-1).undo.apply();
        pointer--;
        System.out.println(pointer);
    }
    
    public void redoLast(){
        if(pointer >= getActionsSize()) return;
        actions.get(pointer).redo.apply();
        pointer++;
        //System.out.println("p " + pointer);
        //System.out.println("a " + actionSize);
    }
    
    
    public void addCommand(ICommand undo, ICommand redo){
        CommandTuple cmd = new CommandTuple(undo, redo);
        int actionsSize = actions.size();
        int remItemsCounter = actionsSize - pointer;
        int i = 1;
        for (; remItemsCounter > 0; remItemsCounter--){
            actions.remove(actionsSize-i);
            i++;
            
        }
        
        
        actions.add(cmd);
        //alles über pointer, del
        pointer++;
    }
    
    public int getActionsSize(){
        return RedoCommander.actions.size();
    }
    

}
