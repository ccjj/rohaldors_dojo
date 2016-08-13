/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.UIManager;

/**
 *
 * @author essi
 */
public class HelpFrame extends JFrame {
    
    public static void showHelpFrame(){
        
             String help = "<html><style>\n"
                + "	.demo {\n"
                + "		width:100%;\n"
                + "	}\n"
                + "</style>\n"
                + "<table class=\"demo\">\n"
                + "	<caption><b>About</b></caption>\n"
                + "	<thead>\n"
                + "	<tr>\n"
                + "		<th>Shortkeys:</th>\n"
                + "		<th><br></th>\n"
                + "	</tr>\n"
                + "	</thead>\n"
                + "	<tbody>\n"
                + "	<tr>\n"
                + "		<td><b>Ctrl + C</b><br></td>\n"
                + "		<td>&nbsp;<i>Copy the selected entities to clipboard (Table only).</i></td>\n"
                + "	</tr>\n"
                + "	<tr>\n"
                + "		<td>&nbsp;<b>Ctrl + V</b><br></td>\n"
                + "		<td>&nbsp;<i>Paste the selected entities from clipboard (Table only) . It is even possible to paste data from Excel or from a textfile, as long as the rows are seperated by newlines, the data is seperated by tabulators, the values are valid, and the amount of columns is the same as in the table.</i><br></td>\n"
                + "	</tr>\n"
                + "	<tr>\n"
                + "		<td>&nbsp;<b>+</b></td>\n"
                + "		<td>&nbsp;<i>Add a new entity (Table only).</i><br></td>\n"
                + "	</tr>\n"
                + "	<tr>\n"
                + "		<td>&nbsp;<b>-</b></td>\n"
                + "		<td>&nbsp;<i>Delete an entity.</i><br></td>\n"
                + "	</tr>\n"
                + "	<tbody>\n"
                + "</table>\nIcon made by Freepik from www.flaticon.com \n\nFor Wahnfried. He was the best of us.</html>";

        final JFrame detailFrame = new JFrame("Help");
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setSize(300, 400);
        detailFrame.setResizable(false);
        Box contentBox = Box.createVerticalBox();
        JTextPane contentPane = new JTextPane();
        contentPane.setContentType("text/html");
        contentPane.setEditable(false);
        contentPane.setText(help);
        contentBox.add(contentPane);
        detailFrame.add(contentBox);
        contentPane.setBackground(UIManager.getColor("Label.background"));
        detailFrame.setLocationRelativeTo(null);
        detailFrame.setVisible(true);
        
    }
       
}
