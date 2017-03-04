package com.example.valentin.apppendu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentin.apppendu.ClasseMetier.Joueur;
import com.example.valentin.apppendu.DAO.HistoriqueDAO;
import com.example.valentin.apppendu.DAO.JoueurDAO;
import com.example.valentin.apppendu.DAO.MotDAO;
import com.example.valentin.apppendu.GestionBD.GestionBDMot;
import com.example.valentin.apppendu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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

    /** Le nombre d'erreur commises par l'utilisateur */
    private int nbErreurs = 0;

    /** ImageView qui affiche le pendu en fonction du nombre d'erreurs */
    private ImageView imagePendu;

    /** TextView du score en cours du joueur */
    private TextView tvScore;

    /** Nom du joueur actif */
    private String nomJoueur;

    /** Catégorie des mot */
    private int categorie;

    /** Difficulté des mot */
    private int difficulte;

    /** Mode de jeu 1 ou 2 joueurs */
    private boolean modeJeu;

    /** DAO */
    private MotDAO motDAO;

    /** Liste des mots à trouver pour un joueur */
    private ArrayList<String> listeMot1Joueur;

    /** Score du joueur en mode 1 joueur */
    private int score1Joueur = 0;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jeu);

        motDAO = new MotDAO(this);
        this.motDAO.open();
        initImageButton();
        TextViewMotAChercher = (TextView) findViewById(R.id.tvMot);
        imagePendu = (ImageView) findViewById(R.id.imageViewPendu);
        tvScore = (TextView) findViewById(R.id.tvScore);


        // Recupère les infos de l'activité précédente
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            modeJeu = extras.getBoolean(Difficultes.MODE_PARTIE);
            categorie = extras.getInt(Difficultes.CATEGORIE_PARTIE);
            difficulte = extras.getInt(Difficultes.DIFFICULTE_PARTIE);
            nomJoueur = extras.getString(Difficultes.JOUEUR_PARTIE);
            if (extras.getInt("score") > 0) {
                score1Joueur = extras.getInt("score");
            }
        }

        tvScore.setText("Score : " + String.valueOf(score1Joueur));

        listeMot1Joueur = new ArrayList<String>();

        if(!modeJeu) {

            if (extras.getStringArrayList("listeMotUtilises") != null) {
                listeMot1Joueur = extras.getStringArrayList("listeMotUtilises");
            } else {
                Cursor curseur = this.motDAO.getMotsCategorie(categorie, difficulte);
                for(curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext()) {
                    listeMot1Joueur.add(curseur.getString(curseur.getColumnIndex(GestionBDMot.MOT_NOM)));
                }
            }

            final int nombreMot = listeMot1Joueur.size();
            Random r = new Random();
            int tmp = r.nextInt(nombreMot);
            final String motAChercher = listeMot1Joueur.get(tmp);

            listeMot1Joueur.remove(tmp);

            Toast.makeText(this, motAChercher, Toast.LENGTH_LONG).show();
            String nbTirets = "";

            for (int i = 0 ; i<motAChercher.length() ; i++){
                nbTirets += "_";
            }
            TextViewMotAChercher.setText(nbTirets);

            for (final ImageButton ib : listImageButton) {
                ib.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                modeJeu1Joueur(v, motAChercher.toUpperCase());
                            }
                        }
                );
            }
        } else {
            // TODO mode 2 joueurs by QuentinMS
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

    public void modeJeu1Joueur(View v, String motAChercher) {
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
                            score1Joueur += 10 - nbErreurs;

                            finish();
                            Intent intent = new Intent(MainJeu.this, MainJeu.class);
                            intent.putExtra("score", score1Joueur);
                            intent.putExtra("listeMotUtilises",listeMot1Joueur);
                            intent.putExtra("DIFFICULTE", difficulte);
                            intent.putExtra("CATEGORIE",categorie);
                            intent.putExtra("JOUEUR",nomJoueur);
                            intent.putExtra("MODE", modeJeu);
                            startActivity(intent);
                        }
                    }
            } else {
                nbErreurs++;
                String nomImage = "pendu" + nbErreurs;
                int resID = getResources().getIdentifier(nomImage, "drawable", getPackageName());
                imagePendu.setImageResource(resID);
            }
        } else {
            HistoriqueDAO historiqueDAO = new HistoriqueDAO(this);
            historiqueDAO.open();
            JoueurDAO joueurDAO = new JoueurDAO(this);
            joueurDAO.open();

            long idJoueur = joueurDAO.createJoueur(nomJoueur);
            Joueur joueur = new Joueur((int) idJoueur, nomJoueur);

            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR);
            int min = c.get(Calendar.MINUTE);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int date = c.get(Calendar.DATE);

            // TODO la date - Tester si le joueur n'a saisie son nom
            Date currentDate = new Date(year, month+1, date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            String currentData = sdf.format(currentDate);

            historiqueDAO.createHistorique(currentData, hour+1 + "h" + min, score1Joueur, joueur, difficulte);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainJeu.this);
            builder.setTitle("Vous avez perdu")
                    .setMessage("Votre score est de :" + score1Joueur + " puntos.")
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

    @Override
    protected void onResume() {
        motDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        motDAO.close();
        super.onPause();
    }
}
