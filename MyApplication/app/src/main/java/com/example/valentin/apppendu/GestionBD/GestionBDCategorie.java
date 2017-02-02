package com.example.valentin.apppendu.GestionBD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thibaut on 01/02/2017.
 */

public class GestionBDCategorie extends SQLiteOpenHelper {

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

    private static final String SUPPRIMER_TABLE_CATEGORIE =
            "DROP TABLE IF EXISTS " + NOM_TABLE_CATEGORIE + ";";

    public GestionBDCategorie (Context contexte, String nom, SQLiteDatabase.CursorFactory fabrique, int version) {
        super(contexte, nom, fabrique, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATION_TABLE_CATEGORIE);	// Création de la table

        ContentValues enregistrement = new ContentValues();

        // Insertion des catégories de base

        //============ A U T R E ============//
        enregistrement.put(CATEGORIE_NOM, "Autre");
        db.insert(NOM_TABLE_CATEGORIE, CATEGORIE_NOM, enregistrement);

        //============ M U S I Q U E ==================//
        enregistrement.put(CATEGORIE_NOM, "Musique");
        db.insert(NOM_TABLE_CATEGORIE, CATEGORIE_NOM, enregistrement);

        //============ S P O R T ==================//
        enregistrement.put(CATEGORIE_NOM, "Sport");
        db.insert(NOM_TABLE_CATEGORIE, CATEGORIE_NOM, enregistrement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int ancienneVersion, int nouvelleVersion) {
        if (ancienneVersion < 3) {
            db.execSQL(SUPPRIMER_TABLE_CATEGORIE);	// Destruction de la table
            onCreate(db);
        }
    }



}