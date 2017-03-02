package com.example.valentin.apppendu.GestionBD;

/**
 * Created by thibaut on 01/02/2017.
 */

public class GestionBDJoueur {

    /** Nom de la table categorie */
    public static final String NOM_TABLE_JOUEUR = "joueur";

    /** Nom de la clef primaire de la table mot */
    public static final String JOUEUR_CLEF = "_id";

    /** Nom du champ correspondant au mot */
    public static final String JOUEUR_NOM = "nom";

    /** Requête de création de la table mot */
    public static final String CREATION_TABLE_JOUEUR =
            "CREATE TABLE " + NOM_TABLE_JOUEUR + " ("
                    + JOUEUR_CLEF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + JOUEUR_NOM + " TEXT NOT NULL);";

    /** Requête pour sélectionner toutes les catégories */
    public static final String REQUETE_JOUEUR_ALL =
            "SELECT "
                    + JOUEUR_CLEF + ", "
                    + JOUEUR_NOM + " "
                    + " FROM " + NOM_TABLE_JOUEUR
                    + " ORDER BY " + JOUEUR_NOM + ";";

    public static final String SUPPRIMER_TABLE_JOUEUR =
            "DROP TABLE IF EXISTS " + NOM_TABLE_JOUEUR + ";";

}