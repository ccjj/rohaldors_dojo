/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easykampf;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Omar
 */
public class PersonCollection extends AbstractTableModel implements Serializable {

    public static ArrayList<Person> persons = new ArrayList<>();
    private static PersonCollection instance;
    private static final long serialVersionUID = 2L;
    private final String[] columnNames;
    //todo old aktionen val, array of clones?

    public static PersonCollection getInstance() {
        if (instance == null) {
            instance = new PersonCollection();
        }
        return instance;
    }

    public PersonCollection() {
        this.columnNames = new String[]{
            "NAME", "AT", "PA", "INI", "RS", "LP", "MAXLP", "WUNDEN", "WS", "ASP", "ALLY", "BONUSAKTIONEN"
        };
    }

    public void addPerson() {
        Person p = new Person();
        persons.add(p);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return persons.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person row = persons.get(rowIndex);
        /*
         Method method = null;
         String retVal = null;
         String methodname = "get" + columnNames[columnIndex];
         try {
         method = row.getClass().getMethod(methodname);
         } catch (NoSuchMethodException | SecurityException ex) {
         Logger.getLogger(PersonCollection.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         Object v = method.invoke(row);
         try {
         retVal = (String) v;
         } catch (Exception e) {
         int tmp = (int) v;
         retVal = Integer.toString(tmp);
         }

         } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
         Logger.getLogger(PersonCollection.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        if (0 == columnIndex) {
            return row.getNAME();
        } else if (1 == columnIndex) {
            return row.getAT();
        } else if (2 == columnIndex) {
            return row.getPA();
        } else if (3 == columnIndex) {
            return row.getINI();
        } else if (4 == columnIndex) {
            return row.getRS();
        } else if (5 == columnIndex) {
            return row.getLP();
        } else if (6 == columnIndex) {
            return row.getMAXLP();
        } else if (7 == columnIndex) {
            return row.getWUNDEN();
        } else if (8 == columnIndex) {
            return row.getWS();
        } else if (9 == columnIndex) {
            return row.getASP();
        } else if (10 == columnIndex) {
            return row.getALLY();
        } else if (11 == columnIndex) {
            return row.getAKTIONEN();
        }
        return null;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Person row = persons.get(rowIndex);
        System.out.println(row.getISTMP());
        if (row.getISTMP() > 0 && columnIndex != 3) {
            return; //tmp felder können nicht beschrieben werden, und nicht ini die man schreiben will
        }

        if (0 == columnIndex) {
            row.setNAME(value);
        } else if (1 == columnIndex) {
            row.setAT(value);
        } else if (2 == columnIndex) {
            row.setPA(value);
        } else if (3 == columnIndex) {
            row.setINI(value);
        } else if (4 == columnIndex) {
            row.setRS(value);
        } else if (5 == columnIndex) {
            row.setLP(value);
        } else if (6 == columnIndex) {
            row.setMAXLP(value);
        } else if (7 == columnIndex) {
            row.setWUNDEN(value);
        } else if (8 == columnIndex) {
            row.setWS(value);
        } else if (9 == columnIndex) {
            row.setASP(value);
        } else if (10 == columnIndex) {
            row.setALLY(value);
        } else if (11 == columnIndex) {
            try {
                int aktionen = Integer.parseInt(value.toString());
                row.setAKTIONEN(aktionen);
                addClones(row, aktionen);
            } catch (Exception e) {
            } finally {
                return;
            }

        }

        fireTableCellUpdated(rowIndex, columnIndex);

    }

    @Override
    public boolean isCellEditable(int row, int columnIndex) {
        //return columnIndex < 9;
        return true;
    }

    Color getRowColour(int rowIndex) {
        Person row = persons.get(rowIndex);
        return row.getColor();
    }

    void applyPersonDmg(int selectedRow, int dmg, boolean selected) {
        Person row = persons.get(selectedRow);
        row.applyDmg(dmg, selected);
        fireTableRowsUpdated(selectedRow, selectedRow);
    }

    void removePerson(int selectedRow) {
        Person row = persons.get(selectedRow);
        persons.remove(row);
        fireTableRowsDeleted(selectedRow, selectedRow);
    }

    public void addPerson(Person p) {
        this.persons.add(p);
    }

    private void addClones(Person row, int aktionen) {
        if (aktionen < 1) {
            return;
        }
        try {
            for (int a = 1; a <= aktionen; a++) {
                Person c = (Person) row.clone();
                c.setTMP();
                c.setNAME(row.getNAME() + " Aktion " + Integer.toString(a));
                PersonCollection.getInstance().addPerson(c);
            }

        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        fireTableDataChanged();
    }

    public void removeTmpPersons() {
        Iterator<Person> it = persons.iterator();
        while (it.hasNext()) {
            Person p = it.next();
            if (p.getISTMP() > 0) {
                it.remove();
            } else {
                p.setAKTIONEN(0);
            }
        }
    }

}
