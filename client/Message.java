package client;
import java.lang.*; 
import java.io.*;



public class Message implements Serializable {

    private int idClient  ; 
    private  Commande c ; 
    private int x ; 
    private int borneA ;  
    private  int borneB ; 
    private int[] tab ; 
    private boolean istab    ; 
    private boolean end ; 

    // constructeur 


    
    public Message(int i )
    {
        this.idClient  =  i ; 
        this.c = Commande.Vide  ; 
        this.borneA =0 ; 
        this.borneB =0 ; 
        this.x  = 0 ; 
        this.tab  = new int[100] ; 
        this.istab = false  ; 
        this.end= false ; 
    }

    // test 
    public Message(int id , int a ,  int b )
    {
        this(id,Commande.Vide) ; 
        this.borneA =  a ; 
        this.borneB  = b  ; 
        this.tab = new int[b-a +1] ; 
        this.generateTab();
        this.istab = true ; 
    }


    public Message(int id , Commande c   )  
    {
        this.idClient  =  id ; 
        this.c = c  ; 
        this.borneA =0 ; 
        this.borneB =0 ; 
        this.x  = 0 ; 
        this.tab  = new int[100] ; 
        this.istab = false  ; 
      
    }

    public Message(int id  , Commande c  , int x )
    {
        this(id , c ) ; 
        this.x = x  ; 
       
      
    }

    public Message(int id , Commande c , int a ,  int b )
    {
        this(id,c) ; 
        this.borneA =  a ; 
        this.borneB  = b  ; 
        this.tab = new int[b-a +1] ; 
        this.generateTab();
        this.istab = true ; 
    }



 // getteur 


      public   Commande getC()
        {
            return this.c ; 
        }

        public int getID()
        {
            return this.idClient ; 
        }
        public int getX()
        {
            return this.x ;  
        }
            public int getBornA()
            {
                return this.borneA ;  
            }

        public int getBornB()
        {
            return this.borneB ; 
        }

    public int[] getTab()
    {
        return this.tab   ; 
    }
    public void setTab(int[] t )

    {
        this.tab = t ; 
    }


    public boolean getIsTab()
    { return  this.istab  ; }
     

    public boolean getEnd()
    {
       return  this.end ; 
    }
    // setteurs 
    public void setC(Commande c ) 
    {
        this.c = c   ;  
    }

    public void setID(int id ) 
    {
        this.idClient= id   ;  
    }

    public void setX(int x ) 
    {
        this.x= x  ;  
    }

    public void setBorneA(int a )
    {
        this.borneA  = a  ; 
    }

    public void setBorneB(int b )

    {
        this.borneB   = b   ; 
    }

    public void setEND(  boolean b)
    {
        this.end =   b  ;  
    }



  



    public String toString()
    {
        switch(this.c.texte){
        
        case "MEDIANE":
            return"Le Client "+this.idClient + "  fait la demande "+ this.c +" [ "+this.borneA+","+this.borneB+" ]." ;

        case "MEDIATRICE":
            return"Le Client "+this.idClient + "  fait la demande "+ this.c +" [ "+this.borneA+","+this.borneB+" ]." ;

        case "INTERVALLE":
            return"Le Client "+this.idClient + "  fait la demande "+ this.c +" [ "+this.borneA+","+this.borneB+" ]." ;

        case "PERSISTANCE":
            return"Le Client "+this.idClient + "  fait la demande "+ this.c +" "+this.x+"." ;

        case "NB OCCURENCE":
            return"Le Client "+this.idClient + "  fait la demande "+ this.c +" "+this.x+"." ;

        default:
            return"Le Client "+this.idClient + "  fait la demande "+ this.c+".";
        
        }
    }


    // methodes utiles 


      // permet de generer tous  les nombres appartenabt a l'intervalle [a,b ]
      private void generateTab ()
      {
  
          int  k   =  this.borneA  ; 
          for(int i= 0 ; i< tab.length  ; i++ ){
              this.tab[i]  =  k  ; 
              k++  ; 
          }
      }

 
     

    
}
