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
public class Video {
    
    private int size;
    private ArrayList<Integer> endPoints= new ArrayList<Integer>();
    private int videoID;
    private int countTimesGet = 0;

    public Video(int size, int videoID,int eps) {
        this.size = size;
        this.videoID = videoID;
        for(int i = 0; i<eps; i++){
            this.endPoints.add(null);
        }
    }

    public int getCountTimesGet() {
        return countTimesGet;
    }

    public void setCountTimesGet(int countTimesGet) {
        this.countTimesGet = countTimesGet;
    }

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Integer> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(int endPoints, int pet) {
        if(this.endPoints.get(endPoints)!=null){
            this.endPoints.set(endPoints, this.endPoints.get(endPoints)+pet);//Si ya se pide desde un endPoint sumamos las peticiones
        }
        else
            this.endPoints.set(endPoints, pet);
    }
    public int getSizeEndPoints(){
        return this.endPoints.size();
    }


    
    
}
