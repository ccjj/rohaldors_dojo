/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.table.TableColumn;

/**
 * 
 * @author Omar
 */
public class MenuItem extends JCheckBoxMenuItem {
private TableColumn column;
private int maxWidth, minWidth;
public int width;

    MenuItem(String s) {
        super(s);
        super.setSelected(true);
    }

    /**
     * @return the column
     */
    public TableColumn getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(TableColumn column) {
        this.column = column;
    }

    /**
     * @return the maxWidth
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * @param maxWidth the maxWidth to set
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * @return the minWidth
     */
    public int getMinWidth() {
        return minWidth;
    }

    /**
     * @param minWidth the minWidth to set
     */
    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

}
