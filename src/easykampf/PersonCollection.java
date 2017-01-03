/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easykampf;

import RedoLogic.RedoCommander;
import functionInterface.BoolFunction;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import ui.GUI;
import RedoLogic.ICommand;
import RedoLogic.PersonCommand;

/**
 *
 * @author Omar
 */
public class PersonCollection extends AbstractTableModel implements Serializable {

    private static ArrayList<Person> allyList = new ArrayList<>();
    private static ArrayList<Person> foeList = new ArrayList<>();
    private final String[] columnNames = new String[]{
        "NAME", "AT", "PA", "INI", "BASEINI", "RS", "LP", "MAXLP", "WUNDEN", "WS", "ASP", "ALLY", "BONUSAKTIONEN", "KAMPF_GEGEN", "MR", "AU", "GS", "TP"
    };

    private RedoCommander redoCommander;

    private Color tmpColor = new Color(237, 237, 111);
    private Color tmpSelectedColor = new Color(222, 216, 60);
    private Color foeColor = new Color(110, 230, 110);
    private Color foeSelectedColor = new Color(54, 139, 54);
    private Color allyColor = new Color(237, 111, 111);
    private Color allySelectedColor = new Color(191, 41, 41);

    private final Map<String, String> columnDict = new HashMap<>(columnNames.length);

    //TODO jeder columne einen index zuweisen
    static PersonComboListModel foeModel;
    static PersonComboListModel allyModel;

    private static ArrayList<Person> persons;

    /**
     * @return the allyList
     */
    public static ArrayList<Person> getAllyList() {
        return allyList;
    }

    /**
     * @param aAllyList the allyList to set
     */
    public static void setAllyList(ArrayList<Person> aAllyList) {
        allyList = aAllyList;
    }

    /**
     * @return the foeList
     */
    public static ArrayList<Person> getFoeList() {
        return foeList;
    }

    /**
     * @param aFoeList the foeList to set
     */
    public static void setFoeList(ArrayList<Person> aFoeList) {
        foeList = aFoeList;
    }
    private final long serialVersionUID = 2L;
    //todo old aktionen val, array of clones?

    public PersonCollection() {
        this.redoCommander = RedoCommander.getInstance();
        persons = new ArrayList<Person>();
        getFoeList().add(null);
        getAllyList().add(null);

        columnDict.put("NAME", "String");
        columnDict.put("AT", "Integer");
        columnDict.put("PA", "Integer");
        columnDict.put("AT", "Integer");
        columnDict.put("INI", "Integer");
        columnDict.put("BASEINI", "Integer");
        columnDict.put("LP", "Integer");
        columnDict.put("MAXLP", "Integer");
        columnDict.put("WUNDEN", "Integer");
        columnDict.put("WS", "Integer");
        columnDict.put("BONUSAKTIONEN", "Integer");
        columnDict.put("KAMPF_GEGEN", "Person");
        columnDict.put("MR", "Integer");
        columnDict.put("AU", "Integer");
        columnDict.put("GS", "Integer");
        columnDict.put("TP", "String");

        Map<String, BoolFunction> columnFuncs = new HashMap<>(columnNames.length);
        //columnDict.put("NAME", personHasChanged());
    }

    public void setComboBoxModels(PersonComboListModel fModel, PersonComboListModel aModel) {
        foeModel = fModel;
        allyModel = aModel;
    }

    public int getKAMPF_GEGENColumnIndex() {
        //return columnNames.indexOf("KAMPF_GEGEN");
        //return  java.util.Arrays.asList(columnNames).indexOf("KAMPF_GEGEN");
        return 13;
    }

    public void addPerson() {
        Person p = new Person();
        addPerson(p);
     }

    public void addPerson(Person p) {
        this.persons.add(p);
        if (p.getISTMP() != 1) {
            if (p.getALLY() == 0) {
                getFoeList().add(p);
                foeModel.addComboListPerson();
            } else {
                getAllyList().add(p);
                allyModel.addComboListPerson();
            }
        }

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
        switch (columnIndex) {
            case 0:
                return row.getNAME();
            case 1:
                return row.getAT();
            case 2:
                return row.getPA();
            case 3:
                return row.getINI();
            case 4:
                return row.getBASEINI();
            case 5:
                return row.getRS();
            case 6:
                return row.getLP();
            case 7:
                return row.getMAXLP();
            case 8:
                return row.getWUNDEN();
            case 9:
                return row.getWS();
            case 10:
                return row.getASP();
            case 11:
                return row.getALLY();
            case 12:
                return row.getAKTIONEN();
            case 13:
                return row.getKAMPF_GEGEN();
            case 14:
                return row.getMR();
            case 15:
                return row.getAU();
            case 16:
                return row.getGS();
            case 17:
                return row.getTP();
            default:
                break;
        }
        return null; //"BONUSAKTIONEN", "KAMPF_GEGEN", "MR", "AU", "GS", "TP"
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public int getIndex(String columnName) {
        int i = 0;
        for (; i < columnNames.length; i++) {
            if (columnNames[i].equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        throw new RuntimeException("Spalte " + columnName + " nicht gefunden");
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        ICommand redoit = null;
        ICommand undoit = null;
        boolean hasChanged = false;
        Object oldValue = this.getValueAt(rowIndex, columnIndex);
        String oldVal = "nichts";
        try {
            oldVal = this.getValueAt(rowIndex, columnIndex).toString();
        } catch (Exception e) {
            System.out.println("NULL");
        }
        Person row = persons.get(rowIndex);

        if (row.getISTMP() > 0 && columnIndex != getIndex("INI")) {
            return; //tmp felder können nicht beschrieben werden, und nicht ini die man schreiben will
        }

        switch (columnIndex) {
            case 0:
                int oldGen = row.getGeneration();
                redoit = () -> {
                    row.setGeneration(0);
                    boolean b = row.setNAME(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };

                undoit = () -> {
                    boolean b = row.setNAME(oldValue);
                    row.setGeneration(oldGen);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                //TODO auch redo?

                break;
            case 1:
                redoit = () -> {
                    boolean b = row.setAT(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setAT(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 2:
                redoit = () -> {
                    boolean b = row.setPA(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setPA(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 3:
                redoit = () -> {
                    boolean b = row.setINI(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setINI(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 4:
                redoit = () -> {
                    boolean b = row.setBASEINI(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setBASEINI(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 5:
                redoit = () -> {
                    boolean b = row.setRS(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setRS(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 6:
                redoit = () -> {
                    boolean b = row.setLP(value);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setLP(oldValue);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 7:
                redoit = () -> {
                    boolean b = row.setMAXLP(value);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setMAXLP(oldValue);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 8:
                redoit = () -> {
                    boolean b = row.setWUNDEN(value);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setWUNDEN(oldValue);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 9:
                redoit = () -> {
                    boolean b = row.setWS(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setWS(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 10:
                redoit = () -> {
                    boolean b = row.setASP(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setASP(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 11:
                redoit = () -> {
                    boolean b = setPersonAlly(row, value);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = setPersonAlly(row, oldValue);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 12:
                final int aktionen;
                try {

                    aktionen = Integer.parseInt(value.toString());
                    if (aktionen <= 0) {
                        return;
                    }

                    //TODO logger text ändern
                    redoit = () -> {
                        row.setAKTIONEN(aktionen);
                        addClones(row, aktionen);
                        fireTableDataChanged();
                        return false;
                    };
                    undoit = () -> {
                        row.setAKTIONEN(0);
                        removeClones(row, aktionen);
                        fireTableDataChanged();
                        return false;
                    };
                    RedoCommander.getInstance().addCommand(undoit, redoit);
                    redoit.apply();

                } catch (Exception e) {
                } finally {
                    return;
                }
            case 13:
                redoit = () -> {
                    boolean b = row.setKAMPF_GEGEN(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setKAMPF_GEGEN(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 14:
                redoit = () -> {
                    boolean b = row.setMR(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setMR(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 15:
               redoit = () -> {
                    boolean b = row.setAU(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setAU(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 16:
               redoit = () -> {
                    boolean b = row.setGS(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setGS(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case 17:
               redoit = () -> {
                    boolean b = row.setTP(value);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setTP(oldValue);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            default:
                break; //TODO return?
        }

        if (hasChanged) {
            RedoCommander.getInstance().addCommand(undoit, redoit);
            String newval;
            try {
                newval = value.toString();
            } catch (Exception e) {
                newval = "nichts";
            }

            if (oldVal.equals("") || oldVal == null) {
                oldVal = "nichts";
            }

            String msg = row.getNAME() + " " + columnNames[columnIndex] + ": von " + oldVal + " auf " + newval + " geändert";
            TextLogger.getInstance().add(msg);
        }

    }

    public boolean setPersonAlly(Person row, Object value) {
        boolean hasChanged = row.setALLY(value);

        if (hasChanged) {
            if (row.getALLY() == 1) {

                getAllyList().add(row);
                getFoeList().remove(row);
                foeModel.removeComboListPerson(row);
                allyModel.addComboListPerson();

            } else if (row.getALLY() == 0) {
                getAllyList().remove(row);
                getFoeList().add(row);
                allyModel.removeComboListPerson(row);
                foeModel.addComboListPerson();

            }
        }

        return hasChanged;
    }

    @Override
    public boolean isCellEditable(int row, int columnIndex) {
        //return columnIndex != 13;
        return true;
    }

    public Color getRowColour(int rowIndex, boolean isSelected) {
        Person row = persons.get(rowIndex);
        return getColor(row, isSelected);

    }

    private Color getColor(Person p, boolean isSelected) {
        if (p.getISTMP() > 0) {
            if (isSelected) {
                return tmpSelectedColor;
            }
            return tmpColor;
        }

        if (p.getALLY() == 0) {
            if (isSelected) {
                return allySelectedColor;
            }
            return allyColor;
        } else {
            if (isSelected) {
                return foeSelectedColor;
            }
            return foeColor;
        }

    }

    public void applyPersonDmg(int selectedRow, int dmg, boolean ignoreWS, boolean ignoreRS) {
        ICommand redoit;
        ICommand undoit;
        //redoit = () -> row.setALLY(value);
        //undoit = () -> row.setALLY(oldvalue);
        Person row = persons.get(selectedRow);
        int[] oldWounds = new int[1];
        int[] oldLP = new int[1];
        redoit = () -> {
            oldWounds[0] = row.getWUNDEN();
            oldLP[0] = row.getLP();
            doPersonDmg(selectedRow, dmg, ignoreWS, ignoreRS);

            return false;
        };
        undoit = () -> {
            row.setWUNDEN(oldWounds[0]);
            row.setLP(oldLP[0]);
            return false;
        };
        RedoCommander.getInstance().addCommand(undoit, redoit);
        redoit.apply();

    }

    public void doPersonDmg(int selectedRow, int dmg, boolean ignoreWS, boolean ignoreRS) {
        Person row = persons.get(selectedRow);
        int oldLp = row.getLP();
        int lpNow = row.applyDmg(dmg, ignoreWS, ignoreRS);
        String msg = row.getNAME() + " erleidet " + dmg + "Schaden. LP" + ": von " + oldLp + " auf " + lpNow + " gesunken.";
        TextLogger.getInstance().add(msg);
        fireTableRowsUpdated(selectedRow, selectedRow);
    }
    
    public void removePerson(Person row){
                if (row.getALLY() == 0) {
            getFoeList().remove(row);
            foeModel.removeComboListPerson(row);

        } else {
            getAllyList().remove(row);
            allyModel.removeComboListPerson(row);
        }

        removeDeletedKAMPF_GEGEN(row);
        persons.remove(row);

        //fireTableRowsDeleted(selectedRow, selectedRow); todo
        fireTableDataChanged();
    }

    public void removePerson(int selectedRow) {
        Person row = persons.get(selectedRow);
        removePerson(row);

    }

    private void addClones(Person row, int aktionen) {
        if (aktionen < 1) {
            return;
        }//todo
        try {
            for (int a = 1; a <= aktionen; a++) {
                Person c = (Person) row.clone();
                c.setTMP();
                c.setNAME(row.getNAME() + " Aktion " + Integer.toString(a));
                this.addPerson(c);
            }

        } catch (CloneNotSupportedException ex) {
            //Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    // TODO danach genrell personen löschen/hinzufügen
    private void removeClones(Person row, int aktionen) {
        //String pname = row.getPureName();
        /*
        for(int i = persons.size() -1; i >= 0; i--){
            Person p = persons.get(i);
            if(p.getISTMP() > 0 && p.UUID == row.UUID){
                persons.remove(p);
            }
            
        }
        */
        Iterator<Person> it = persons.iterator();
        while (it.hasNext()) {
            Person p = it.next();
            if (p.getISTMP() > 0 && p.UUID == row.UUID && aktionen > 0) {
                it.remove();
                aktionen--;
            }
        }
        

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

    @Override
    public Class<?> getColumnClass(int colNum) {
        switch (colNum) {
            case 0:
                return String.class;
            case 13:
                return Person.class;
            case 17:
                return String.class;
            default:
                return Integer.class;
        }
    }

    public Person getPerson(int selectedRow) {
        return persons.get(selectedRow);
    }

    //todo refactor
    private void callTypeByColumn(int colNum) {
        Class<?> a = getColumnClass(colNum);
        if (a.isInstance(Integer.class)) {
            return;
        }
    }

    public void setPersons(ArrayList<Person> per) {
        this.persons = per;
        getAllyList().clear();
        getFoeList().clear();
        getFoeList().add(null);
        getAllyList().add(null);
        for (Person p : per) {
            if (p.getALLY() == 0) {
                getFoeList().add(p);
            } else {
                getAllyList().add(p);
            }
        }
    }

    public ArrayList<Person> getPersons() {
        return this.persons;
    }

    public void removeDeletedKAMPF_GEGEN(Person p) {
        ArrayList<Integer> changedIndices = new ArrayList<>();
        for (Person per : persons) {
            if (per.getKAMPF_GEGEN() == p) {
                per.setKAMPF_GEGEN(null);
                int index = persons.indexOf(per);
                changedIndices.add(index);

                String msg = per.getNAME() + "'s Gegner " + p.getNAME() + " gelöscht ";
                TextLogger.getInstance().add(msg);
            }

            //TODO
            for (int i : changedIndices) {

                fireTableCellUpdated(i, 13);

            }

        }
    }

    public int getEnumPersonName(Person per) {
        String pName = per.getPureName();
        int maxGen = -1;
        for (Person p : persons) {
            if (p.getPureName().equals(pName)) {
                if (p.getGeneration() > maxGen) {
                    maxGen = p.getGeneration();
                }
            }

        }
        return maxGen;
    }

    private boolean personHasChanged(BoolFunction f, Person p) {
        boolean changed = f.func(p);
        return changed;
    }

    public static Person getPersonAt(int rowIndex) {
        return persons.get(rowIndex);
    }

    public void fireRowUpdated(int row) {

        fireTableRowsUpdated(row, row);
    }
}
