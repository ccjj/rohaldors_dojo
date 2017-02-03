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
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 *
 * @author Omar
 */
public class PersonCollection extends AbstractTableModel implements Serializable {

    private static ArrayList<Person> allyList = new ArrayList<>();
    private static ArrayList<Person> foeList = new ArrayList<>();

    public void resetPerson(Person p) {

        ICommand redoit = () -> {
            p.setLP(p.getMAXLP());
            p.setWUNDEN(0);
            p.setINI(p.getBASEINI());
            updatePerson(p);
            return false;
        };
        ICommand undoit = () -> {
            int currentLP = p.getLP();
            int currentWunden = p.getWUNDEN();
            int currentINI = p.getINI();
            p.setLP(currentLP);
            p.setWUNDEN(currentWunden);
            p.setINI(currentINI);
            updatePerson(p);
            return false;
        };
        RedoCommander.getInstance().addCommand(undoit, redoit);
        redoit.apply();

    }

    private void updatePerson(Person p) {
        int index = persons.indexOf(p);
        assert index != -1;
        fireRowUpdated(index);
    }
    private String[] columnNames;// = new String[]{
    //     "NAME", "AT", "PA", "INI", "BASEINI", "RS", "LP", "MAXLP", "WUNDEN", "WS", "ASP", "ALLY", "BONUSAKTIONEN", "KAMPF_GEGEN", "MR", "AU", "GS", "TP"
    // };
    private Class[] columnClasses;

    private RedoCommander redoCommander;

    private Color tmpColor = new Color(237, 237, 111);
    private Color tmpSelectedColor = new Color(222, 216, 60);
    private Color foeColor = new Color(110, 230, 110);
    private Color foeSelectedColor = new Color(54, 139, 54);
    private Color allyColor = new Color(237, 111, 111);
    private Color allySelectedColor = new Color(191, 41, 41);

    //private final Map<String, String> columnDict = new HashMap<>(columnNames.length);
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
        LinkedHashMap<String, Class<?>> tmpSet = new LinkedHashMap<>();
        persons = new ArrayList<Person>();
        getFoeList().add(null);
        getAllyList().add(null);

        tmpSet.put("NAME", String.class);
        tmpSet.put("AT", Integer.class);
        tmpSet.put("PA", Integer.class);
        tmpSet.put("INI", Integer.class);
        tmpSet.put("BASEINI", Integer.class);
        tmpSet.put("RS", Integer.class);
        tmpSet.put("LP", Integer.class);
        tmpSet.put("MAXLP", Integer.class);
        tmpSet.put("WUNDEN", Integer.class);
        tmpSet.put("WS", Integer.class);
        tmpSet.put("ASP", Integer.class);
        tmpSet.put("ALLY", Integer.class);
        tmpSet.put("BONUSAKTIONEN", Integer.class);
        tmpSet.put("KAMPF_GEGEN", Person.class);
        tmpSet.put("MR", Integer.class);
        tmpSet.put("AU", Integer.class);
        tmpSet.put("GS", Integer.class);
        tmpSet.put("TP", String.class);
        tmpSet.put("ANGEGRIFFEN", Boolean.class);
        tmpSet.put("PARIERT", Boolean.class);
        tmpSet.put("NOTIZ", String.class);

        columnNames = tmpSet.keySet().toArray(new String[tmpSet.size()]);
        columnClasses = tmpSet.values().toArray(new Class[tmpSet.size()]);
        //Map<String, BoolFunction> columnFuncs = new HashMap<>(columnNames.length);
        //columnDict.put("NAME", personHasChanged());
    }

    public void setComboBoxModels(PersonComboListModel fModel, PersonComboListModel aModel) {
        foeModel = fModel;
        allyModel = aModel;
    }

    public int getKAMPF_GEGENColumnIndex() {
        return Arrays.asList(columnNames).indexOf("KAMPF_GEGEN");
        //return  java.util.Arrays.asList(columnNames).indexOf("KAMPF_GEGEN");

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
        String columnName = getColumnName(columnIndex);
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

        switch (columnName) {
            case "NAME":
                return row.getNAME();
            case "AT":
                return row.getAT();
            case "PA":
                return row.getPA();
            case "INI":
                return row.getINI();
            case "BASEINI":
                return row.getBASEINI();
            case "RS":
                return row.getRS();
            case "LP":
                return row.getLP();
            case "MAXLP":
                return row.getMAXLP();
            case "WUNDEN":
                return row.getWUNDEN();
            case "WS":
                return row.getWS();
            case "ASP":
                return row.getASP();
            case "ALLY":
                return row.getALLY();
            case "BONUSAKTIONEN":
                return row.getAKTIONEN();
            case "KAMPF_GEGEN":
                return row.getKAMPF_GEGEN();
            case "MR":
                return row.getMR();
            case "AU":
                return row.getAU();
            case "GS":
                return row.getGS();
            case "TP":
                return row.getTP();
            case "ANGEGRIFFEN":
                return row.getAngegriffen();
            case "PARIERT":
                return row.getPariert();
            case "NOTIZ":
                return row.getNotiz();
            default:
                throw new RuntimeException("Could not find row " + columnName);
        }

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
        String columnName = getColumnName(columnIndex);
        ICommand redoit = null;
        ICommand undoit = null;
        boolean hasChanged = false;
        Object oldValue = this.getValueAt(rowIndex, columnIndex);
        String oldVal = "nichts";
        try {
            oldVal = this.getValueAt(rowIndex, columnIndex).toString();
        } catch (Exception e) {
            //System.out.println("NULL");
        }
        Person row = persons.get(rowIndex);

        if (row.getISTMP() > 0 && columnIndex != getIndex("INI") && columnIndex != getIndex("ANGEGRIFFEN") && columnIndex != getIndex("PARIERT")) {
            return; //tmp felder können nicht beschrieben werden, und nicht ini die man schreiben will
        }

        switch (columnName) {
            case "NAME":
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
            case "AT":
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
            case "PA":
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
            case "INI":
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
            case "BASEINI":
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
            case "RS":
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
            case "LP":
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
            case "MAXLP":
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
            case "WUNDEN":
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
            case "WS":
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
            case "ASP":
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
            case "ALLY":
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
            case "BONUSAKTIONEN":
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
            case "KAMPF_GEGEN":
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
            case "MR":
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
            case "AU":
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
            case "GS":
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
            case "TP":
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
            case "ANGEGRIFFEN":
                redoit = () -> {
                    boolean b = row.setAngegriffen(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setAngegriffen(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case "PARIERT":
                redoit = () -> {
                    boolean b = row.setPariert(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setPariert(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            case "NOTIZ":
                redoit = () -> {
                    boolean b = row.setNotiz(value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                undoit = () -> {
                    boolean b = row.setNotiz(oldValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    return b;
                };
                hasChanged = redoit.apply();
                break;
            default:
                throw new RuntimeException("Could not find row " + columnName);
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
            if (oldVal.equals("true")) {
                oldVal = "ja";
            }
            if (newval.equals("true")) {
                newval = "ja";
            }
            if (oldVal.equals("false")) {
                oldVal = "nein";
            }
            if (newval.equals("false")) {
                newval = "nein";
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

    public void removePerson(Person row) {
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
        //System.out.println(columnNames[colNum] + " " + columnClasses[colNum].getTypeName() + " c " + colNum);
        return columnClasses[colNum];

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

    public void resetAngPar() {
        for (Person p : persons) {
            if (p.getAngegriffen() == true) {
                p.setAngegriffen(false);
            }
            if (p.getPariert() == true) {
                p.setPariert(false);
            }
        }
    }

    public String[] getColumnNames() {
        return this.columnNames;
    }

    public int getColumnIndex(String columnName) {
        int index = Arrays.asList(columnNames).indexOf(columnName);
        if (index == -1) {
            throw new RuntimeException("Index not found for column" + columnName);
        }
        return index;
    }
}
