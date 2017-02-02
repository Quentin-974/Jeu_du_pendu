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
    private int nbCoup = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mots2_joueurs);

        // Accès au différent widgets
        conteneur = (LinearLayout) findViewById(R.id.conteneur_ET);
        nomJoueur = (TextView) findViewById(R.id.tv_nomJoueur);
        btnPrecedent = (ImageButton) findViewById(R.id.imgPrecedentMots);
        btnSuivant = (ImageButton) findViewById(R.id.imgSuivantMots);

        conteneur.removeAllViews();
        for (int i = 0; i < nbCoup; i++) {
            EditText motSaisie = (EditText) getLayoutInflater().inflate(R.layout.editext,null);
            conteneur.addView(motSaisie);
        }
    }
}
