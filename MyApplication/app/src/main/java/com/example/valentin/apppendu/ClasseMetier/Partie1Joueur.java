package com.example.valentin.apppendu.classeMetier;

/**
 * Created by syldu on 02/02/2017.
 */

public class Partie1Joueur {

    private Joueur joueur;
    private Score score;

    public Partie1Joueur(Joueur joueur, Score score) {
        this.joueur = joueur;
        this.score = score;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
