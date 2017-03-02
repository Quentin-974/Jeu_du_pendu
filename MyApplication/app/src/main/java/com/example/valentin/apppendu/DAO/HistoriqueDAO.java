package com.example.valentin.apppendu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.valentin.apppendu.ClasseMetier.Categorie;
import com.example.valentin.apppendu.ClasseMetier.Joueur;
import com.example.valentin.apppendu.ClasseMetier.Score;
import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDHistorique;
import com.example.valentin.apppendu.GestionBD.GestionBDJoueur;

import java.util.ArrayList;

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

    public long createHistorique(String date, String heure, int nbMotsTrouve, Joueur joueur, int difficulte){
        ContentValues values = new ContentValues();

        values.put(GestionBDHistorique.HISTORIQUE_DATE, date);
        values.put(GestionBDHistorique.HISTORIQUE_HEURE, heure);
        values.put(GestionBDHistorique.HISTORIQUE_NB_GAGNES, nbMotsTrouve);
        values.put(GestionBDHistorique.HISTORIQUE_JOUEUR, joueur.getId());
        values.put(GestionBDHistorique.HISTORIQUE_DIFFICULTE, difficulte);
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
        values.put(GestionBDHistorique.HISTORIQUE_DIFFICULTE, score.getDifficulte());
        String[] param = {String.valueOf(score.getId())};
        database.update(GestionBDJoueur.NOM_TABLE_JOUEUR, values, GestionBDJoueur.JOUEUR_CLEF + " = ?", param);
    }

    public Cursor getAllJoueurs() {
        Cursor cursor = database.rawQuery(GestionBDJoueur.REQUETE_JOUEUR_ALL, null);
        return cursor;
    }

    public Cursor getAllHistorique() {
        Cursor cursor = database.rawQuery(GestionBDHistorique.REQUETE_HISTORIQUE_ALL, null);
        return cursor;
    }

    public ArrayList<Score> recuperer10(){
        Cursor curseur = database.rawQuery("Select * FROM " + GestionBDHistorique.NOM_TABLE_HISTORIQUE,null); /** + " JOIN "
                + GestionBDJoueur.NOM_TABLE_JOUEUR + " ON " +  GestionBDHistorique.NOM_TABLE_HISTORIQUE + "." + GestionBDHistorique.HISTORIQUE_JOUEUR + " = " + GestionBDJoueur.NOM_TABLE_JOUEUR + "." + GestionBDJoueur.JOUEUR_CLEF +
                " ORDER BY DESC " +  GestionBDHistorique.NOM_TABLE_HISTORIQUE + "." + GestionBDHistorique.HISTORIQUE_CLEF + " LIMIT 10;"*/
        ArrayList<Score> listeScore = new ArrayList<Score>();
        for(curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext()){
            Score score = new Score(curseur.getInt(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_CLEF)),
                    curseur.getString(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_DATE)),
                    curseur.getString(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_HEURE)),
                    curseur.getInt(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_NB_GAGNES)),
                    new Joueur(curseur.getInt(curseur.getColumnIndex(GestionBDJoueur.JOUEUR_CLEF)),
                    curseur.getString(curseur.getColumnIndex(GestionBDJoueur.JOUEUR_NOM))),
                    curseur.getInt(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_DIFFICULTE)));
            listeScore.add(score);
        }
        curseur.close();
        return listeScore;

    }

    public ArrayList<Score> recupererScore(String nom,int difficulte){
        Cursor curseur = database.rawQuery("Select * FROM " + GestionBDHistorique.NOM_TABLE_HISTORIQUE + " JOIN " + GestionBDJoueur.NOM_TABLE_JOUEUR + " ON " +
                                            GestionBDHistorique.HISTORIQUE_JOUEUR + " = " + GestionBDJoueur.JOUEUR_CLEF + " WHERE " + GestionBDJoueur.JOUEUR_NOM + " = ?;",
                                            new String[]{nom});
        ArrayList<Score> listeScore = new ArrayList<Score>();
        for(curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext()){
            Score score = new Score(curseur.getInt(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_CLEF)),
                    curseur.getString(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_DATE)),
                    curseur.getString(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_HEURE)),
                    curseur.getInt(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_NB_GAGNES)),
                    new Joueur(curseur.getInt(curseur.getColumnIndex(GestionBDJoueur.JOUEUR_CLEF)),
                    curseur.getString(curseur.getColumnIndex(GestionBDJoueur.JOUEUR_NOM))),
                    curseur.getInt(curseur.getColumnIndex(GestionBDHistorique.HISTORIQUE_DIFFICULTE)));
            listeScore.add(score);
        }
        curseur.close();
        return listeScore;
    }
}
