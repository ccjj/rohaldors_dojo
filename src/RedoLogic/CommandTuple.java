/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RedoLogic;

/**
 * 
 * @author Omar
 */
public class CommandTuple {

    final ICommand undo;
    final ICommand redo;
    
    public CommandTuple(ICommand undo, ICommand redo){
        this.undo = undo;
        this.redo = redo;
    }
    
}
