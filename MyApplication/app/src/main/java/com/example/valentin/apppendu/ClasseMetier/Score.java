package com.example.valentin.apppendu.ClasseMetier;

/**
 * Created by syldu on 02/02/2017.
 */


public class Score {

    private int id;
    private String date;
    private String heure;
    private int nbMotsTrouve;
    private Joueur joueur;
    private int difficulte;

    public Score(int id, String date, String heure, int nbMotsTrouve, Joueur joueur, int difficulte){
        this.id = id;
        this.date = date;
        this.heure = heure;
        this.nbMotsTrouve = nbMotsTrouve;
        this.joueur = joueur;
        this.difficulte = difficulte;
    }

    public Score() {
        id = -1;
        date = "Undefined";
        heure = "Undefined";
        nbMotsTrouve = -1;
        joueur = null;
        difficulte = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getNbMotsTrouve() {
        return nbMotsTrouve;
    }

    public void setNbMotsTrouve(int nbMotsTrouve) {
        this.nbMotsTrouve = nbMotsTrouve;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }
}
