package com.example.valentin.apppendu.ClasseMetier;

/**
 * Created by syldu on 02/02/2017.
 */

public class Categorie {

    private int id;
    private String libelle;

    public Categorie(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Categorie() {
        id = -1;
        libelle = "Undefined";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
