package com.example.valentin.apppendu.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.valentin.apppendu.R;

public class Mots2Joueurs extends Activity {

    /** Conteneur contenant les EditText */
    private LinearLayout conteneur;
    /** nom du joueur */
    private TextView nomJoueur;
    /** Bouton précédent */
    private ImageButton btnPrecedent;
    /** Bouton suivant */
    private ImageButton btnSuivant;
    /** Nombre de coup saisie*/
    private int nbCoup;
    /** Nom du joueur 1*/
    private String joueur1;
    /** Nom du joueur 2*/
    private String joueur2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mots2_joueurs);

        // Accès au différent widgets
        conteneur = (LinearLayout) findViewById(R.id.conteneur_ET);
        nomJoueur = (TextView) findViewById(R.id.tv_nomJoueur);
        btnPrecedent = (ImageButton) findViewById(R.id.imgPrecedentMots);
        btnSuivant = (ImageButton) findViewById(R.id.imgSuivantMots);

        // Récupération des valeurs par intent
        Bundle extras = getIntent().getExtras();
            if (extras != null ) {
                nbCoup = extras.getInt(Accueil2Joueurs.NBCOUPS);
                joueur1 = extras.getString(Accueil2Joueurs.NOM_JOUEUR1);
                joueur2 = extras.getString(Accueil2Joueurs.NOM_JOUEUR2);
            }

        // initialisation de la TextView
        nomJoueur.setText(joueur1);

        // Vider le conteneur au préalable
        conteneur.removeAllViews();
        // Création des editText dynamiquement
        for (int i = 0; i < nbCoup; i++) {
            EditText motSaisie = (EditText) getLayoutInflater().inflate(R.layout.editext,null);
            motSaisie.setHint("Mot " + (i+1));
            conteneur.addView(motSaisie);
        }
    }
}
