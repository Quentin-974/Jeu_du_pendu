package com.example.valentin.apppendu.classeMetier;

/**
 * Created by syldu on 02/02/2017.
 */


public class Score {

    private int id;
    private int nbMotsTrouve;
    private int classement;

    private Joueur joueur;

    public Score(int id, int nbMotsTrouve,int classement,Joueur joueur){
        this.id = id;
        this.nbMotsTrouve = nbMotsTrouve;
        this.classement = classement;
        this.joueur = joueur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbMotsTrouve() {
        return nbMotsTrouve;
    }

    public void setNbMotsTrouve(int nbMotsTrouve) {
        this.nbMotsTrouve = nbMotsTrouve;
    }

    public int getClassement() {
        return classement;
    }

    public void setClassement(int classement) {
        this.classement = classement;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
}
