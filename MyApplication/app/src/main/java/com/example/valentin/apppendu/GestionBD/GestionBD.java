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
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "0");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "ble");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "0");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "roux");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "0");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "soleil");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "atomes");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "stylos");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "tableaux");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "constitution");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "2");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "electronique");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "2");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "multijoueur");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "2");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "1");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        // Insertion des mots de base dans la catégorie Musique
        enregistrement.put(GestionBDMot.MOT_NOM, "jazz");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "0");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "rock");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "0");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "voix");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "0");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "chanson");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "album");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "concert");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "melodie");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "synthetiseur");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "2");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "musicographie");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "2");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "electroacoustique");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "2");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "2");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);


        // Insertion des mots de base dans la catégorie Sport
        enregistrement.put(GestionBDMot.MOT_NOM, "golf");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "0");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "surf");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "0");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "boxe");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "0");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "tennis");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "curling");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "natation");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "taekwondo");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "1");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "equitation");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "2");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "halterophilie");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "2");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        enregistrement.put(GestionBDMot.MOT_NOM, "gymnastique");
        enregistrement.put(GestionBDMot.MOT_DIFFICULTE, "2");
        enregistrement.put(GestionBDMot.MOT_CATEGORIE, "3");
        db.insert(GestionBDMot.NOM_TABLE_MOT, GestionBDMot.MOT_NOM, enregistrement);

        // AJOUT lignes JOUEURS
        enregistrement.put(GestionBDJoueur.JOUEUR_NOM, "Patrick");
        db.insert(GestionBDJoueur.NOM_TABLE_JOUEUR, GestionBDJoueur.JOUEUR_NOM, enregistrement);

        enregistrement.put(GestionBDJoueur.JOUEUR_NOM, "ZENNNN");
        db.insert(GestionBDJoueur.NOM_TABLE_JOUEUR, GestionBDJoueur.JOUEUR_NOM, enregistrement);

        // AJOUT lignes HISTORIQUE
        enregistrement.put(GestionBDHistorique.HISTORIQUE_DATE, "01/03/2017");
        enregistrement.put(GestionBDHistorique.HISTORIQUE_HEURE, "11h06");
        enregistrement.put(GestionBDHistorique.HISTORIQUE_NB_GAGNES, 5);
        enregistrement.put(GestionBDHistorique.HISTORIQUE_JOUEUR, 1);
        enregistrement.put(GestionBDHistorique.HISTORIQUE_DIFFICULTE, 0);
        db.insert(GestionBDHistorique.NOM_TABLE_HISTORIQUE, GestionBDHistorique.HISTORIQUE_DATE, enregistrement);

        enregistrement.put(GestionBDHistorique.HISTORIQUE_DATE, "15/10/2017");
        enregistrement.put(GestionBDHistorique.HISTORIQUE_HEURE, "17h35");
        enregistrement.put(GestionBDHistorique.HISTORIQUE_NB_GAGNES, 3);
        enregistrement.put(GestionBDHistorique.HISTORIQUE_JOUEUR, 2);
        enregistrement.put(GestionBDHistorique.HISTORIQUE_DIFFICULTE, 2);
        db.insert(GestionBDHistorique.NOM_TABLE_HISTORIQUE, GestionBDHistorique.HISTORIQUE_DATE, enregistrement);
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
