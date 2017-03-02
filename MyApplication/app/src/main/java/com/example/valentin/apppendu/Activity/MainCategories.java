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

import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDCategorie;
import com.example.valentin.apppendu.R;

public class MainCategories extends AppCompatActivity {

    /** Identifiant pour le passage de donnée */
    public static final String CATEGORIE_PARTIE = "CATEGORIE";

    /** Identifiant pour le passage de donnée */
    public static final String JOUEUR_PARTIE = "JOUEUR";

    private String categorie;

    private String nomJoueur;

    /** Instance de la classe de gestion des categories dans la base de données */
    private GestionBD gestionBD;

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
        setContentView(R.layout.activity_main_categories);

        Bundle extras = getIntent().getExtras();
        // Si je joueur n'a pas de nom on lui affiche une boite de dialogue
        if(extras== null) {


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

        // Gestionnaire de la table et création de la base de données si elle n'existe pas
        gestionBD = GestionBD.getInstance(this);

        // Récupération de la base de données
        base = gestionBD.getWritableDatabase();

        // Initialisation d'un curseur pour récupérer toutes les données de la table
        curseur = base.rawQuery(GestionBDCategorie.REQUETE_CATEGORIE_ALL, null);

        adaptateur = new SimpleCursorAdapter(this,
                R.layout.ligne_liste, curseur,
                new String[] {GestionBDCategorie.CATEGORIE_CLEF,
                        GestionBDCategorie.CATEGORIE_NOM},
                new int[] {R.id.id_categorie,
                        R.id.nom_categorie}, 0);

        listeView.setAdapter(adaptateur);
        listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                curseur.moveToPosition(position);
                categorie = curseur.getString(curseur.getColumnIndex("nom"));
                Toast.makeText(MainCategories.this,categorie.toString(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainCategories.this, Difficultes.class);
                intent.putExtra(CATEGORIE_PARTIE, categorie);
                intent.putExtra(JOUEUR_PARTIE,nomJoueur);
                startActivity(intent);
            }
        });

    }

    public void vueAccueil(View view){
        Intent intent = new Intent(MainCategories.this, MainActivity.class);
        startActivity(intent);
    }
}
