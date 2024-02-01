package worker;
import client.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.annotation.processing.Generated;




public class Worker {
    static  int nbrDeCoeurs ; 
    static  int port  = 9632;  
    static   Hashtable<Integer , Integer >  data   = new Hashtable<Integer,Integer >()  ; 
    static int cpt  = 0 ; 
    static  int cptArret  = 0 ; 
   



    // calcul de la persistance 


    public static synchronized  int persistance(int nbr )
    { 
        int p=0; 
     
        int produit;
        while (nbr >9){
            produit = 1;
            while(nbr !=0){
                produit = produit * (nbr%10);
                nbr /= 10;
            }
            p++;
            nbr = produit;
        }
        return p   ;  
    }




    public static void main(String[] args) throws Exception {
    
       
        
      
        if(args.length!=0) 
        {
            nbrDeCoeurs =  Integer.parseInt(args[1]) ; 
        }
           
        else {
            nbrDeCoeurs =  Runtime.getRuntime().availableProcessors(); // on recupere le nombre de coeurs 
        }
         System.out.println(InetAddress.getLocalHost().toString());   // on affichage l'adresse locale du PC
         Socket socket  = new Socket(args[0] , port) ; 
         
         ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream()) ; 
         ObjectInputStream reader = new ObjectInputStream(socket.getInputStream()) ;  

        
        // allocation du thread  computer en fonction du  nombre de coeurs; 
         Computer c[]  = new Computer[nbrDeCoeurs] ; 
        

        while(cpt!=1000000000)
        {
        
         //on cree les threads en fonction du nombre de coeurs  disponibles  ; 
         try {
             cpt  =  (int) reader.readObject() ; 
         } catch (Exception e) {
         }
         cptArret = 0 ; 
        while (cptArret!=50) 
    
        {
            for(int i  =  0 ; i < nbrDeCoeurs  ; i ++)
        
            {
                c[i] =  new Computer(cpt*10000) ; 
                c[i].start(); 
            }
          
        
            if( data.size()>=200000)

            {
                System.out.print("++++++++++++++++++++");
       
            }
         
             if (data.size()>=1000000)
             {
                try {
                    System.out.print("\033[H\033[2J"); 
   					System.out.flush(); 
                    writer.reset();
                    writer.writeObject(data) ;
                    System.out.println(" \n  ////////////////////////////////////////////");
                    System.out.println("||                                            ||") ; 
                    System.out.println("||                                            ||") ; 
                    System.out.println("||                    SEND                    ||");
                    System.out.println("||                                            ||") ; 
                    System.out.println("||                                            ||") ; 
                    System.out.println("   ////////////////////////////////////////////");
                    
                } catch (IOException e) {
                    System.out.println("HashTable non envoyée");

                }


                data.clear();
                cptArret++  ;  
                try {
                    writer.reset();
                    writer.writeObject(cptArret);
                    
                } catch (Exception e) {
    
                }
            }
            cpt ++  ; 


        }
 

    }
 socket.close() ; 
    }      
}





class Computer extends Thread {

    int  i  ;

    Computer( int i )
    {
       
        this.i  =  i  ;
    }



    public void run()
    
    {  
       
    for (int k   = this.i  ; k< this.i+10000; k++ )  
    {
           
            
        Worker.data.put(k,Worker.persistance(k)) ; 
            
    
    }


   
}

}