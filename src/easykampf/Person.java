/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easykampf;

import java.awt.Color;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.GUI;

/**
 *
 * @author Omar
 */
public class Person implements Serializable, Cloneable {

    private static final long serialVersionUID = 2L;
    private int AT, PA, INI, BASEINI, RS, LP, MAXLP, WUNDEN, WS, ASP, WUNDENOLD, LPFAKTOROLD, ISTMP, AKTIONEN, MR, AU, GS, generation = 0;
    private int ALLY = 1;
    private String NAME = "";
    private String TP = "";
    private Person KAMPF_GEGEN = null;
    private Boolean ignoreLowLP = false;
    private Boolean pariert = false;
    private Boolean angegriffen = false;
    public final String UUID = java.util.UUID.randomUUID().toString();
    private String notiz;
    //TODO objekt für stats
    private final String fromVersion = Version.getVersion();

    
    public Person(String NAME, int AT, int PA, int INI, int BASEINI, int RS, int LP, int MAXLP, int WUNDEN, int WS, int ASP, int ALLY, int BONUSAKTIONEN) {
        this.NAME = NAME;
        this.AT = AT;
        this.PA = PA;
        this.INI = INI;
        this.BASEINI = BASEINI;
        this.RS = RS;
        this.LP = LP;
        this.MAXLP = MAXLP;
        this.WUNDEN = WUNDEN;
        this.WS = WS;
        this.ASP = ASP;
        this.ALLY = ALLY;
        this.AKTIONEN = BONUSAKTIONEN;
    }
    
    

    public Person(String NAME, int AT, int PA, int INI, int BASEINI, int RS, int LP, int MAXLP, int WUNDEN, int WS, int ASP, int ALLY, int BONUSAKTIONEN, int MR, int AU, int GS, String TP) {
        this.NAME = NAME;
        this.AT = AT;
        this.PA = PA;
        this.INI = INI;
        this.BASEINI = BASEINI;
        this.RS = RS;
        this.LP = LP;
        this.MAXLP = MAXLP;
        this.WUNDEN = WUNDEN;
        this.WS = WS;
        this.ASP = ASP;
        this.ALLY = ALLY;
        this.AKTIONEN = BONUSAKTIONEN;
        this.MR = MR;
        this.AU = AU;
        this.GS = GS;
        this.TP = TP;
    }

    public Person(){}
    
    /**
     * @return the AT
     */
    public int getAT() {
        return AT;
    }

    /**
     * @param AT the AT to set
     */
    public void setAT(int AT) {
        this.AT = AT;
    }

    /**
     * @return the PA
     */
    public int getPA() {
        return PA;
    }

    /**
     * @param PA the PA to set
     */
    public void setPA(int PA) {
        this.PA = PA;
    }

    /**
     * @return the INI
     */
    public int getINI() {
        return INI;
    }

    /**
     * @param INI the INI to set
     */
    public void setINI(int INI) {
        this.INI = INI;
    }

    /**
     * @return the RS
     */
    public int getRS() {
        return RS;
    }

    /**
     * @param RS the RS to set
     */
    public void setRS(int RS) {
        this.RS = RS;
    }

    /**
     * @return the LP
     */
    public int getLP() {
        return LP;
    }

    /**
     * @param LP the LP to set
     */
    public void setLP(int LP) {
        int factor = 0;
        int oldLP = this.LP;
        this.LP = LP;
        if(!isIgnoreLowLP()){
            if (MAXLP / 4 > LP) {
                factor = -3;
            } else if (MAXLP / 3 > LP) {
                factor = -2;
            } else if (MAXLP / 2 > LP) {
                factor = -1;
            } else {
                factor = 0;
            }
        
        
        setAT(getAT() + -1 * LPFAKTOROLD + factor);
        setPA(getPA() + -1 * LPFAKTOROLD + factor);
        LPFAKTOROLD = factor;
        }
    }

    /**
     * @return the WUNDEN
     */
    public int getWUNDEN() {
        return WUNDEN;
    }

    /**
     * @param WUNDEN the WUNDEN to set
     */
    public void setWUNDEN(int WUNDEN) {
        this.WUNDENOLD = WUNDEN;
        int ce = this.WUNDEN - WUNDEN;
        setAT(getAT() + ce * 2);
        setPA(getPA() + ce * 2);
        setINI(getINI() + ce * 2);
        this.WUNDEN = WUNDEN;
    }

    /**
     * @return the WS
     */
    public int getWS() {
        return WS;
    }

    /**
     * @param WS the WS to set
     */
    public void setWS(int WS) {
        this.WS = WS;
    }

    /**
     * @return the ASP
     */
    public int getASP() {
        return ASP;
    }

    /**
     * @param ASP the ASP to set
     */
    public void setASP(int ASP) {
        this.ASP = ASP;
    }

    /**
     * @return the ALLY
     */
    public int getALLY() {
        return this.ALLY;
    }

    /**
     * @param ALLY the ALLY to set
     */
    public void setALLY(int ALLY) {
        this.ALLY = ALLY;
    }

    /**
     * @return the Name
     */
    public String getNAME() {
        return getGenName();
    }

    /**
     * @param Name the Name to set
     */
    public boolean setNAME(String Name) {
        if (this.NAME.equals(Name)) {
            return false;
        }
        this.NAME = Name;
        return true;
    }

    boolean setAT(Object value) {
        try {
            setAT(Integer.parseInt(value.toString()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setPA(Object value) {
        try {
            setPA(Integer.parseInt(value.toString()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setINI(Object value) {
        try {
            setINI(Integer.parseInt(value.toString()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setRS(Object value) {
        try {
            setRS(Integer.parseInt(value.toString()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setLP(Object value) {
        try {
            setLP(Integer.parseInt(value.toString()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setWUNDEN(Object value) {
        try {
            setWUNDEN(Integer.parseInt(value.toString()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setWS(Object value) {
        try {
            setWS(Integer.parseInt(value.toString()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setASP(Object value) {
        try {
            setASP(Integer.parseInt(value.toString()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setALLY(Object value) {
        try {
            setALLY(Integer.parseInt(value.toString()));

        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    boolean setNAME(Object value) {
        return setNAME(value.toString());

    }




    public int applyDmg(int damage, boolean ignoreWounds, boolean ignoreRS) {
        if (ignoreRS) {
            damage = damage - this.getRS();
            if (damage < 0) {
                damage = 0;
            }
        }
        if (ignoreWounds) {
            if (getWS() != 0) {
                int wounds = damage / getWS(); //Math.floor
                if (wounds > 0) {
                    setWUNDEN(getWUNDEN() + wounds);
                }
            }
        }
        int lpNow = getLP() - damage;
        setLP(lpNow);
        return lpNow;
    }

    /**
     * @return the MAXLP
     */
    public int getMAXLP() {
        return MAXLP;
    }

    /**
     * @param MAXLP the MAXLP to set
     */
    public void setMAXLP(int MAXLP) {
        this.MAXLP = MAXLP;
    }

    boolean setMAXLP(Object value) {
        try {
            setMAXLP(Integer.parseInt(value.toString()));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @return the AKTIONEN
     */
    public int getAKTIONEN() {
        return AKTIONEN;
    }

    /**
     * @param AKTIONEN the AKTIONEN to set
     */
    public void setAKTIONEN(int AKTIONEN) {
        this.AKTIONEN = AKTIONEN;
    }

    boolean setAKTIONEN(Object value) {
        try {
            setAKTIONEN(Integer.parseInt(value.toString()));

        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Person cloned = (Person) super.clone();
        cloned.setKAMPF_GEGEN(null);
        cloned.setAKTIONEN(0);
        return cloned;
    }

    void setTMP() {
        this.setISTMP(this.getISTMP() + 1);
    }

    /**
     * @return the ISTMP
     */
    public int getISTMP() {
        return ISTMP;
    }

    /**
     * @param ISTMP the ISTMP to set
     */
    public void setISTMP(int ISTMP) {
        this.ISTMP = ISTMP;
    }

    @Override
    public String toString() {
        return this.getNAME();
    }

    /**
     * @return the BASEINI
     */
    public int getBASEINI() {
        return BASEINI;
    }

    /**
     * @param BASEINI the BASEINI to set
     */
    public void setBASEINI(int BASEINI) {
        this.BASEINI = BASEINI;
    }

    boolean setBASEINI(Object value) {
        try {
            setBASEINI(Integer.parseInt(value.toString()));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @return the MR
     */
    public int getMR() {
        return MR;
    }

    /**
     * @param MR the MR to set
     */
    public void setMR(int MR) {
        this.MR = MR;
    }

    /**
     * @return the AU
     */
    public int getAU() {
        return AU;
    }

    /**
     * @param AU the AU to set
     */
    public void setAU(int AU) {
        this.AU = AU;
    }

    /**
     * @return the GS
     */
    public int getGS() {
        return GS;
    }

    /**
     * @param GS the GS to set
     */
    public void setGS(int GS) {
        this.GS = GS;
    }

    /**
     * @return the TP
     */
    public String getTP() {
        return TP;
    }

    /**
     * @param TP the TP to set
     * @return 
     */
    public boolean setTP(String TP) {
        if (this.TP.equals(TP)) {
            return false;
        }
        this.TP = TP;
        return true;

    }

    boolean setMR(Object value) {
        try {
            setMR(Integer.parseInt(value.toString()));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setAU(Object value) {
        try {
            setAU(Integer.parseInt(value.toString()));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setGS(Object value) {
        try {
            setGS(Integer.parseInt(value.toString()));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean setTP(Object value) {
        return this.setTP(value.toString());
    }

    /**
     * @return the KAMPF_GEGEN
     */
    public Person getKAMPF_GEGEN() {
        return KAMPF_GEGEN;
    }

    /**
     */
    public boolean setKAMPF_GEGEN(Person p) {
        if (this.KAMPF_GEGEN == p) {
            return false;
        }
        this.KAMPF_GEGEN = p;
        return true;

    }

    boolean setKAMPF_GEGEN(Object value) {
        Person p = null;
        try {
            p = (Person) value;
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            return false;
        }

        return setKAMPF_GEGEN(p);
    }

    public String getDetails() {
        return getGenName() + ", MAXLP: " + this.getMAXLP() + ", BASEINI: " + this.getBASEINI() + ", RS: " + this.getRS();
    }

    /**
     * @return the generation
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * @param generation the generation to set
     */
    public void setGeneration(int generation) {
        this.generation = generation;
    }
    
    private String getGenName(){
        if(this.getGeneration() == 0){
            return this.NAME;
        }
        return this.NAME + " " + this.getGeneration();
    }

    
    public String getPureName(){
        return this.NAME;
    }

    /**
     * @return the ignoreLowLP
     */
    public boolean isIgnoreLowLP() {
        return ignoreLowLP;
    }

    /**
     * @param ignoreLowLP the ignoreLowLP to set
     */
    public void setIgnoreLowLP(boolean ignoreLowLP) {
        this.ignoreLowLP = ignoreLowLP;
    }

    /**
     * @return the WUNDENOLD
     */
    public int getWUNDENOLD() {
        return WUNDENOLD;
    }

    /**
     * @return the pariert
     */
    public Boolean getPariert() {
        return pariert;
    }

    /**
     * @param pariert the pariert to set
     */
    public void setPariert(Boolean pariert) {
        this.pariert = pariert;
    }

    /**
     * @return the angegriffen
     */
    public Boolean getAngegriffen() {
        return angegriffen;
    }

    /**
     * @param angegriffen the angegriffen to set
     */
    public void setAngegriffen(Boolean angegriffen) {
        this.angegriffen = angegriffen;
    }

    Boolean setAngegriffen(Object value) {
        Boolean b = (Boolean) value;
        setAngegriffen(b);
        return true;
    }

    Boolean setPariert(Object value) {
                Boolean b =  (Boolean) value;
        setPariert(b);
        return true;
    }

    /**
     * @return the notiz
     */
    public String getNotiz() {
        return notiz;
    }

    /**
     * @param notiz the notiz to set
     */
    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    Boolean setNotiz(Object value) {
        String newVal = value.toString();
        if(newVal.equals(this.getNotiz())){
            return false;
        }
        setNotiz(newVal);
        return true;
       
    }
}
