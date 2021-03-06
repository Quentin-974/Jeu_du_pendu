package com.example.valentin.apppendu.ClasseMetier;

import java.util.List;

/**
 * Created by syldu on 02/02/2017.
 */

public class Mot {

    private static final int MOT_FACILE = 5;
    private static final int MOT_NORMAL = 10;

    private int id;
    private String libelle;
    private int difficulte;
    // private List<Mot> dejaJoue;  --> TODO déplacer dans Partie1joueur
    private Categorie categorie;

    public Mot(int id, String libelle, int difficulte, Categorie categorie) {
        this.id = id;
        this.libelle = libelle;
        this.difficulte = difficulte;
        this.categorie = categorie;
    }

    public Mot() {
        id = -1;
        libelle = "Undefined";
        difficulte = -1;
        categorie = null;
    }

    /**
     * Permet de recuperer une liste de mot
     * @param nbLettreMax nombre de lettre maximale des mots
     * @param categorie categorie des mots
     * @return la liste de mots correpondants
     */
    public List<Mot> getMot(int nbLettreMax,Categorie categorie){
        return null;
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

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    /**
     * Détermination de la difficulté d'un mot passé en argument
     * @param mot mot dont l'on cherche la difficuté d'un mot
     * @return 0 si le mot est facile, 1 si il est moyen, 2 si il est difficile
     */
    public static int difficultes(String mot) {
        if (mot.length() < MOT_FACILE ) {
            return 0;
        } else if (mot.length() >= MOT_FACILE  && mot.length() < MOT_NORMAL) {
            return 1;
        } else {
            return 2;
        }
    }

}
