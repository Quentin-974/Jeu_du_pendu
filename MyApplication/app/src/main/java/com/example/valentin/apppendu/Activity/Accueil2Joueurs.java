package com.example.valentin.apppendu.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.valentin.apppendu.R;

public class Accueil2Joueurs extends AppCompatActivity {

    /** Identifiant pour le passage du nom du joueur1 */
    public final static String NOM_JOUEUR1 = "JOUEUR1";
    /** Identifiant pour le passage du nom du joueur2 */
    public final static String NOM_JOUEUR2 = "JOUEUR2";
    /** Identifiant pour le passage du nombre de coup de la partie */
    public final static String NBCOUPS = "COUPS";
    /** Identifiant pour le compteur d'affichage de la page */
    public final static String COMPTEUR_PAGE = "COMPTEUR";

    /**  */
    private NumberPicker nbMots;
    /** EditText pour le joueur 1 */
    private EditText et_joueur1;
    /** EditText pour le joueur 2 */
    private EditText et_joueur2;

    /**Nombre de coup de la partie */
    private int nbCoup;
    /** Nom du joueur 1 */
    private String joueur1;
    /** Nom du joueur 2 */
    private String joueur2;
    /** Compteur d'affichage de page */
    private int compteur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil2_joueur);
        // Récupération des accès sur les différents widgets
        nbMots = (NumberPicker) findViewById(R.id.nombreMots);
        et_joueur1 = (EditText) findViewById(R.id.nomJoueur1);
        et_joueur2 = (EditText) findViewById(R.id.nomJoueur2);

        // Initialisation du numberPicker
        nbMots.setMinValue(1);
        nbMots.setMaxValue(100);
        nbMots.setWrapSelectorWheel(true);
    }

    public void pagePrecedente(View view){
        Intent intent = new Intent(Accueil2Joueurs.this, MainActivity.class);
        startActivity(intent);
    }

    public void pageSuivante(View view){
        // Récupération du nombre de coup
        nbCoup = nbMots.getValue();
        // Récupération des noms des joueur
        joueur1 = et_joueur1.getText().toString();
        joueur2 = et_joueur2.getText().toString();
        // Compteur initialisé à 1 pour l'affichage de la page 1 Joueur
        compteur = 1;
        // Passage des valeurs à la prochaine activité
        Intent intent = new Intent(Accueil2Joueurs.this, com.example.valentin.apppendu.Activity.Mots2Joueurs.class);
        intent.putExtra(NBCOUPS, nbCoup);
        intent.putExtra(NOM_JOUEUR1, joueur1);
        intent.putExtra(NOM_JOUEUR2, joueur2);
        intent.putExtra(COMPTEUR_PAGE,compteur);
        startActivity(intent);
    }
}
