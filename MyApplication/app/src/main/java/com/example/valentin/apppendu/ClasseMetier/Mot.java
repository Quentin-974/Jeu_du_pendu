package com.example.valentin.apppendu.classeMetier;

import java.util.List;

/**
 * Created by syldu on 02/02/2017.
 */

public class Mot {

    private int id;
    private String libelle;
    private int difficulte;
    private List<Mot> dejaJoue;
    private Categorie categorie;

    public Mot(int id, String libelle, int difficulte, List<Mot> dejaJoue, Categorie categorie) {
        this.id = id;
        this.libelle = libelle;
        this.difficulte = difficulte;
        this.dejaJoue = dejaJoue;
        this.categorie = categorie;
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

    public List<Mot> getDejaJoue() {
        return dejaJoue;
    }

    public void setDejaJoue(List<Mot> dejaJoue) {
        this.dejaJoue = dejaJoue;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
