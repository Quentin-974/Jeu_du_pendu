package com.example.valentin.apppendu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.valentin.apppendu.R;

public class Difficultes extends Activity {

    /** Identifiant pour le passage de donnée */
    public static final String CATEGORIE_PARTIE = "CATEGORIE";

    /** Identifiant pour le passage de donnée */
    public static final String JOUEUR_PARTIE = "JOUEUR";

    /** Identifiant pour le passage de donnée */
    public static final String MODE_PARTIE = "MODE";

    /** Bouton pour la difficulté facile */
    private ImageButton btnFacile;
    /** Bouton pour la difficulté Moyen */
    private ImageButton btnMoyen;
    /** Bouton pour la difficulté difficile */
    private ImageButton btnDifficile;
    /** Bouton pour le retour à la page précédente */
    private ImageButton btnPrecedent;

    /** Identifiant pour le passage de donnée */
    public static final String DIFFICULTE_PARTIE = "DIFFICULTE";

    /** Categorie des mots  */
    private String categorie;

    /** Nom du joueur de la partie */
    private String joueur;


    /** Si false mode jeu à 1 joueur*/
    public final static boolean modePartie = false;


    /** Difficulté de la partie
     * 1 -> Facile
     * 2 -> Moyen
     * 3 -> Difficile
     */
    private int difficulte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficultes);

        // Récupération des accès sur les widgets
        btnFacile = (ImageButton) findViewById(R.id.imgFacile);
        btnMoyen = (ImageButton) findViewById(R.id.imgMoyen);
        btnDifficile = (ImageButton) findViewById(R.id.imgDifficile);
        btnPrecedent = (ImageButton) findViewById(R.id.imgPrecedent);
    }

    /**
     * Passage à la fenêtre précedente lorsque l'on est sur la page Difficulté
     * @param bouton Bouton sur lequel va cliqué l'utilisateur
     */
    public void clicPrecedent(View bouton) {
        Intent intent = new Intent(Difficultes.this,MainCategories.class);
        intent.putExtra(JOUEUR_PARTIE,joueur);
        startActivity(intent);
    }

    /**
     * Passage à la fenêtre suivante lorsque l'on clic sur un niveau de difficulté
     * @param bouton Bouton sur lequel va cliqué l'utilisateur
     */
    public void clicDifficulte(View bouton) {

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            categorie = extras.getInt(MainCategories.CATEGORIE_PARTIE);
            joueur = extras.getString(MainCategories.JOUEUR_PARTIE);
        }
        // Clique sur l'imageButton facile
        if(bouton.getId() == R.id.imgFacile) {
            difficulte = 1;
            Intent intent = new Intent(Difficultes.this, MainJeu.class);
            intent.putExtra(DIFFICULTE_PARTIE, difficulte);
            intent.putExtra(CATEGORIE_PARTIE,categorie);
            intent.putExtra(JOUEUR_PARTIE,joueur);
            intent.putExtra(MODE_PARTIE,modePartie);
            startActivity(intent);
        // Clique sur l'imageButton moyen
        } else if(bouton.getId() == R.id.imgMoyen) {
            difficulte = 2;
            Intent intent = new Intent(Difficultes.this, MainJeu.class);
            intent.putExtra(DIFFICULTE_PARTIE, difficulte);
            intent.putExtra(CATEGORIE_PARTIE,categorie);
            intent.putExtra(JOUEUR_PARTIE,joueur);
            intent.putExtra(MODE_PARTIE,modePartie);
            startActivity(intent);
            // Clique sur l'imageButton difficile
        } else {
            difficulte = 3;
            Intent intent = new Intent(Difficultes.this, MainJeu.class);
            intent.putExtra(DIFFICULTE_PARTIE, difficulte);
            intent.putExtra(CATEGORIE_PARTIE,categorie);
            intent.putExtra(JOUEUR_PARTIE,joueur);
            intent.putExtra(MODE_PARTIE,modePartie);
            startActivity(intent);
        }
    }


}
