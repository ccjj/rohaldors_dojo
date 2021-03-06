/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easykampf;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * 
 * @author Omar
 */
public class SavedPersonCollection extends AbstractListModel implements Serializable {
    
    private static final long serialVersionUID = 2L;

    public static ArrayList<Person> persons = new ArrayList<>();
    
    public void addPerson() {
        Person p = new Person();
        persons.add(p);
        //this.fireContentsChanged(this, persons.size()-1, persons.size()-1);
    }
        
    public void removePerson(int selectedRow) {
        Person row = persons.get(selectedRow);
        persons.remove(row);

    }

    public void addPerson(Person p) {
         persons.add(p);
    }

    @Override
    public int getSize() {
        return persons.size();
    }

    @Override
    public Object getElementAt(int index) {
        return persons.get(index);
    }

    public Person getPerson(int index) {
       return persons.get(index);
    }

    public void setPersons(ArrayList<Person> spc) {
        this.persons = spc;
        //this.fireContentsChanged(spc, 0, spc.lastIndexOf(spc));
    }



}
