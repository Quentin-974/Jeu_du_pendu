package com.example.valentin.apppendu.GestionBD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thibaut on 01/02/2017.
 */

public class GestionBDJoueur extends SQLiteOpenHelper {

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
                    + JOUEUR_NOM + ", "
                    + " FROM " + NOM_TABLE_JOUEUR
                    + " ORDER BY " + JOUEUR_NOM + ";";

    private static final String SUPPRIMER_TABLE_JOUEUR =
            "DROP TABLE IF EXISTS " + NOM_TABLE_JOUEUR + ";";

    public GestionBDJoueur (Context contexte, String nom, SQLiteDatabase.CursorFactory fabrique, int version) {
        super(contexte, nom, fabrique, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATION_TABLE_JOUEUR);	// Création de la table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int ancienneVersion, int nouvelleVersion) {
        db.execSQL(SUPPRIMER_TABLE_JOUEUR);	// Destruction de la table
        onCreate(db);
    }



}