/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erinnerung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import javax.swing.AbstractListModel;

/**
 *
 * @author Omar
 */
public class ErinnerungCollection extends AbstractListModel {

    private static ErinnerungCollection instance = null;
    static ArrayList<Erinnerung> erinnerungen = new ArrayList<>();
    private ErinnerungComparator co = new ErinnerungComparator();
    //Map a = new HashMap<Integer, ArrayList<String>>();
    //wie remove?

    private ErinnerungCollection() {
    }

    public static ErinnerungCollection getInstance() {
        if (instance == null) {
            instance = new ErinnerungCollection();
        }
        return instance;

    }

    public void add(int runden, int rundenLeft, String msg) {
        erinnerungen.add(new Erinnerung(runden, rundenLeft, msg));
        Collections.sort(erinnerungen, co);

    }

    public void setErinnerungen(ArrayList<Erinnerung> er) {
        this.erinnerungen = er;
        Collections.sort(erinnerungen, co);
    }

    public void remove(int index) {
        erinnerungen.remove(index);
        Collections.sort(erinnerungen, co);
    }

    public ArrayList<String> getErinnerungFromRunde(int runde) {
        ArrayList<String> erinnerungenNow = new ArrayList<>();
        ArrayList<Erinnerung> toDel = new ArrayList<>();
        for (Erinnerung er : erinnerungen) {
            if (er.getRunde() == runde) {
                erinnerungenNow.add(er.getMsg());
                toDel.add(er);
            }
        }
        removeErinnerungen(toDel);
        return erinnerungenNow;
    }

    public void clear() {
        this.erinnerungen.clear();
    }

    @Override
    public int getSize() {
        return erinnerungen.size();
    }

    @Override
    public Object getElementAt(int index) {
        return erinnerungen.get(index);
    }

    public void update() {
        this.fireContentsChanged(instance, 0, getSize());
    }

    public void updateRoundsLeft(int currentRound) {
        for (Erinnerung er : erinnerungen) {
            er.reduceLeftRounds();
        }
    }

    private void removeErinnerungen(ArrayList<Erinnerung> toDel) {
        for(Erinnerung e : toDel){
            erinnerungen.remove(e);
        }
    }

}

class ErinnerungComparator implements Comparator<Erinnerung> {

    @Override
    public int compare(Erinnerung o1, Erinnerung o2) {
        return Integer.compare(o1.getRunde(), o2.getRunde());
    }
}
