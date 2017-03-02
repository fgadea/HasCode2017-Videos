/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posvideosop;

import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author felipegadeallopis
 */
public class Main {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try{
            
            FileReader fr1 = new FileReader(args[0]);
            BufferedReader br1 = new BufferedReader(fr1);
            
            String[] Aux = br1.readLine().split(" ");
            
            int[] linea0 = new int[Aux.length];
            for (int i=0; i<linea0.length; i++){
                linea0[i] = Integer.parseInt(Aux[i]);
                //debug// System.out.print("\n" + linea0[i]);
            }
            Aux = null;
            
            
            
            ////////////////////////array de objetos cache ////////////////////////////////////
            Cache[] cache = new Cache[linea0[3]];
            for(int i = 0; i< cache.length; i++){
                Cache cach = new Cache(linea0[4],linea0[0]);
                cache[i] = cach;
            }
            
            
            
            ///////////////////////// imprimir archivo para comprobar lectura///////////////////
            /*for(int i = 0; i< arch.length; i++){
            for(int j = 0; j < arch[i].length; j++){
            System.out.print(arch[i][j] + " ");
            
            }
            System.out.print("\n");
            }*/
            
            
            
            ///////////////////// array de objetos video(array con los videos) //////////////////////////////////////////////////
            Aux = br1.readLine().split(" ");
            int[] linea1 = new int[Aux.length];
            for (int i=0; i<linea1.length; i++){
                linea1[i] = Integer.parseInt(Aux[i]);//creo un array para los datos de los videos
            }
            Aux = null;
            Video[] videos = new Video[linea0[0]];
            for(int i = 0; i < videos.length; i++){
                Video video = new Video(linea1[i], i, linea0[1]);  //inicializo
                videos[i] = video;
            }
            //print debuger//
            /*
            for (Video video : videos) {
            System.out.print("\nVideo " + video.getVideoID() + " de tamaño " + video.getSize() + "mb \n");
            }*/
            
            
            
            /////////////////////recojo los datos que quedan en una matriz////////////////////////////////////////
            FileReader fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr);
            int lC=0;
            String vTemp;
            while ((vTemp = br.readLine()) != null) {
                lC++;
            }
            fr.close();
            
            int[][] sArch = new int[lC-2][3];
            String lectura = br1.readLine();
            int iC = 0;
            while(lectura != null){
                Aux = lectura.split(" ");
                for (int j = 0; j < Aux.length; j++) {
                    sArch[iC][j] = Integer.parseInt(Aux[j]);
                }
                iC++;

                lectura = br1.readLine();
            }
            fr1.close();
            
            
            
            /////////////////////array de objetos endPoint( array con todos los endPoint)////////////////////////////////////////
            ArrayList<EndPoint> epoint = new ArrayList<>(linea0[1]);
            
            for(int j = 0, i = 0; j < sArch.length && sArch[j][2] == 0;j++, i++){  // iteramos el array de datos
                //debug//  System.out.println("\n linia" + j+" i los serv" + sArch[j][1]+" ");
                EndPoint ep = new EndPoint(sArch[j][0], i, sArch[j][1], j+1);

                epoint.add(ep);
                j += sArch[j][1];
            }
            //debug//
            /*EndPoint temp1 =  epoint.get(0);
            
            System.out.print("\n" + temp1.getConectToXservers());
            System.out.println("tamaño " + epoint.size());*/
            
            
            
            ////////////// Mete en el endpoint correspondiente a que server está conectado y la latencia de este//////////////////
            for(int i = 0; i < epoint.size(); i++){
                int nServ = epoint.get(i).getConectToXservers();//Obtengo el número de a cuantos servidores está conectado el EndPoint
                int linea = epoint.get(i).getLinea();
                for(int j = 0; j < nServ; j++, linea++){//Aquí debería setear las características de los servidores a los que está conectado el EndPoint
                    int server = -1; //inicializamos en -1
                    int latency = -1;

                    server = sArch[linea][0];// Obtenemos el número del servidor y su latencia y se la ponemos al EndPoint
                    latency = sArch[linea][1];//repetimos con todos los serv del EP


                    epoint.get(i).consEpToServ(j, server, latency);
                }

            }
            
            
            //debug//
            /////////////////////////////// Print para comprobar los EndPoint//////////////////////////////////////////////////
            /*for(int w = 0; w< epoint.size(); w++){
            int[][] temp = epoint.get(w).getEpToServ();
            System.out.print("\nEndPoint "+epoint.get(w).getNumID()+" \\/ conectado a "+ epoint.get(w).getConectToXservers() +" servidores y "+ epoint.get(w).getLatency() +"ms de ping al data center \n");
            for(int i = 0; i< temp.length; i++){
            for(int j = 0; j<temp[i].length;j++){
            System.out.print(temp[i][j] + " ");
            }
            System.out.print("\n========\n");
            }
            }*/
            
            
            
            
            ///////////////////////////////Ponemos en los videos las peticiones y de que servidor vienen éstas////////////////////////////////////////////

            for(int j = epoint.get(epoint.size()-1).getLinea()+epoint.get(epoint.size()-1).getConectToXservers(); j < sArch.length; j++){
                int video = sArch[j][0];
                int ep = sArch[j][1];
                int pet = sArch[j][2];
                videos[video].setEndPoints(ep, pet);
            }
            
            
            
            
            //////////////////////////////seteo las veces que es pedido cada video///////////////////////////////
            for (Video video : videos) {
                int count = 0;
                ArrayList<Integer> temp = video.getEndPoints();
                for(int j = 0; j<temp.size(); j++){
                    if(temp.get(j)!=null) count++;
                }
                video.setCountTimesGet(count);
            }
            
            
            
            //debug//
            ///////////print probar lo anterior/////////////
            /*
            int mbN = 0;
            for(int i = 0; i<videos.length; i++){
            ArrayList<Integer> temp = videos[i].getEndPoints();
            boolean countMb = true;
            System.out.print("\n El video "+ i + " de " + videos[i].getSize() +"MB lo piden "+ videos[i].getCountTimesGet() +" EndPoints diferentes\n");
            for(int j = 0; j<temp.size(); j++){
            if(temp.get(j)!=null){
            if(countMb)mbN += videos[i].getSize();
            System.out.print("\n\t En el EP " +j + ", " + temp.get(j) + " veces(peticiones) \n");
            countMb = false;
            }

            }
            }/*
            System.out.print("\n Hacen falta " + mbN + "MB para todos los videos pedidos");
            System.out.print("\n El total de megas de los servideos es "+(linea0[3]*linea0[4])+"\n");
            */
            //////////////////////////////implementción algoritmo////////////////////////////////////////////////////
            //debug// System.out.print("Algoritmo");
            //ArrayList<Integer> V = new ArrayList<>();//Lista de videos que no se piden para
            for (Video video : videos) {
                //debug// System.out.print("\n Entro en el bucle 1 de 3");
                //if(!V.contains(i)){
                //if (video.getCountTimesGet() > 0) {
                    int aux = -1;
                    //int ep = -1;
                    //ArrayList<Integer> eP = new ArrayList<>();//Lista ep vacios
                    /////////Elección EndPoint///////////////////
                    for (int j = 0; j < video.getSizeEndPoints(); j++) {
                        //debug// System.out.print("\n Entro en el bucle 2 de 3");
                        //if(!eP.contains(j)){
                        int ep = -1;
                        if (video.getEndPoints().get(j) != null) {
                            if (video.getEndPoints().get(j) > aux) {
                                aux = video.getEndPoints().get(j); //cojo el endPoint que lo pide más veces
                                ep = j;
                            }
                        } //debug// Debería estar cogiendo el Id del EP
                        ///////////Servidores del endPoint////////////////
                        if (ep > -1) {
                            int[][] bestServer = epoint.get(ep).getEpToServ();
                            //ArrayList<Integer> arrlSvr = new ArrayList<>();//lista servidores vacios
                            for(int e = 0; e< bestServer.length-1; e++){
                                if (bestServer[e][1]>bestServer[e+1][1]){
                                    int variableauxiliar=bestServer[e][1];
                                    int variableauxiliar2 = bestServer[e][0];
                                    bestServer[e][1]=bestServer[e+1][1];
                                    bestServer[e][0]=bestServer[e+1][0];
                                    bestServer[e+1][1]=variableauxiliar;
                                    bestServer[e+1][0]=variableauxiliar2;
                                }
                            }
                            for (int[] bestServer1 : bestServer) {
                                //debug// System.out.print("\n Entro en el bucle 3 de 3");
                                //if(!arrlSvr.contains(k)){
                                if (bestServer1 != null) {
                                    if (bestServer1[1] < epoint.get(ep).getLatency() && (cache[bestServer1[0]].getUsedMemory() + video.getSize()) <= cache[bestServer1[0]].getMemory() && cache[bestServer1[0]].isGetFull() == false) {
                                        cache[bestServer1[0]].setVideos(video.getVideoID(), video.getSize());
                                        cache[bestServer1[0]].setIsUsed(true);
                                    }
                                    //else arrlSvr.add(k);
                                }
                                //}
                            } //debug// System.out.print("\n salgo del bucle 3");
                        } //else eP.add(j);
                        //}
                    } //debug// System.out.print("\n ep del video " + videos[i].getVideoID()+ " es " + ep + "\n");
                //} //else V.add(i);
                //}
                //debug// System.out.print("\n video " +i+" de " +videos.length);
            }
            //debug// //System.out.print("\n Salgo del algoritmo y empiezo escritura");
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
            
            ////////////////////escribo archivo//////////////////
            int usedServ = 0;
            for (Cache caches : cache) {
                if (caches.isIsUsed()) {
                    usedServ++;
                }
            }
            //debug// System.out.print(usedServ);
            File f;
            f = new File(args[0].substring(0, args[0].length()-3)+".out");
            try{

                FileWriter w = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write(usedServ+"\n");//escribimos en el archivo
                for(int i = 0; i<cache.length; i++){
                    if(cache[i].isIsUsed()) wr.append(i+" "+cache[i].toStringVideos()+"\n"); //concatenamos en el archivo sin borrar lo existente
                }        //ahora cerramos los flujos de canales de datos, al cerrarlos el archivo quedará guardado con información escrita
                //de no hacerlo no se escribirá nada en el archivo
                wr.close();
                bw.close();
            }catch(IOException e){}
            ;
            //debug// System.out.print("\n Archivo escrito, adios");
        }catch(FileNotFoundException ex){Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
}       catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }     }
    
    
}
