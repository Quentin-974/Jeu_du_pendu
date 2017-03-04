package com.example.valentin.apppendu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.valentin.apppendu.DAO.CategorieDAO;
import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDCategorie;
import com.example.valentin.apppendu.R;

public class MainCategories extends AppCompatActivity {

    /** Identifiant pour le passage de donnée */
    public static final String CATEGORIE_PARTIE = "CATEGORIE";

    /** Identifiant pour le passage de donnée */
    public static final String JOUEUR_PARTIE = "JOUEUR";

    private int categorie;

    private String nomJoueur;

    private CategorieDAO categorieDAO;

    /** Curseur utilisé pour la lecture de données dans la base de données */
    private Cursor curseur;

    /** Adaptateur servant d'intermédiare entre le curseur et la vue qui affiche les catégories */
    private SimpleCursorAdapter adaptateur;

    /** Liste contenant les valeurs présentes dans la table catégorie */
    private ListView listeView;

    @Override
    protected void onResume() {
        categorieDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        categorieDAO.close();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categories);

        Bundle extras = getIntent().getExtras();
        // Si je joueur n'a pas de nom on lui affiche une boite de dialogue
        if(extras == null) {

            //On instancie notre layout en tant que View
            LayoutInflater factory = LayoutInflater.from(this);
            final View alertDialogView = factory.inflate(R.layout.saisie_nom_dialog, null);

            //Création de l'AlertDialog
            AlertDialog.Builder adb = new AlertDialog.Builder(this);

            //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
            adb.setView(alertDialogView);

            //On donne un titre à l'AlertDialog
            adb.setTitle(R.string.titreNom);


            //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
            adb.setPositiveButton(R.string.boutonPositif, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
                    EditText et = (EditText) alertDialogView.findViewById(R.id.EditText1);
                    Toast.makeText(MainCategories.this, et.getText(), Toast.LENGTH_SHORT);
                    nomJoueur = et.getText().toString();
                }

            });

            //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
            adb.setNegativeButton(R.string.boutonNegatif, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Lorsque l'on cliquera sur annuler on quittera l'application
                    Toast.makeText(MainCategories.this,R.string.messageSansNom,Toast.LENGTH_SHORT).show();
                    nomJoueur = null;
                }
            });
            adb.show();
        }
        listeView = (ListView) findViewById(R.id.listeView_categorie);

        categorieDAO = new CategorieDAO(this);
        categorieDAO.open();

        // Initialisation d'un curseur pour récupérer toutes les données de la table
        curseur = categorieDAO.getAllCategories();

        adaptateur = new SimpleCursorAdapter(this,
                R.layout.ligne_liste, curseur,
                new String[] {GestionBDCategorie.CATEGORIE_NOM},
                new int[] {R.id.nom_categorie}, 0);

        listeView.setAdapter(adaptateur);
        listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                curseur.moveToPosition(position);
                categorie = curseur.getInt(curseur.getColumnIndex(GestionBDCategorie.CATEGORIE_CLEF));
                Intent intent = new Intent(MainCategories.this, Difficultes.class);
                intent.putExtra(CATEGORIE_PARTIE, categorie);
                intent.putExtra(JOUEUR_PARTIE,nomJoueur);
                categorieDAO.close();
                startActivity(intent);
            }
        });

    }

    public void vueAccueil(View view){
        Intent intent = new Intent(MainCategories.this, MainActivity.class);
        startActivity(intent);
    }
}
