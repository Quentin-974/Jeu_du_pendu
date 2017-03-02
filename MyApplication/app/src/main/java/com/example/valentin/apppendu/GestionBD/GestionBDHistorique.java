package com.example.valentin.apppendu.GestionBD;

/**
 * Created by thibaut on 01/02/2017.
 */

public class GestionBDHistorique {

    /** Nom de la table categorie */
    public static final String NOM_TABLE_HISTORIQUE = "historique";

    /** Nom de la clef primaire de la table mot */
    public static final String HISTORIQUE_CLEF = "_id";

    /** Nom du champ correspondant à une date */
    public static final String HISTORIQUE_DATE = "date";

    /** Nom du champ correspondant à l'heure */
    public static final String HISTORIQUE_HEURE = "heure";

    /** Nom du champ correspondant aux nombres de coups gagnés */
    public static final String HISTORIQUE_NB_GAGNES = "nb_gagnes";

    /** Nom du champ correspondant à l'id du joueur */
    public static final String HISTORIQUE_JOUEUR = "joueur";

    /** Nom du champ correspondant à l'id de la catégorie */
    public static final String HISTORIQUE_DIFFICULTE = "difficulte";

    /** Requête de création de la table mot */
    public static final String CREATION_TABLE_HISTORIQUE =
            "CREATE TABLE " + NOM_TABLE_HISTORIQUE + " ("
                    + HISTORIQUE_CLEF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + HISTORIQUE_DATE + " TEXT NOT NULL, "
                    + HISTORIQUE_HEURE + " TEXT NOT NULL, "
                    + HISTORIQUE_NB_GAGNES + " INTEGER NOT NULL, "
                    + HISTORIQUE_JOUEUR + " INTEGER NOT NULL, "
                    + HISTORIQUE_DIFFICULTE + " INTEGER NOT NULL, "
                    + "FOREIGN KEY (" + HISTORIQUE_JOUEUR + ")"
                    + " REFERENCES " + GestionBDJoueur.NOM_TABLE_JOUEUR
                    + " (" + GestionBDJoueur.JOUEUR_CLEF + "));";

    /** Requête pour sélectionner toutes les catégories */
    public static final String REQUETE_HISTORIQUE_ALL =
            "SELECT * FROM " + NOM_TABLE_HISTORIQUE;

    public static final String SUPPRIMER_TABLE_HISTORIQUE =
            "DROP TABLE IF EXISTS " + NOM_TABLE_HISTORIQUE + ";";
}