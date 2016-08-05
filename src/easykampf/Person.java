/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easykampf;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Omar
 */
public class Person implements Serializable, Cloneable {

    private static final long serialVersionUID = 2L;
    private int AT, PA, INI, RS, LP, MAXLP, WUNDEN, WS, ASP, WUNDENOLD, LPFAKTOROLD, ISTMP, AKTIONEN = 0;
    private int ALLY = 1;
    private String NAME = "";
    private Color color = new Color(110, 230, 110);

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
        this.LP = LP;
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
        setColorFromType();
    }

    /**
     * @return the Name
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * @param Name the Name to set
     */
    public void setNAME(String Name) {
        this.NAME = Name;
    }

    void setAT(Object value) {
        try {
            setAT(Integer.parseInt(value.toString()));
        } catch (Exception e) {

        }
    }

    void setPA(Object value) {
        try {
            setPA(Integer.parseInt(value.toString()));
        } catch (Exception e) {

        }
    }

    void setINI(Object value) {
        try {
            setINI(Integer.parseInt(value.toString()));
        } catch (Exception e) {

        }
    }

    void setRS(Object value) {
        try {
            setRS(Integer.parseInt(value.toString()));
        } catch (Exception e) {

        }
    }

    void setLP(Object value) {
        try {
            setLP(Integer.parseInt(value.toString()));
        } catch (Exception e) {

        }
    }

    void setWUNDEN(Object value) {
        try {
            setWUNDEN(Integer.parseInt(value.toString()));
        } catch (Exception e) {

        }
    }

    void setWS(Object value) {
        try {
            setWS(Integer.parseInt(value.toString()));
        } catch (Exception e) {

        }
    }

    void setASP(Object value) {
        try {
            setASP(Integer.parseInt(value.toString()));
        } catch (Exception e) {

        }
    }

    void setALLY(Object value) {
        try {
            setALLY(Integer.parseInt(value.toString()));

        } catch (Exception e) {

        }
    }

    void setNAME(Object value) {
        setNAME(value.toString());
    }

    private void setColorFromType() {
        if (this.getISTMP() > 0) {
            this.color = new Color(237, 237, 111); //light yellow
            return;
        }
        if (this.ALLY == 0) {
            this.color = new Color(237, 111, 111); //light blue
        } else {
            this.color = new Color(110, 230, 110); //light red
        }
    }

    public Color getColor() {

        return this.color;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void applyDmg(int damage, boolean applyWounds) {
        if (applyWounds) {
            int wounds = damage / getWS(); //Math.floor
            if (wounds > 0) {
                setWUNDEN(getWUNDEN() + wounds);
            }
        }
        setLP(getLP() - damage);
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

    void setMAXLP(Object value) {
        try {
            setMAXLP(Integer.parseInt(value.toString()));

        } catch (Exception e) {

        }
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

    public void setAKTIONEN(Object value) {
        try {
            setAKTIONEN(Integer.parseInt(value.toString()));

        } catch (Exception e) {

        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    void setTMP() {
        this.setISTMP(this.getISTMP() + 1);
        setColorFromType();
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

}