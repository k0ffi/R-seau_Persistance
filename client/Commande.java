package client;

import java.io.*;
import java.net.* ;

public enum Commande implements Serializable {
	Persistance("PERSISTANCE"),
	ListWorker("LIST WORKER"),
	Statisque("STATISTIQUE"),
	NombreDoccurence("NB OCCURENCE"),
	Intervalle("INTERVALLE"),
	PersistMax("PERSIST MAX"),
	Vide("") ; 

    public String texte = "";
	
	private Commande(String texte) {
		this.texte = texte;
	}
	public String getTexte()
	{
		return this.texte ; 
	}
}
