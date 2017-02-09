package com.example.valentin.apppendu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDCategorie;
import com.example.valentin.apppendu.GestionBD.GestionBDMot;
import com.example.valentin.apppendu.R;

public class GestionMots extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    /** Bouton d'ajout d'un mot dans la catégorie */
    private FloatingActionButton floatingActionButton;

    /** Bouton de retour vers l'activité de gestion des catégories */
    private ImageButton buttonRetour;

    /** Instance de la classe de gestion des mots dans la base de données */
    private GestionBD gestionBD;

    /** base de données crée */
    private SQLiteDatabase base;

    /** Curseur utilisé pour la lecture de données dans la base */
    private Cursor curseur;

    /** Adaptateur servant d'intermédiare entre le curseru et la vue qui affiche les catgéories */
    private SimpleCursorAdapter adaptateur;

    /** Liste contenant les valeurs présentes dans la table catégorie */
    private ListView listeView;

    /** Identifiant de la catégorie sélectionnée */
    private int identifiantCategorie = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_mots);

        // Floating Action Button -> Ajouter un mot dans une catégorie
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabCategories);
        floatingActionButton.setOnClickListener(this);

        // Bouton de retour vers la gestion des activités
        buttonRetour = (ImageButton) findViewById(R.id.buttonRetourGestionCategories);
        buttonRetour.setOnClickListener(this);

        // Listview affichant tous les mots d'une catégorie
        listeView = (ListView) findViewById(R.id.listeView_mot);

        // Gestionnaire de la table et création de la base de données si elle n'existe pas
        gestionBD = GestionBD.getInstance(this);

        // Récupération de la base de données
        base = gestionBD.getWritableDatabase();

        // Récupération de l'identifiant de la catégorie
        Intent intent = getIntent();
        identifiantCategorie = intent.getIntExtra(GestionCategories.ID_CATEGORIE, -1);
        String[] param = {String.valueOf(identifiantCategorie)};  // Tableau de paramètres pour la requête

        // Initialisation d'un curseur pour récupérer les données dans la table
        // curseur = base.rawQuery(GestionBDMot.REQUETE_MOT_CATEGORIE, param);
        curseur = base.rawQuery(GestionBDMot.REQUETE_MOT_ALL, null);

        // Adaptateur qui permet de faire le lien entre la listView et le curseur
        adaptateur = new SimpleCursorAdapter(this,
                R.layout.ligne_liste, curseur,
                new String[] {GestionBDMot.MOT_NOM,
                        GestionBDMot.MOT_DIFFICULTE},
                new int[] {R.id.id_categorie,
                        R.id.nom_categorie}, 0);

        listeView.setAdapter(adaptateur);

        // Gestion du click sur un des items de la liste
        listeView.setOnItemClickListener(this);

        // Association du menu à la liste
        unregisterForContextMenu(listeView);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
