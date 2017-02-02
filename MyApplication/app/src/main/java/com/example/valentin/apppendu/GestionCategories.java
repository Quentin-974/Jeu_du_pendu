package com.example.valentin.apppendu;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.widget.ListView;

public class GestionCategories extends Activity {

    /** Numéro de version de la base de données */
    private static final int VERSION = 3;

    /** Instance de la classe de gestion des categories dans la base de données */
    private GestionBDCategorie gestionBDCategorie;

    /** Nom de la base de données dans laquelle seront conservé les catégories */
    private static final String NOM_BD = "pendu.db";

    /** Base de données crée */
    private SQLiteDatabase base;

    /** Curseur utilisé pour la lecture de données dans la base de données */
    private Cursor curseur;

    /** Adaptateur servant d'intermédiare entre le curseur et la vue qui affiche les catégories */
    private SimpleCursorAdapter adaptateur;

    /** Liste contenant les valeurs présentes dans la table catégorie */
    private ListView listeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_categories);

        listeView = (ListView) findViewById(R.id.listeView_categorie);

        // Gestionnaire de la table et création de la base de données si elle n'existe pas
        gestionBDCategorie = new GestionBDCategorie(this, NOM_BD, null, VERSION);

        // Récupération de la base de données -> indique que l'on veut seulement la lire
        base = gestionBDCategorie.getReadableDatabase();

        // Initialisation d'un curseur pour récupérer toutes les données de la table
        curseur = base.rawQuery(GestionBDCategorie.REQUETE_CATEGORIE_ALL, null);

        adaptateur = new SimpleCursorAdapter(this,
                                                R.layout.ligne_liste, curseur,
                                                new String[] {GestionBDCategorie.CATEGORIE_CLEF,
                                                              GestionBDCategorie.CATEGORIE_NOM},
                                                new int[] {R.id.id_categorie,
                                                           R.id.nom_categorie}, 0);

        listeView.setAdapter(adaptateur);


    }
}
