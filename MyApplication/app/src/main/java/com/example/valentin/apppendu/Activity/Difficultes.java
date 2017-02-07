package com.example.valentin.apppendu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import com.example.valentin.apppendu.R;

public class Difficultes extends Activity {

    /** Bouton pour la difficulté facile */
    private ImageButton btnFacile;
    /** Bouton pour la difficulté Moyen */
    private ImageButton btnMoyen;
    /** Bouton pour la difficulté difficile */
    private ImageButton btnDifficile;
    /** Bouton pour le retour à la page précédente */
    private ImageButton btnPrecedent;

    /** Identifiant pour le passage de donnée */
    private final String DIFFICULTE_PARTIE = "DIFFICULTE";

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
        Intent intent = new Intent(Difficultes.this,GestionCategories.class);
        startActivity(intent);
    }

    /**
     * Passage à la fenêtre suivante lorsque l'on clic sur un niveau de difficulté
     * @param bouton Bouton sur lequel va cliqué l'utilisateur
     */
    public void clicDifficulte(View bouton) {
        // Clique sur l'imageButton facile
        if(bouton.getId() == R.id.imgFacile) {
            difficulte = 1;
            Intent intent = new Intent(Difficultes.this, MainActivity.class);
            intent.putExtra(DIFFICULTE_PARTIE, difficulte);
            startActivity(intent);
        // Clique sur l'imageButton moyen
        } else if(bouton.getId() == R.id.imgMoyen) {
            difficulte = 2;
            Intent intent = new Intent(Difficultes.this, MainActivity.class);
            intent.putExtra(DIFFICULTE_PARTIE, difficulte);
            startActivity(intent);
            // Clique sur l'imageButton difficile
        } else {
            difficulte = 3;
            Intent intent = new Intent(Difficultes.this, MainActivity.class);
            intent.putExtra(DIFFICULTE_PARTIE, difficulte);
            startActivity(intent);
        }
    }

}
