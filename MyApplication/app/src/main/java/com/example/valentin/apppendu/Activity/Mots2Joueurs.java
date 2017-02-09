package com.example.valentin.apppendu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentin.apppendu.R;

import java.util.ArrayList;

public class Mots2Joueurs extends Activity {

    /** Identifiant pour le passage de la liste de mot du joueur1 */
    public final static String LISTE_J1 = "MOTS_J1";
    /** Identifiant pour le passage de la liste de mot du joueur1 */
    public final static String LISTE_J2 = "MOTS_J2";

    /** Conteneur contenant les EditText */
    private LinearLayout conteneur;
    /** nom du joueur */
    private TextView nomJoueur;
    /** Bouton précédent */
    private ImageButton btnPrecedent;
    /** Bouton suivant */
    private ImageButton btnSuivant;
    /**ET à creer dynamiquement */
    private EditText motSaisie;
    /** Tableau d'editText*/
    private EditText[] tab_mots = new EditText[20];

    /** Nombre de coup saisie*/
    private int nbCoup;
    /** Nom du joueur 1*/
    private String joueur1;
    /** Nom du joueur 2*/
    private String joueur2;
    /** compteur d'afficahge de la page */
    private int compteur;
    /** Liste des mots saisie par le joueur 1 */
    private ArrayList<String> listeJ1;
    /** Liste des mots saisie par le joueur 1 */
    private ArrayList<String> listeJ2;

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
            compteur = extras.getInt(Accueil2Joueurs.COMPTEUR_PAGE);
        }

        //Affichage de la page en fonction du joueur
        if (compteur == 1 ) {
            nomJoueur.setText(joueur1);
        } else {
            initJ2();
        }

        // Vider le conteneur au préalable
        conteneur.removeAllViews();
        // Création des editText dynamiquement
        for (int i = 0; i < nbCoup; i++) {
            tab_mots[i] = new EditText(this);
            tab_mots[i] = (EditText) getLayoutInflater().inflate(R.layout.editext,null);
            tab_mots[i].setHint("Mot " + (i+1));
            conteneur.addView(tab_mots[i]);
        }

    }

    /** Récupération des valeurs et affichage de la page de saisie
     * pour le joueur 1
     */
    public void initJ2() {
        // Récupération des valeurs par intent
        Bundle extras = getIntent().getExtras();
        if (extras != null ) {
            nbCoup = extras.getInt(Accueil2Joueurs.NBCOUPS);
            joueur1 = extras.getString(Accueil2Joueurs.NOM_JOUEUR1);
            joueur2 = extras.getString(Accueil2Joueurs.NOM_JOUEUR2);
            compteur = extras.getInt(Accueil2Joueurs.COMPTEUR_PAGE);
            listeJ1 = extras.getStringArrayList(LISTE_J1);
        }
        // Initialisation de la textView servant de titre
        nomJoueur.setText(joueur2);
    }



    /**
     * Opération qui va s'effectuer lors du clic dur l'ImageButton
     * suivant
     * @param bouton ImageButton suivant
     */
    public void clicSuivantMots(View bouton) {
        // Page de saisie du joueur 1
        if (compteur == 1) {
            listeJ1 = new ArrayList<String>();
            // Stockage des différents mots saisie dans une liste
            for (int i = 0; i < nbCoup; i++) {
                if (!(tab_mots[i].getText().toString().equals(""))) {
                    listeJ1.add(tab_mots[i].getText().toString().trim());
                    //Toast.makeText(this, listeJ1.get(i), Toast.LENGTH_SHORT).show();
                }
            }
            // Incrementation du compteur pour avoir l'affichage de la page pour le joueur 2
            compteur++;
            Intent intent = new Intent(Mots2Joueurs.this, Mots2Joueurs.class);
            intent.putExtra(Accueil2Joueurs.NOM_JOUEUR1, joueur1);
            intent.putExtra(Accueil2Joueurs.NOM_JOUEUR2, joueur2);
            intent.putExtra(Accueil2Joueurs.COMPTEUR_PAGE, compteur);
            intent.putExtra(Accueil2Joueurs.NBCOUPS, nbCoup);
            intent.putStringArrayListExtra(LISTE_J1, listeJ1);
            startActivity(intent);

        } else {
            // Page de saisie du joueur 2
            listeJ2 = new ArrayList<String>();
            // Stockage des différents mots saisie dans une liste
            for (int i = 0; i < nbCoup; i++) {
                if (!(tab_mots[i].getText().toString().equals(""))) {
                    listeJ2.add(tab_mots[i].getText().toString().trim());
                }
            }
            Intent intent = new Intent(Mots2Joueurs.this, MainActivity.class);
            intent.putExtra(Accueil2Joueurs.NOM_JOUEUR1, joueur1);
            intent.putExtra(Accueil2Joueurs.NOM_JOUEUR2, joueur2);
            intent.putStringArrayListExtra(LISTE_J1, listeJ1);
            intent.putStringArrayListExtra(LISTE_J2, listeJ2);
            startActivity(intent);
        }

    }

}
