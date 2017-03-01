package com.example.valentin.apppendu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.valentin.apppendu.ClasseMetier.Joueur;
import com.example.valentin.apppendu.ClasseMetier.Score;
import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDHistorique;
import com.example.valentin.apppendu.GestionBD.GestionBDJoueur;

/**
 * Created by thibaut on 01/03/2017.
 */

public class HistoriqueDAO {

    // Champs de la base de données
    private SQLiteDatabase database;
    private GestionBD gestionBD;

    public HistoriqueDAO(Context contexte) {
        gestionBD = GestionBD.getInstance(contexte);
    }

    public void open() {
        database = gestionBD.getWritableDatabase();
    }

    public void close() {
        gestionBD.close();
    }

    public long createHistorique(String date, String heure, int nbMotsTrouve, Joueur joueur){
        ContentValues values = new ContentValues();

        values.put(GestionBDHistorique.HISTORIQUE_DATE, date);
        values.put(GestionBDHistorique.HISTORIQUE_HEURE, heure);
        values.put(GestionBDHistorique.HISTORIQUE_NB_GAGNES, nbMotsTrouve);
        values.put(GestionBDHistorique.HISTORIQUE_JOUEUR, joueur.getId());
        long id = database.insert(GestionBDHistorique.NOM_TABLE_HISTORIQUE,
                GestionBDHistorique.HISTORIQUE_DATE, values);

        return id;
    }

    public int deleteHistorique(Score score) {
        int id = score.getId();

        int nbLignesSupp = database.delete(GestionBDHistorique.NOM_TABLE_HISTORIQUE,
                GestionBDHistorique.HISTORIQUE_CLEF + " = ?",
                new String[] {String.valueOf(id)});

        return nbLignesSupp;
    }

    public void updateHistorique(Score score) {
        ContentValues values = new ContentValues();

        values.put(GestionBDHistorique.HISTORIQUE_DATE, score.getDate());
        values.put(GestionBDHistorique.HISTORIQUE_HEURE, score.getHeure());
        values.put(GestionBDHistorique.HISTORIQUE_NB_GAGNES, score.getNbMotsTrouve());
        values.put(GestionBDHistorique.HISTORIQUE_JOUEUR, score.getJoueur().getId());
        String[] param = {String.valueOf(score.getId())};
        database.update(GestionBDJoueur.NOM_TABLE_JOUEUR, values, GestionBDJoueur.JOUEUR_CLEF + " = ?", param);
    }

    public Cursor getAllJoueurs() {
        Cursor cursor = database.rawQuery(GestionBDJoueur.REQUETE_JOUEUR_ALL, null);
        return cursor;
    }
}
