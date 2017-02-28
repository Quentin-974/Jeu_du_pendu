package com.example.valentin.apppendu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.valentin.apppendu.ClasseMetier.Categorie;
import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDCategorie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thibaut on 28/02/2017.
 */

public class CategorieDAO {

    // Champs de la base de données
    private SQLiteDatabase database;
    private GestionBD gestionBD;

    public CategorieDAO(Context contexte) {
        gestionBD = GestionBD.getInstance(contexte);
    }

    public void open() {
        database = gestionBD.getWritableDatabase();
    }

    public void close() {
        gestionBD.close();
    }

    public Categorie createCategorie(String nom) {
        ContentValues values = new ContentValues();
        values.put(GestionBDCategorie.CATEGORIE_NOM, nom);
        database.insert(GestionBDCategorie.NOM_TABLE_CATEGORIE,
                GestionBDCategorie.CATEGORIE_NOM, values);

        Cursor cursor = database.rawQuery(GestionBDCategorie.REQUETE_CATEGORIE_ALL, null);

        cursor.moveToFirst();
        Categorie categorieRetourne = cursorToCategorie(cursor);
        cursor.close();
        return categorieRetourne;
    }

    public int deleteCategorie(Categorie categorie) {
        int id = categorie.getId();

        int nbLignesSupp = database.delete(GestionBDCategorie.NOM_TABLE_CATEGORIE,
                GestionBDCategorie.CATEGORIE_CLEF+ " = ?",
                new String[] {String.valueOf(id)});

        return nbLignesSupp;
    }

    public void updateCategorie(Categorie categorie) {
        ContentValues enregistrement = new ContentValues();

        enregistrement.put(GestionBDCategorie.CATEGORIE_NOM, categorie.getLibelle());
        String[] param = {String.valueOf(categorie.getId())};
        database.update(GestionBDCategorie.NOM_TABLE_CATEGORIE, enregistrement, GestionBDCategorie.CATEGORIE_CLEF + " = ?", param);
    }

    public Cursor getAllCategories() {
        Cursor cursor = database.rawQuery(GestionBDCategorie.REQUETE_CATEGORIE_ALL, null);
        return cursor;
    }

    private Categorie cursorToCategorie(Cursor cursor) {
        Categorie categorie = new Categorie();
        categorie.setId(cursor.getInt(0));
        categorie.setLibelle(cursor.getString(1));
        return categorie;
    }
}
