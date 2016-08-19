/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easykampf;

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

    private final Map<String, String> columnDict = new HashMap<>(columnNames.length);

    //TODO jeder columne einen index zuweisen
    PersonComboListModel foeModel;
    PersonComboListModel allyModel;

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

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        boolean hasChanged = false;

        String oldVal = "nichts";
        try {
            oldVal = this.getValueAt(rowIndex, columnIndex).toString();
        } catch (Exception e) {
            System.out.println("NULL");
        }
        Person row = persons.get(rowIndex);

        if (13 == columnIndex) { //set kampf gegen

            hasChanged = row.setKAMPF_GEGEN(value);

            //TODO remove from all persons durch setter/getter? eigene klasse?
        }

        if (row.getISTMP() > 0 && columnIndex != 3) {
            return; //tmp felder können nicht beschrieben werden, und nicht ini die man schreiben will
        }

        switch (columnIndex) {
            case 0:
                hasChanged = row.setNAME(value);
                row.setGeneration(0);
                break;
            case 1:
                hasChanged = row.setAT(value);
                break;
            case 2:
                hasChanged = row.setPA(value);
                break;
            case 3:
                hasChanged = row.setINI(value);
                break;
            case 4:
                hasChanged = row.setBASEINI(value);
                break;
            case 5:
                hasChanged = row.setRS(value);
                break;
            case 6:
                hasChanged = row.setLP(value);
                break;
            case 7:
                hasChanged = row.setMAXLP(value);
                break;
            case 8:
                hasChanged = row.setWUNDEN(value);
                break;
            case 9:
                hasChanged = row.setWS(value);
                break;
            case 10:
                hasChanged = row.setASP(value);
                break;
            case 11:
                hasChanged = row.setALLY(value);
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

                    } else {
                        System.out.println("ERR");
                    }
                } else {
                    System.out.println("NO CHANGES");
                }
                break;
            case 12:
                try {
                    int aktionen = Integer.parseInt(value.toString());
                    row.setAKTIONEN(aktionen);
                    addClones(row, aktionen);
                } catch (Exception e) {
                } finally {
                    return;
                }
            case 14:
                hasChanged = row.setMR(value);
                break;
            case 15:
                hasChanged = row.setAU(value);
                break;
            case 16:
                hasChanged = row.setGS(value);
                break;
            case 17:
                hasChanged = row.setTP(value);
                break;
            default:
                break;
        }

        if (hasChanged) {
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
        fireTableCellUpdated(rowIndex, columnIndex);

    }

    @Override
    public boolean isCellEditable(int row, int columnIndex) {
        //return columnIndex != 13;
        return true;
    }

    public Color getRowColour(int rowIndex) {
        Person row = persons.get(rowIndex);
        return row.getColor();
    }

    public void applyPersonDmg(int selectedRow, int dmg, boolean ignoreWS, boolean ignoreRS) {
        Person row = persons.get(selectedRow);
        int oldLp = row.getLP();
        int lpNow = row.applyDmg(dmg, ignoreWS, ignoreRS);
        String msg = row.getNAME() + " erleidet " + dmg + "Schaden. LP" + ": von " + oldLp + " auf " + lpNow + " geändert";
        TextLogger.getInstance().add(msg);
        fireTableRowsUpdated(selectedRow, selectedRow);
    }

    public void removePerson(int selectedRow) {
        Person row = persons.get(selectedRow);
        if (row.getALLY() == 0) {
            getFoeList().remove(row);
            foeModel.removeComboListPerson(row);

        } else {
            getAllyList().remove(row);
            allyModel.removeComboListPerson(row);
        }

        persons.remove(row);
        removeDeletedKAMPF_GEGEN(row);
        fireTableRowsDeleted(selectedRow, selectedRow);
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

            for (int i : changedIndices) {
                fireTableCellUpdated(i, 13);
                //System.out.println(i);
            }

        }
    }
    
    public int getEnumPersonName(Person per){
        String pName = per.getPureName();
        int maxGen = 0;
        for (Person p : persons){
            if(p.getPureName().equals(pName)){
                if (p.getGeneration() > maxGen)
                    maxGen = p.getGeneration();
            }
            
        }
        return maxGen++;
    }

}
