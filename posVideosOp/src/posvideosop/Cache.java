/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posvideosop;

import java.util.ArrayList;

/**
 *
 * @author felipegadeallopis
 */
public class Cache {
    private final int memory;
    private int usedMemory = 0;
    private int numEndPoints;
    private boolean getFull = false;
    private boolean isUsed = false;
    private ArrayList<Integer> Videos = new ArrayList<Integer>();
    
    public Cache(int memory, int numOfVideos){
        this.memory = memory;
        for(int i = 0; i<numOfVideos; i++){
            this.Videos.add(null);
        }
        
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(int usedMemory) {
        this.usedMemory = usedMemory;
    }

    public ArrayList<Integer> getVideos() {
        return Videos;
    }

    public void setVideos(int video, int size) {
        this.Videos.set(video, size);
        this.usedMemory+=size;
    }
    
    public int getMemory() {
        return memory;
    }

    public int getNumEndPoints() {
        return numEndPoints;
    }

    public void setNumEndPoints(int numEndPoints) {
        this.numEndPoints = numEndPoints;
    }

    public boolean isGetFull() {
        return getFull;
    }

    public void setGetFull(boolean getFull) {
        this.getFull = getFull;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
    public String toStringVideos(){
        String rtrn = null;
        for(int i = 0; i< this.Videos.size();i++){
            if(this.Videos.get(i)!= null) {
                if(rtrn == null) rtrn = i+" ";
                else rtrn += i + " ";
            }
        }
        return rtrn;
    }

}
