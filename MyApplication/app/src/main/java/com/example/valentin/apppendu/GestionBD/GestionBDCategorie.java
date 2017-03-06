package com.example.valentin.apppendu.GestionBD;

/**
 * Created by thibaut on 01/02/2017.
 */

public class GestionBDCategorie {

    /** Nom de la table categorie */
    public static final String NOM_TABLE_CATEGORIE = "categorie";

    /** Nom de la clef primaire de la table categorie */
    public static final String CATEGORIE_CLEF = "_id";

    /** Nom du champ correspondant au nom de la catégorie */
    public static final String CATEGORIE_NOM = "nom";

    /** Requête de création de la table categorie */
    public static final String CREATION_TABLE_CATEGORIE =
            "CREATE TABLE " + NOM_TABLE_CATEGORIE + " ("
                    + CATEGORIE_CLEF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CATEGORIE_NOM + " TEXT NOT NULL);";

    /** Requête pour sélectionner toutes les catégories */
    public static final String REQUETE_CATEGORIE_ALL =
            "SELECT "
                    + CATEGORIE_CLEF + ", "
                    + CATEGORIE_NOM
                    + " FROM " + NOM_TABLE_CATEGORIE
                    + " ORDER BY '" + CATEGORIE_NOM + "';";


    /** Nombres de catégories dans la bd */
    public static final String REQUETE_COUNT =
            "SELECT COUNT(DISTINCT(_id)) FROM " + NOM_TABLE_CATEGORIE;

    public static final String SUPPRIMER_TABLE_CATEGORIE =
            "DROP TABLE IF EXISTS " + NOM_TABLE_CATEGORIE + ";";

}