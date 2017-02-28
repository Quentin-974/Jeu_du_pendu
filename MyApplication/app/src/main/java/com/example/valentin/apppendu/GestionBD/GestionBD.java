package com.example.valentin.apppendu.GestionBD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thibaut on 09/02/2017.
 */

public class GestionBD extends SQLiteOpenHelper {

    /** Numéro de version de la base de données */
    private static final int VERSION = 6;

    /** Nom de la base de données dans laquelle seront conservé les catégories */
    private static final String NOM_BD = "pendu.db";

    /** Instance unique de gestionBD */
    private static GestionBD instance;

    private GestionBD(Context contexte) {
        super(contexte, NOM_BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Création des tables
        db.execSQL(GestionBDCategorie.CREATION_TABLE_CATEGORIE);
        db.execSQL(GestionBDMot.CREATION_TABLE_MOT);

        ContentValues enregistrement = new ContentValues();

        // Insertion des catégories de base
        enregistrement.put(GestionBDCategorie.CATEGORIE_NOM, "Autre");
        db.insert(GestionBDCategorie.NOM_TABLE_CATEGORIE, GestionBDCategorie.CATEGORIE_NOM, enregistrement);

        enregistrement.put(GestionBDCategorie.CATEGORIE_NOM, "Musique");
        db.insert(GestionBDCategorie.NOM_TABLE_CATEGORIE, GestionBDCategorie.CATEGORIE_NOM, enregistrement);

        enregistrement.put(GestionBDCategorie.CATEGORIE_NOM, "Sport");
        db.insert(GestionBDCategorie.NOM_TABLE_CATEGORIE, GestionBDCategorie.CATEGORIE_NOM, enregistrement);

        // Insertion des mots de base dans la catégorie Autre
        enregistrement.put(GestionBDMot.MOT_NOM, "mot");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "mot2");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int ancienneVersion, int nouvelleVersion) {
        if (ancienneVersion < 4) {
            db.execSQL(GestionBDMot.SUPPRIMER_TABLE_MOT);	// Destruction de la table
            db.execSQL(GestionBDCategorie.SUPPRIMER_TABLE_CATEGORIE);	// Destruction de la table
            onCreate(db);
        }

    }
    public static synchronized GestionBD getInstance(Context contexte) {
        if (instance == null) {
            instance = new GestionBD(contexte.getApplicationContext());
        }
        return instance;
    }


}
