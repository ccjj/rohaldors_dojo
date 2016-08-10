/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erinnerung;

/**
 *
 * @author cje
 */
public class Erinnerung {
    private int runde;
    private String msg;
    
    public Erinnerung(int runden, String msg){
        this.runde = runden;
        this.msg = msg;
    }

    /**
     * @return the runden
     */
    public int getRunde() {
        return runde;
    }

    /**
     * @param runden the runden to set
     */
    public void setRunde(int runden) {
        this.runde = runden;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    
}
