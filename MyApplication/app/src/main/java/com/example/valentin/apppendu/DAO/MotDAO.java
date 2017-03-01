package com.example.valentin.apppendu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.valentin.apppendu.Activity.GestionMots;
import com.example.valentin.apppendu.ClasseMetier.Categorie;
import com.example.valentin.apppendu.ClasseMetier.Mot;
import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDCategorie;
import com.example.valentin.apppendu.GestionBD.GestionBDMot;

/**
 * Created by thibaut on 28/02/2017.
 */

public class MotDAO {

    // Champs de la base de données
    private SQLiteDatabase database;
    private GestionBD gestionBD;

    public MotDAO(Context contexte) {
        gestionBD = GestionBD.getInstance(contexte);
    }

    public void open() {
        database = gestionBD.getWritableDatabase();
    }

    public void close() {
        gestionBD.close();
    }

    public long createMot(String nom, int idCategorie) {
        ContentValues values = new ContentValues();

        values.put(GestionBDMot.MOT_NOM, nom);
        values.put(GestionBDMot.MOT_DIFFICULTE, Mot.difficultes(nom));
        values.put(GestionBDMot.MOT_CATEGORIE, idCategorie);
        long id = database.insert(GestionBDMot.NOM_TABLE_MOT,
                GestionBDMot.MOT_NOM, values);

        return id;
    }

    public int deleteMot(Mot mot) {
        int id = mot.getId();

        int nbLignesSupp = database.delete(GestionBDMot.NOM_TABLE_MOT,
                GestionBDMot.MOT_CLEF + " = ?",
                new String[] {String.valueOf(id)});

        return nbLignesSupp;
    }

    public void updateMot(Mot mot) {
        ContentValues enregistrement = new ContentValues();

        enregistrement.put(GestionBDMot.MOT_NOM, mot.getLibelle());
        String[] param = {String.valueOf(mot.getId())};
        database.update(GestionBDMot.NOM_TABLE_MOT, enregistrement, GestionBDMot.MOT_CLEF + " = ?", param);
    }

    public Cursor getAllMots() {
        Cursor cursor = database.rawQuery(GestionBDMot.REQUETE_MOT_ALL, null);
        return cursor;
    }

    public Cursor getMotsCategorie(int idCategorie) {
        String[] param = {String.valueOf(idCategorie)};
        Cursor cursor = database.rawQuery(GestionBDMot.REQUETE_MOTS_CATEGORIE, param);

        return cursor;
    }

}
