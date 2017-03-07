package com.example.valentin.apppendu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.valentin.apppendu.ClasseMetier.Categorie;
import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDCategorie;
import com.example.valentin.apppendu.GestionBD.GestionBDMot;

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

    public long createCategorie(String nom) {
        ContentValues values = new ContentValues();
        values.put(GestionBDCategorie.CATEGORIE_NOM, nom);
        long id = database.insert(GestionBDCategorie.NOM_TABLE_CATEGORIE,
                GestionBDCategorie.CATEGORIE_NOM, values);

        return id;
    }

    public int deleteCategorie(Categorie categorie) {
        int id = categorie.getId();
        int nbLignesSupp = 0;

        // Test du nombre de catégories
        if (getAllCategories().getCount() > 3) {
            // Suppression des mots dans la catégorie
            int nbLignesSuppM = database.delete(GestionBDMot.NOM_TABLE_MOT,
                    GestionBDMot.MOT_CATEGORIE + "= ?", new String[] {String.valueOf(id)});

            // Suppression de la catégorie
            nbLignesSupp = database.delete(GestionBDCategorie.NOM_TABLE_CATEGORIE,
                    GestionBDCategorie.CATEGORIE_CLEF + " = ?",
                    new String[] {String.valueOf(id)});
        }

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

    public int recherchePresenceCategorie(String nom) {

        String[] param = {nom};
        Cursor cursor = database.rawQuery(GestionBDCategorie.REQUETE_CATEGORIE_NOM, param);

        return cursor.getCount();
    }

}
