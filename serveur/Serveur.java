package serveur;

import worker.*;
import  client.*; 

import java.lang.* ; 
import java.io.*;
import java.net.*;
import java.util.*;


public class Serveur {
	static int port1 = 8080;
    static int port2 = 9632; 
	static final int Clients = 100;
    
	static Hashtable<Integer ,Integer> listRes ; 
    static  boolean arretClient =  false  ; 
    static final int MaxWorker=20;
	static ObjectOutputStream wClient[];
    static ObjectOutputStream wWorker[] ; 
	static ArrayList<Integer> listWorker   ; 
	static int IdClient = 0;
    static int IdWorker  =0 ; 
	static  int index  = 0 ; 
	static ObjectOutputStream oos ; 
	static ObjectInputStream ois  ; 
	static FileWriter writer; 
	// Pour utiliser un autre port pour le serveur, l'exécuter avec la commande : java ServeurMC 8081
	public static void main(String[] args) throws Exception {
		if (args.length != 0) {
			port1 = Integer.parseInt(args[0]);
            port2= Integer.parseInt(args[1]) ; 
		}
		wClient  = new ObjectOutputStream[Clients];
		wWorker = new  ObjectOutputStream[MaxWorker] ; 
		listRes = new Hashtable<Integer,Integer>() ; 
		oos  = new ObjectOutputStream(new FileOutputStream("hashtableFile.bin"))  ; 
		ois  = new ObjectInputStream(new FileInputStream("hashtableFile.bin") ) ; 
		listWorker  = new ArrayList<Integer>()  ; 
		writer = new FileWriter("hashTable.txt");
		// 1 - Ouverture du ServerSocket par le serveur

	
    





	 
				 GererConnexionC   gc  = new  GererConnexionC(port1) ;
				 
				 GererConnexionW gw =  new  GererConnexionW(port2) ;  
				 gc.start() ; 
				 gw.start();

	}
  
 }	

 














 

class  GererConnexionC  extends Thread{

	private Socket  soc  ; 
	private ServerSocket s ; 
	 





	GererConnexionC(int port  )
	{
		try {
			
			this.s  =  new ServerSocket(port) ;
			this.soc = null ; 

	
			
		   } catch (IOException e) {
			   // TODO: handle exception
		   }
		
    
	}
	public void run()
	{
		System.out.println("SOCKET ECOUTE  CLIENT CREE => " + this.s);
	System.out.println("--------------------------");
	
			 

		while (Serveur.IdClient < Serveur.Clients){

		try {
			

	
	     this.soc = this.s.accept() ; 
		} catch (IOException e) {
			// TODO: handle exception
		}
    
			 connexionClient cClient = new connexionClient(Serveur.IdClient, this.soc);
			 System.out.println("NOUVELLE CONNEXION - SOCKET CLIENT=> " + this.soc);
			 Serveur.IdClient++;
			 cClient.start();
		
			
			
		}
		try {
			this.s.close();
			this.soc.close(); 
		} catch (Exception e) {
			System.out.println("Connexion Client Terminée");
		}
        

	}


 }



 class GererConnexionW extends Thread
 
 {
	 private Socket  soc ; 
	 private ServerSocket s ;





	GererConnexionW( int port  )
	{
		try {
			
				
			this.s  =  new ServerSocket(port) ;
			this.soc = null ; 
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		
	}
	public void run   ()
	{
		
		System.out.println("SOCKET ECOUTE  WORKER CREE => " + this.s); 
		while(Serveur.IdWorker <Serveur.MaxWorker)
        {
			try {
			
				
				this.soc  = this.s.accept() ; 
			} catch (IOException e) {
				// TODO: handle exception
			}
			
			
				 //soc1.setSoTimeout(10000);
			connexionWorker cWorker = new connexionWorker(Serveur.IdWorker, this.soc);
			Serveur.listWorker.add(Serveur.IdWorker) ; 
			System.out.println("NOUVELLE CONNEXION - SOCKET  WORKER=> " + this.soc);
			Serveur.IdWorker++;
			cWorker.start();
		
			
			
			

        }
		try {
			this.s.close();
			this.soc.close(); 
		} catch (Exception e) {
			System.out.println("Connexion  Worker Terminée");}
		


	}
	
 }