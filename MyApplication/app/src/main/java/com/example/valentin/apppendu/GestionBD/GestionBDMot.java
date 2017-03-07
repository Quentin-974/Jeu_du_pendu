package com.example.valentin.apppendu.GestionBD;

/**
 * Created by thibaut on 01/02/2017.
 */

public class GestionBDMot {

    /** Nom de la table categorie */
    public static final String NOM_TABLE_MOT = "mot";

    /** Nom de la clef primaire de la table mot */
    public static final String MOT_CLEF = "_id";

    /** Nom du champ correspondant au mot */
    public static final String MOT_NOM = "nom";

    /** Nom du champ correspondant à la difficulté du mot */
    public static final String MOT_DIFFICULTE = "difficulte";

    /** Nom du champ correspondant à la catégorie du mot */
    public static final String MOT_CATEGORIE = "idCategorie";

    /** Requête de création de la table mot */
    public static final String CREATION_TABLE_MOT =

    "CREATE TABLE " + NOM_TABLE_MOT + " ("
            + MOT_CLEF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MOT_NOM + " TEXT NOT NULL, "
            + MOT_DIFFICULTE + " INTEGER NOT NULL, "
            + MOT_CATEGORIE + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + MOT_CATEGORIE + ")"
            + " REFERENCES " + GestionBDCategorie.NOM_TABLE_CATEGORIE
            +" (" + GestionBDCategorie.CATEGORIE_CLEF + ")"
            + ");";

    /** Requête pour sélectionner tous les mots */
    public static final String REQUETE_MOT_ALL =
            "SELECT "
                    + MOT_CLEF + ", "
                    + MOT_NOM + ", "
                    + MOT_DIFFICULTE + ", "
                    + MOT_CATEGORIE
                    + " FROM " + NOM_TABLE_MOT
                    + " ORDER BY " + MOT_NOM + ";";

    /** Requête pour sélectionner tous les mots d'une catégorie */
    public static final String REQUETE_MOTS_CATEGORIE =
            "SELECT * FROM " + NOM_TABLE_MOT + " WHERE " + MOT_CATEGORIE + " = ?";

    /** Requête pour sélectionner tous les mots d'une catégorie pour une difficulté donnée */
    public static final String REQUETE_MOTS_CAT_DIFF =
            "SELECT * FROM " + NOM_TABLE_MOT + " WHERE " + MOT_CATEGORIE + " = ? AND "
                    + MOT_DIFFICULTE + " = ?";

    /** Requête pour sélectionner tous les mots d'une catégorie pour une difficulté donnée */
    public static final String REQUETE_MOTS_DIFF =
            "SELECT * FROM " + NOM_TABLE_MOT + " WHERE " + MOT_DIFFICULTE + " = ?";

    public static final String REQUETE_MOT_NOM_CAT =
            "SELECT * FROM " + NOM_TABLE_MOT + " WHERE "
                    + MOT_NOM + " = ? AND " + MOT_CATEGORIE + " = ?";

    public static final String SUPPRIMER_TABLE_MOT =
            "DROP TABLE IF EXISTS " + NOM_TABLE_MOT + ";";

    public static final String RECUP_NOM_CATEGORIE_MOT =
            "SELECT " + GestionBDCategorie.NOM_TABLE_CATEGORIE + "." + GestionBDCategorie.CATEGORIE_NOM + " FROM " + NOM_TABLE_MOT + " JOIN "
            + GestionBDCategorie.NOM_TABLE_CATEGORIE + " ON "
            + NOM_TABLE_MOT + "." + MOT_CLEF + " = " + GestionBDCategorie.NOM_TABLE_CATEGORIE + "." + GestionBDCategorie.CATEGORIE_CLEF
            + " WHERE " + GestionBDCategorie.NOM_TABLE_CATEGORIE + "." + GestionBDCategorie.CATEGORIE_CLEF + " = ?";
}
