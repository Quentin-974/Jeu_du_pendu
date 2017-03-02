package com.example.valentin.apppendu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentin.apppendu.ClasseMetier.Categorie;
import com.example.valentin.apppendu.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Activité du tableau de jeu
 */
public class MainJeu extends AppCompatActivity {
    /** Image button du bouton quitter*/
    private ImageButton ibQuitter;
    /** TextView qui affiche les lettres du mot à cherhcher */
    private TextView TextViewMotAChercher;
    /** Liste contenant les images button des lettres */
    private ArrayList<ImageButton> listImageButton;
    /** Le mot à chercher par l'utilisateur */
    private String motAChercher = "CONSOLE";
    /** Le nombre d'erreur commises par l'utilisateur */
    private int nbErreurs = 0;
    /** ImageView qui affiche le pendu en fonction du nombre d'erreurs */
    private ImageView imagePendu;
    /** Nom du joueur actif */
    private String nomJoueur;
    /** Catégorie des mot */
    private int categorie;
    /** Difficulté des mot */
    private int difficulte;
    /** Mode de jeu 1 ou 2 joueurs */
    private boolean modeJeu;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jeu);

        /*
            TO DO :
             - Récupérer les valeurs pour la catégorie du mot
             - Générer une liste de mot aléatoire avec les valeurs récupérées
             - Séparer les execution en fonction d'un boolean qui détermine si 1 ou 2 joueurs
         */

        initImageButton();
        TextViewMotAChercher = (TextView) findViewById(R.id.tvMot);
        imagePendu = (ImageView) findViewById(R.id.imageViewPendu);
        String nbTirets = "";

        // Recupère les infos de l'activité précédente
        Intent intent = getIntent();

        modeJeu = intent.getBooleanExtra("modePartie", false);
        categorie = intent.getIntExtra("categorie", 0);
        difficulte = intent.getIntExtra("difficulte", 1);
        nomJoueur = intent.getStringExtra("joueur");

        Toast.makeText(this, "mode de jeu : " + String.valueOf(modeJeu), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "catgegorie : " + categorie, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "difficulte : " + String.valueOf(difficulte), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "nom du joueur : " + nomJoueur, Toast.LENGTH_SHORT).show();


        // set le mode jeu
        modeJeu = false;

        for (int i = 0 ; i<motAChercher.length() ; i++){
            nbTirets += "_";
        }
        TextViewMotAChercher.setText(nbTirets);

        if(!modeJeu) {
            for (final ImageButton ib : listImageButton) {
                ib.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                modeJeu1Joueur(v);
                            }
                        }
                );
            }
        } else {
            // mode 2 joueur
        }



        ibQuitter = (ImageButton) findViewById(R.id.imageButtonQuitter);

        ibQuitter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainJeu.this);
                        builder.setTitle("Quitter ?")
                                .setMessage("Voulez-vous quitter la partie?")
                                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intention = new Intent(MainJeu.this, MainActivity.class);
                                        startActivity(intention);
                                    }
                                })
                                .setNegativeButton("Non", null);
                        builder.show();
                    }
                }
        );
    }

    private void initImageButton() {
        listImageButton = new ArrayList<>();

        listImageButton.add((ImageButton) findViewById(R.id.ibA));
        listImageButton.add((ImageButton) findViewById(R.id.ibB));
        listImageButton.add((ImageButton) findViewById(R.id.ibC));
        listImageButton.add((ImageButton) findViewById(R.id.ibD));
        listImageButton.add((ImageButton) findViewById(R.id.ibE));
        listImageButton.add((ImageButton) findViewById(R.id.ibF));
        listImageButton.add((ImageButton) findViewById(R.id.ibG));
        listImageButton.add((ImageButton) findViewById(R.id.ibH));
        listImageButton.add((ImageButton) findViewById(R.id.ibI));
        listImageButton.add((ImageButton) findViewById(R.id.ibJ));
        listImageButton.add((ImageButton) findViewById(R.id.ibK));
        listImageButton.add((ImageButton) findViewById(R.id.ibL));
        listImageButton.add((ImageButton) findViewById(R.id.ibM));
        listImageButton.add((ImageButton) findViewById(R.id.ibN));
        listImageButton.add((ImageButton) findViewById(R.id.ibO));
        listImageButton.add((ImageButton) findViewById(R.id.ibP));
        listImageButton.add((ImageButton) findViewById(R.id.ibQ));
        listImageButton.add((ImageButton) findViewById(R.id.ibR));
        listImageButton.add((ImageButton) findViewById(R.id.ibS));
        listImageButton.add((ImageButton) findViewById(R.id.ibT));
        listImageButton.add((ImageButton) findViewById(R.id.ibU));
        listImageButton.add((ImageButton) findViewById(R.id.ibV));
        listImageButton.add((ImageButton) findViewById(R.id.ibW));
        listImageButton.add((ImageButton) findViewById(R.id.ibX));
        listImageButton.add((ImageButton) findViewById(R.id.ibY));
        listImageButton.add((ImageButton) findViewById(R.id.ibZ));
    }

    public void modeJeu1Joueur(View v) {
        String id = String.valueOf(getResources().getResourceEntryName(v.getId()));
        char lettre = id.charAt(2);
        if (nbErreurs < 10) {
            if (motAChercher.indexOf(lettre) != -1) {
                for (int i = 0; i < motAChercher.length(); i++)
                    if (motAChercher.charAt(i) == lettre) {
                        StringBuilder tmp = new StringBuilder(TextViewMotAChercher.getText());
                        tmp.setCharAt(i, lettre);
                        TextViewMotAChercher.setText(tmp);
                        if (motAChercher.equals(TextViewMotAChercher.getText())) {
                            Toast.makeText(MainJeu.this, "T'as gagné en "+ nbErreurs + " erreurs.", Toast.LENGTH_SHORT).show();
                            // TO DO appel de l'activité avec le nouveau mot
                        }
                    }
            } else {
                nbErreurs++;
                String nomImage = "pendu" + nbErreurs;
                int resID = getResources().getIdentifier(nomImage, "drawable", getPackageName());
                imagePendu.setImageResource(resID);
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainJeu.this);
            builder.setTitle("T'as fait trop d'erreurs")
                    .setMessage("T'es pas bon")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intention = new Intent(MainJeu.this, MainActivity.class);
                            startActivity(intention);
                        }
                    });
            builder.show();
        }
    }
}
