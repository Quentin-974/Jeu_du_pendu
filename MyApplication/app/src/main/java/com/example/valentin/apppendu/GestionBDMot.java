package com.example.valentin.apppendu;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thibaut on 01/02/2017.
 */

public class GestionBDMot extends SQLiteOpenHelper {

    /** Nom de la table categorie */
    public static final String NOM_TABLE_MOT = "mot";

    /** Nom de la clef primaire de la table mot */
    public static final String MOT_CLEF = "_id";

    /** Nom du champ correspondant au mot */
    public static final String MOT_NOM = "nom";

    /** Nom du champ correspondant à la difficulté du mot */
    public static final String MOT_DIFFICULTE = "difficulte";

    /** Nom du champ correspondant à la catégorie du mot */
    public static final String MOT_CATEGORIE = "categorie";

    /** Requête de création de la table mot */
    public static final String CREATION_TABLE_MOT =
            "CREATE TABLE " + NOM_TABLE_MOT + " ("
                    + MOT_CLEF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + MOT_NOM + " TEXT NOT NULL, "
                    + MOT_DIFFICULTE + " INTEGER NOT NULL, "
                    + MOT_CATEGORIE + " INTEGER NOT NULL, "
                    + "FOREIGN KEY (" + MOT_CATEGORIE
                    + ") REFERENCES " + GestionBDCategorie.NOM_TABLE_CATEGORIE + " ("
                    + GestionBDCategorie.CATEGORIE_CLEF + "));";

    /** Requête pour sélectionner toutes les catégories */
    public static final String REQUETE_MOT_ALL =
            "SELECT "
                    + MOT_CLEF + ", "
                    + MOT_NOM + ", "
                    + MOT_DIFFICULTE + ", "
                    + MOT_CATEGORIE + ", "
                    + " FROM " + NOM_TABLE_MOT
                    + " ORDER BY " + MOT_NOM + ";";

    private static final String SUPPRIMER_TABLE_MOT =
            "DROP TABLE IF EXISTS " + NOM_TABLE_MOT + ";";

    public GestionBDMot (Context contexte, String nom, SQLiteDatabase.CursorFactory fabrique, int version) {
        super(contexte, nom, fabrique, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATION_TABLE_MOT);	// Création de la table

        ContentValues enregistrement = new ContentValues();

        // Insertion des mots dans la table

        //============ M O T  1 ============//
        enregistrement.put(MOT_NOM, "mot");
        enregistrement.put(MOT_DIFFICULTE, "1");
        enregistrement.put(MOT_CATEGORIE, "1");
        db.insert(NOM_TABLE_MOT, MOT_NOM, enregistrement);

        //============  ==================//

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int ancienneVersion, int nouvelleVersion) {
        db.execSQL(SUPPRIMER_TABLE_MOT);	// Destruction de la table
        onCreate(db);
    }



}
