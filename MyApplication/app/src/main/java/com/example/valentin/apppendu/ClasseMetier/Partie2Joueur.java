package com.example.valentin.apppendu.ClasseMetier;

import java.util.List;

/**
 * Created by syldu on 02/02/2017.
 */

public class Partie2Joueur {

    private Joueur joueur1;
    private Joueur joueur2;
    private Score scoreJoueur1;
    private Score scoreJoueur2;
    private int nbMotsAJouer;
    private List<Mot> motsJoueur1;
    private List<Mot> motsJoueur2;

    public Partie2Joueur(Joueur joueur1, Joueur joueur2, Score scoreJoueur1, Score scoreJoueur2, int nbMotsAJouer, List<Mot> motsJoueur1, List<Mot> motsJoueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.scoreJoueur1 = scoreJoueur1;
        this.scoreJoueur2 = scoreJoueur2;
        this.nbMotsAJouer = nbMotsAJouer;
        this.motsJoueur1 = motsJoueur1;
        this.motsJoueur2 = motsJoueur2;
    }

    public Joueur getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Joueur joueur1) {
        this.joueur1 = joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Joueur joueur2) {
        this.joueur2 = joueur2;
    }

    public Score getScoreJoueur1() {
        return scoreJoueur1;
    }

    public void setScoreJoueur1(Score scoreJoueur1) {
        this.scoreJoueur1 = scoreJoueur1;
    }

    public Score getScoreJoueur2() {
        return scoreJoueur2;
    }

    public void setScoreJoueur2(Score scoreJoueur2) {
        this.scoreJoueur2 = scoreJoueur2;
    }

    public int getNbMotsAJouer() {
        return nbMotsAJouer;
    }

    public void setNbMotsAJouer(int nbMotsAJouer) {
        this.nbMotsAJouer = nbMotsAJouer;
    }

    public List<Mot> getMotsJoueur1() {
        return motsJoueur1;
    }

    public void setMotsJoueur1(List<Mot> motsJoueur1) {
        this.motsJoueur1 = motsJoueur1;
    }

    public List<Mot> getMotsJoueur2() {
        return motsJoueur2;
    }

    public void setMotsJoueur2(List<Mot> motsJoueur2) {
        this.motsJoueur2 = motsJoueur2;
    }
}
