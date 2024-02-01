package serveur; 
import worker.*;
import  client.*;

import java.lang.* ; 
import java.io.*;
import java.net.*;
import java.util.*;

class connexionWorker extends Thread{

    private int id   ; 
    private  Socket s  ; 
    private ObjectInputStream sisr ;
    private ObjectOutputStream sisw   ; 



    public connexionWorker(int id, Socket s) {
		this.id = id;
		this.s = s;
	



		Serveur.wWorker[id] = sisw;
	}


    public void run(){

	
		try {

		try {
			sisr = new ObjectInputStream(s.getInputStream());
	
			sisw = new ObjectOutputStream(s.getOutputStream()) ; 
		} catch(IOException e) {
			System.out.println (" Reader ou writer non   créé ") ;  
		}
       int cpt = 0 ;  
      int block    = 0  ; 
	
		// on envois le debut  du travail  au worker  
		while (true)
		
   {
	cpt =0 ; 

	
			sisw.writeObject(((Serveur.IdWorker-1)+block)*50000000);
	
	
		while(cpt!=50)
		{
			
        
			
	
			Serveur.listRes.putAll( (Hashtable<Integer,Integer>) sisr.readObject()  );// concatene les elements les Hashtabls recus 
		
			
		try {
			
			cpt  =  (int)sisr.readObject(); 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
			if(Serveur.listRes.size()>=10000000)
			{
				try {
					Serveur.oos.writeObject(Serveur.listRes);
					Serveur.listRes.clear();
				System.out.println("\n[worker "+this.id+"] >> 10 block de pesistance stoker dans le fichier \n");
					
				} catch (IOException e) {
					System.out.println("HashTable non  Stocké ");
				}
				
			}


				
	
			
		}


	   block++; 
           
   } 
			
		} catch(Exception e) {
			System.out.println( "Worker "+this.id + " s'est deconnecté  =============") ; 
			Serveur.listWorker.remove(this.id) ; 
			
		}
        try {
            sisr.close();
            sisw.close() ; 
            s.close(); 
        } catch (Exception e) {
        System.out.println( "Worker "+this.id + " s'est deconnecté  =============") ; 
		Serveur.listWorker.remove(this.id) ; 
        }
       
	}





}


