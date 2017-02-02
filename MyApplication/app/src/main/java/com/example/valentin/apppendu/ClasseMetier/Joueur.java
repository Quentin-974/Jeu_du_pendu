package com.example.valentin.apppendu.ClasseMetier;

/**
 * Created by syldu on 02/02/2017.
 */

public class Joueur {

    private String nom;

    private int id;

    public Joueur(int id, String nom){
        this.nom = nom;
        this.id = id;
    }

    public String getNom(){
        return this.nom;
    }

    public int getIt(){
        return this.id;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setIt(int id ){
        this.id = id;
    }
}
