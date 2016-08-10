/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easykampf;

import javax.swing.JTextArea;

/**
 *
 * @author cje
 */
public class TextLogger {
    private static TextLogger instance = null;
    private static JTextArea textLog;
    
    
    public static TextLogger getInstance(){
        if(instance == null)
            instance = new TextLogger();
        return instance;
    }
    
    private TextLogger(){}
    
    public void setTextField(JTextArea textLog) {
        this.textLog = textLog;
    }
    
    public void add(String msg){
        //textLog.setText(msg + "\n" + textLog.getText());
        textLog.setText(textLog.getText() + "\n" + msg);
    }
    
    public void clear(){
        textLog.setText(null);
    }
    
}
