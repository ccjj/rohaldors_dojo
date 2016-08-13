/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easykampf;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author cje
 */
public class TextLogger {
    private static TextLogger instance = null;
    private static JEditorPane textLog;
    private static HTMLEditorKit kit;
    private static StyleSheet styleSheet;
    HTMLDocument doc;
    
    public static TextLogger getInstance(){
        if(instance == null)
            instance = new TextLogger();
        return instance;
    }
    
    private TextLogger(){
    kit = new HTMLEditorKit();
    styleSheet = kit.getStyleSheet();
    }
    
    public void setTextField(JEditorPane textLog) {
        this.textLog = textLog;
        this.textLog.setContentType("text/html");
        textLog.setEditorKit(kit);
        doc = new HTMLDocument();
        textLog.setDocument(doc);
        styleSheet.addRule("#bluefont {color :  #6699ff;}");
        styleSheet.addRule("#redfont {color :  #ff5050;}");
    }
    
    public void add(String msg){
       add(msg, "#000000");
    }
    
    public void add(String msg, String color){
               try {
            //textLog.setText(msg + "\n" + textLog.getText());
            //HTMLDocument msg2 = (HTMLDocument) textLog.getDocument();// + "\n" + "<div id='bluefont'>" + msg + "</div>";
            //msg2.setInnerHTML(null, msg);
            //textLog.setText(doc.toString());
            kit.insertHTML(doc, doc.getLength(), "\n<font color='" + color + "'>" + msg + "</font>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(TextLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void clear(){
        textLog.setText(null);
    }
    
}
