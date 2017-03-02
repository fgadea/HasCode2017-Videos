/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posvideosop;

/**
 *
 * @author felipegadeallopis
 */
public class EndPoint {
    private int latency;
    private int numID;
    private int conectToXservers;
    private int linea;
    private int[][] epToServ;
    public EndPoint(int latency, int numID, int conectToServs, int linea){
        this.latency = latency;
        this.numID = numID;
        this.conectToXservers = conectToServs;
        this.linea = linea;
        epToServ = new int[conectToServs][2];
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }
    public void consEpToServ(int pos, int server, int latencyToServ){
        epToServ[pos][0] = server;
        epToServ[pos][1] = latencyToServ;
    }

    public int getConectToXservers() {
        return conectToXservers;
    }

    public void setConectToXservers(int conectToXservers) {
        this.conectToXservers = conectToXservers;
    }

    public int[][] getEpToServ() {
        return epToServ;
    }

    public void setEpToServ(int[][] epToServ) {
        this.epToServ = epToServ;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public int getNumID() {
        return numID;
    }

    public void setNumID(int numID) {
        this.numID = numID;
    }
    
}
