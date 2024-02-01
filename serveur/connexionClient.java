package serveur; 

import worker.*;
import  client.*;
import java.lang.* ; 
import java.io.*;
import java.net.*;
import java.util.*;


class connexionClient extends Thread {
	private int id;
	
	private Socket s;
	private ObjectInputStream sisr;
	private ObjectOutputStream sisw;



	public connexionClient(int id, Socket s) {
		this.id = id;
		this.s = s;
		Serveur.wClient[id] = sisw;
	}

	public void run(){
		try {
			
			sisr = new ObjectInputStream(s.getInputStream());
			sisw = new ObjectOutputStream(s.getOutputStream());
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		try {
			ArrayList<String> listeResultats = new ArrayList<String>();
			Message msg ;
			while (true) {
            	sisw.writeObject(this.id);
				msg= (Message)sisr.readObject();
				msg.setID(this.id)  ;  
				System.out.println("\n--------------------------------------------------------------") ; 
				System.out.print(msg);   	// trace locale
				if(msg.getC().getTexte().equals("PERSISTANCE")){
				try {
					sisw.reset();
				}
				catch(Exception e){}
							if ( Serveur.listRes.containsKey(msg.getX()))
					
									{

										listeResultats.add("La persistance est de : "+Serveur.listRes.get(msg.getX()));
										sisw.writeObject(listeResultats);
										listeResultats.clear();
									}
							else{
									
										try{
										Serveur.oos.writeObject(Serveur.listRes); //  on reecrit le hashTable dans le fichier  avant de l'ecraser 
										}catch(Exception e){System.out.println("Erreur écriture fichier");}

										try{
										Serveur.listRes  = (Hashtable<Integer,Integer>) Serveur.ois.readObject();
										} catch (IOException e) {System.out.println(" Erreur de lecture fichier!! ");}

										if ( Serveur.listRes.containsKey(msg.getX()))
										{
											sisw.writeObject(Serveur.listRes.get(msg.getX()));
										}
										else{
											sisw.writeObject("Persistance pas encore calculer ... \n Veillez reesayer plus tard Merci ! ") ; 

										}
										
									}
						
				}
				else if (msg.getC().getTexte().equals("STATISTIQUE")) 
				{
					try {
					sisw.reset();
					}
					catch(Exception e){}
					int a = msg.getBornA();
					int b = msg.getBornB();
					boolean bool = true;
					int i = a;
					double moy = 0;
					double mediane = 0;

					while ((i<=b) && (bool == true)){
						if (!(Serveur.listRes.containsKey(i))){
							bool = false;
						}
						else {
							i++;
						}
					}
					if (bool == true){		//toutes les valeurs entre a et b sont dans la Hashtable
						for (i = a;i<=b;i++){
							moy = moy + Serveur.listRes.get(i);
						}
						moy = moy / (b-a+1);
						listeResultats.add("La moyenne est de : "+moy);


						if((b-a+1)%2 ==0){
							i = a+(b-a)/2;
							mediane = Serveur.listRes.get(i);
							i = (a+(b-a+1)/2);
							mediane = mediane + Serveur.listRes.get(i);
							mediane = mediane /2;

					
							listeResultats.add("La médiane est de : "+mediane);
						}
						else{
							i = (b-a+1)/2;
							i = i + a;
							mediane = Serveur.listRes.get(i);
					
							
							listeResultats.add("La médiane est de : "+ mediane);
						}
						sisw.writeObject(listeResultats);
						listeResultats.clear();
					}else// on vas chercher dans le fichier 
					{
						try{
							Serveur.oos.writeObject(Serveur.listRes); //  on reecrit le hashTable dans le fichier  avant de l'ecraser 
							}catch(Exception e){System.out.println("Erreur écriture fichier");}

							try{
							Serveur.listRes  = (Hashtable<Integer,Integer>) Serveur.ois.readObject();
							} catch (IOException e) {System.out.println(" Erreur de lecture fichier!! ");}



								for (i = a;i<=b;i++){
							moy = moy + Serveur.listRes.get(i);
						}
						moy = moy / (b-a+1);
						listeResultats.add("La moyenne est de : "+moy);


						if((b-a+1)%2 ==0){
							i = a+(b-a)/2;
							mediane = Serveur.listRes.get(i);
							i = (a+(b-a+1)/2);
							mediane = mediane + Serveur.listRes.get(i);
							mediane = mediane /2;

							listeResultats.add("La médiane est de : "+mediane);
						}
						else{
							i = (b-a+1)/2;
							i = i + a;
							mediane = Serveur.listRes.get(i);
							
							listeResultats.add("La médiane est de : "+ mediane);
						}
						sisw.writeObject(listeResultats);
						listeResultats.clear();
					}


				}
				else if (msg.getC().getTexte().equals("INTERVALLE")) 
				{
					try {
					sisw.reset();
					}
					catch(Exception e){}

					int a = msg.getBornA();
					int b = msg.getBornB();
					boolean bool = true;
					int i = a;
					

					while ((i<=b) && (bool == true)){
						if (!(Serveur.listRes.containsKey(i))){
							bool = false;
						}
						else {
							i++;
						}
					}


					if (bool == true){		//toutes les valeurs entre a et b sont dans la Hashtable
						for (i = a;i<=b;i++){
						
							listeResultats.add(""+Serveur.listRes.get(i));
						}
						try {
								sisw.reset();
							}
							catch(Exception e){}
						sisw.writeObject(listeResultats);
						listeResultats.clear();
					}
					else // on vas chercher  dans le fichier 
				{
					

					try{
						Serveur.oos.writeObject(Serveur.listRes); //  on reecrit le hashTable dans le fichier  avant de l'ecraser 
						}catch(Exception e){System.out.println("Erreur écriture fichier");}

						try{
						Serveur.listRes  = (Hashtable<Integer,Integer>) Serveur.ois.readObject();
						} catch (IOException e) {System.out.println(" Erreur de lecture fichier!! ");}

						for (i = a;i<=b;i++){
							System.out.println(Serveur.listRes.get(i));
							listeResultats.add(""+Serveur.listRes.get(i));
						}
						try {
								sisw.reset();
							}
							catch(Exception e){}
						sisw.writeObject(listeResultats);
						listeResultats.clear();
					


				}
				}
				

				else if (msg.getC().getTexte().equals("NB OCCURENCE")){
					try {
					sisw.reset();
					}
					catch(Exception e){}
					int x = msg.getX();
					int cpt = 0;

					for(int value : Serveur.listRes.values())
					{
						if (x == value){
							cpt++;
						}
					}
					listeResultats.add("Le nombre d'occurence de "+x+" est de "+cpt);
					sisw.writeObject(listeResultats);
					listeResultats.clear();
				}
				else if (msg.getC().getTexte().equals("LIST WORKER"))
				{
					 sisw.reset();
				

					for ( int  i= 0   ; i <Serveur.listWorker.size() ; i++ )

					{ 
						listeResultats.add(" [Worker]  "+Serveur.listWorker.get(i))  ; 
					}
					sisw.writeObject(listeResultats) ; 
					listeResultats.clear();}


				else if (msg.getC().getTexte().equals("PERSIST MAX")){
					try {
					sisw.reset();
					}
					catch(Exception e){}
					int x = 0;

					for (int i =0 ; i<=11;i++){
						if (Serveur.listRes.containsValue(i)){
							x = i;
						}
					}

					listeResultats.add("La persistance max actuelle est de "+x);
					
					for(int key : Serveur.listRes.keySet())
					{
						if (Serveur.listRes.get(key) == x){
							listeResultats.add(""+key);
						}
					}
					
					sisw.writeObject(listeResultats);
					listeResultats.clear();
				}
				
				else{
					listeResultats.add("Veuillez refaire une autre commande.");
					sisw.writeObject(listeResultats);
					listeResultats.clear();
				}

		
			} 



			

		} catch(Exception e) {
			System.out.println(" Client "+this.id + " s'est deconnecté  =============");
		}
        try {
            sisr.close();
            sisw.close() ; 
            s.close(); 
        } catch (Exception e) {
			System.out.println("\nClient "+this.id + " s'est deconnecté  =============");
        }
       
	}
}


/*
				 */