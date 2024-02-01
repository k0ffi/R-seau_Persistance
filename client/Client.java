package client ; 
import java.lang.*; 
import java.io.*;
import java.net.*;
import java.util.*;

import javax.lang.model.util.ElementScanner14;


public class Client{
    static int port = 8080;
    static boolean arreter = false;
	static int id  ;  

	static int getNbr(String str) 
    { 
        // Remplacer chaque nombre non numérique par un espace
        str = str.replaceAll("[^\\d]", " "); 
        // Supprimer les espaces de début et de fin 
        str = str.trim(); 
        // Remplacez les espaces consécutifs par un seul espace
        str = str.replaceAll(" +", " "); 
		if(str=="")
	{

		return 0 ; 
	}else{
		return Integer.parseInt(str) ;  
	}

    }

	
	public static boolean estEntier(Object variable) {
		try {
			Integer.parseInt(variable.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

    public static void main(String[] args) throws Exception {
    
        Socket socket = new Socket(args[0], port);
        System.out.println("Socket ="+socket);
		
		ObjectOutputStream sisw = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream sisr = new ObjectInputStream(socket.getInputStream());
        GererSaisie saisie=new GererSaisie(sisw);
		saisie.start();
		ArrayList<String> listeResultats = new ArrayList<String>();
		while (!arreter) {
			
			try {
				try {
					sisw.reset();
				}
				catch(Exception e){}

				Client.id =(int) sisr.readObject() ;
				listeResultats = (ArrayList<String>) sisr.readObject();
			
				for (int i =0;i<listeResultats.size();i++){
					System.out.println("///////////////////////////////////////////////////////////////////////////////////////////");
					System.out.print("====>                                ["+listeResultats.get(i) +"]                   \n") ;
				}
				
			} catch (Exception e) {
				try {
					Thread.currentThread();
					Thread.sleep(100);
				} catch (InterruptedException e1) {}
			}
		}
	     
      
        
		
		socket.close();
	}
}


 class GererSaisie extends Thread {
	private BufferedReader entreeClavier;
	private ObjectOutputStream write;

	public GererSaisie(ObjectOutputStream pw) {
		
		entreeClavier = new BufferedReader(new InputStreamReader(System.in));
		this.write = pw;
		
	}

	public void run() {
		String str;
		int a =0 ; 
		int b =0 ; 
		Message msg  =  new Message(Client.id,Commande.Vide) ; 
		try {
		

			System.out.print("\033[H\033[2J"); 
			System.out.flush();  
		 System.out.println("Voici la liste des COMMANDES ");
		 System.out.println("-----------------------------------") ; 
		 System.out.println("1- PERSISTANCE ");
		 System.out.println("2- LIST WORKER") ; 
		 System.out.println("3- STATISTIQUE") ; 
		 System.out.println("4- NB OCCURENCE" ) ; 
		 System.out.println("5- INTERVALLE") ; 
		 System.out.println("6- PERSIST MAX") ; 
	  
			while(!(str = entreeClavier.readLine()).equals("END"))
			{ 

					System.out.print("\033[H\033[2J"); 
   					System.out.flush();  
					System.out.println("Voici la liste des COMMANDES ");
					System.out.println("-----------------------------------") ; 
					System.out.println("1- PERSISTANCE ");
					System.out.println("2- LIST WORKER") ; 
					System.out.println("3- STATISTIQUE") ; 
					System.out.println("4- NB OCCURENCE" ) ; 
					System.out.println("5- INTERVALLE") ; 
					System.out.println("6- PERSIST MAX") ; 
			     
                if(str.startsWith(Commande.ListWorker.texte)){
                    msg.setC(Commande.ListWorker) ; 
                }

				else if(str.startsWith(Commande.Persistance.texte)) {
					msg.setC(Commande.Persistance) ; 
				 	msg.setX(Client.getNbr(str)) ;
				}

                else if(str.startsWith(Commande.Statisque.texte)) {
					
					
					System.out.println("\n >> Veuillez saisir votre plage de nombre   [a,b]"); 
					System.out.println("a >> ")  ;  
					boolean nb1=false;
					boolean nb2=false;

					String entier ="";
					do {
						nb1=Client.estEntier(entier=entreeClavier.readLine());
					
					} while (nb1!=true);
					a=Integer.parseInt(entier);
					System.out.println("b >> ")  ; 
					do {
						nb2=Client.estEntier(entier=entreeClavier.readLine());

					} while (nb2!=true);
					b=Integer.parseInt(entier);
					
					
                    msg.setC( Commande.Statisque ); 
					msg.setBorneA(a);
					msg.setBorneB(b);

                }
                else if (str.startsWith(Commande.NombreDoccurence.texte)){
                    msg.setC( Commande.NombreDoccurence) ; 
					msg.setX(Client.getNbr(str)) ; 
                }

				else if(str.startsWith(Commande.Intervalle.texte)) {
					System.out.println("\n >> Veuillez saisir votre plage de nombre   [a,b]"); 
					System.out.println("a >> ")  ;  
					boolean nb1=false;
					boolean nb2=false;

					String entier ="";
					do {
						
						nb1=Client.estEntier(entier=entreeClavier.readLine());
					} while (nb1!=true);
					a=Integer.parseInt(entier);
					System.out.println("b >> ")  ; 
					do {
						
						nb2=Client.estEntier(entier=entreeClavier.readLine());
					} while (nb2!=true);
					b=Integer.parseInt(entier);
					
					
                    msg.setC( Commande.Intervalle ); 
					msg.setBorneA(a);
					msg.setBorneB(b);
				}

				else if (str.startsWith(Commande.PersistMax.texte)){
					msg.setC(Commande.PersistMax) ; 
				}
				else {

					System.out.print("\033[H\033[2J"); 
   					System.out.flush();  
					System.out.println("Voici la liste des COMMANDES ");
					System.out.println("-----------------------------------") ; 
					System.out.println("1- PERSISTANCE ");
					System.out.println("2- LIST WORKER") ; 
					System.out.println("3- STATISTIQUE") ; 
					System.out.println("4- NB OCCURENCE" ) ; 
					System.out.println("5- INTERVALLE") ; 
					System.out.println("6- PERSIST MAX") ; 
			     
					msg.setC(Commande.Vide) ; 

				}
				
				try {
					write.reset();
					
				} catch (IOException e) {
					// TODO: handle exception
				}
				
			
                write.writeObject(msg);
			
		
			


			}   
		} catch (Exception e) {
		e.printStackTrace() ; 
		}  
		// envois l'arret de la connexion au serveur qui en fonction de sa arretra la connexion 
		try {
			write.writeObject(msg) ; 
			write.close() ; 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}