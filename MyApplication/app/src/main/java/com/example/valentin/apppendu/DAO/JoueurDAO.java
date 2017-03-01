package com.example.valentin.apppendu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.valentin.apppendu.ClasseMetier.Joueur;
import com.example.valentin.apppendu.ClasseMetier.Mot;
import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDHistorique;
import com.example.valentin.apppendu.GestionBD.GestionBDJoueur;
import com.example.valentin.apppendu.GestionBD.GestionBDMot;

/**
 * Created by thibaut on 01/03/2017.
 */

public class JoueurDAO {

    // Champs de la base de données
    private SQLiteDatabase database;
    private GestionBD gestionBD;

    public JoueurDAO(Context contexte) {
        gestionBD = GestionBD.getInstance(contexte);
    }

    public void open() {
        database = gestionBD.getWritableDatabase();
    }

    public void close() {
        gestionBD.close();
    }

    public long createJoueur(String nom) {
        ContentValues values = new ContentValues();

        values.put(GestionBDJoueur.JOUEUR_NOM, nom);
        long id = database.insert(GestionBDJoueur.NOM_TABLE_JOUEUR,
                GestionBDJoueur.JOUEUR_NOM, values);

        return id;
    }

    public int deleteJoueur(Joueur joueur) {
        int id = joueur.getId();

        // Suppression des historiques liés à ce joueur
        int nbLignesSuppH = database.delete(GestionBDHistorique.NOM_TABLE_HISTORIQUE,
                GestionBDHistorique.HISTORIQUE_JOUEUR + "= ?", new String[] {String.valueOf(id)});

        int nbLignesSupp = database.delete(GestionBDJoueur.NOM_TABLE_JOUEUR,
                GestionBDJoueur.JOUEUR_CLEF + " = ?",
                new String[] {String.valueOf(id)});

        return nbLignesSupp;
    }

    public void updateJoueur(Joueur joueur) {
        ContentValues enregistrement = new ContentValues();

        enregistrement.put(GestionBDJoueur.JOUEUR_NOM, joueur.getNom());
        String[] param = {String.valueOf(joueur.getId())};
        database.update(GestionBDJoueur.NOM_TABLE_JOUEUR, enregistrement, GestionBDJoueur.JOUEUR_CLEF + " = ?", param);
    }

    public Cursor getAllJoueurs() {
        Cursor cursor = database.rawQuery(GestionBDJoueur.REQUETE_JOUEUR_ALL, null);
        return cursor;
    }
}
