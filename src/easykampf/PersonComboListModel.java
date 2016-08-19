/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easykampf;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * 
 * @author Omar
 */
public class PersonComboListModel extends AbstractListModel implements ComboBoxModel {
    private ArrayList<Person> pList = new ArrayList<>();
    
    private Person selected;

    @Override
    public void setSelectedItem(Object anItem) {
        selected = (Person) anItem;
    }

    @Override
    public Object getSelectedItem() {

        return (Person) selected;
    }

    @Override
    public int getSize() {
        return pList.size();
    }

    
    @Override
    public Object getElementAt(int index) {
        return (Person) pList.get(index);
    }

   
    public int getPersonIndex(Person p){
        return pList.indexOf(p);
    }
    
    public void setPersons(ArrayList<Person> persons){
        this.pList = persons;
    }
    
    public PersonComboListModel(ArrayList<Person> persons){
        this.pList = persons;

    }
    
    @Override
    public void fireIntervalAdded(Object source, int index0, int index1)
    {
        Object[] listeners = listenerList.getListenerList();
        ListDataEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListDataListener.class) {
                if (e == null) {
                    e = new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1);
                }
                ((ListDataListener)listeners[i+1]).intervalAdded(e);
            }
        }
    }
    
    @Override
        public void fireIntervalRemoved(Object source, int index0, int index1)
    {
        Object[] listeners = listenerList.getListenerList();
        ListDataEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListDataListener.class) {
                if (e == null) {
                    e = new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index0, index1);
                }
                ((ListDataListener)listeners[i+1]).intervalRemoved(e);
            }
        }
        
    }
        
  
    public void removeComboListPerson(Person p){
        int pIndex = getPersonIndex(p);
            if(pIndex != -1){
                fireIntervalRemoved(this, pIndex, pIndex);
            }
    }
    
        public void addComboListPerson(){
                fireIntervalAdded(this, getSize() - 1, getSize() - 1);
            
    }

        
        
}

