package com.example.valentin.apppendu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

    /** Nom du second joueur */
    private String joueur2;

    /** Liste de mot du joueur 2 */
    private ArrayList<String> listeMot2Joueurs;

    /**Utiliser pour l'affichage des mots du mode 2 joueur*/
    private  int compteur;

    /** Score du joueur 2 */
    private int scoreJ2 = 0;

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
        ibQuitter = (ImageButton) findViewById(R.id.imageButtonQuitter);

        // Recupère les infos de l'activité précédente
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            modeJeu = extras.getBoolean(Difficultes.MODE_PARTIE);
            if (modeJeu) {
                // info mode 2 joueur
                nomJoueur = extras.getString(Accueil2Joueurs.NOM_JOUEUR1);
                joueur2 = extras.getString(Accueil2Joueurs.NOM_JOUEUR2);
                listeMot1Joueur = extras.getStringArrayList(Mots2Joueurs.LISTE_J1);
                listeMot2Joueurs = extras.getStringArrayList(Mots2Joueurs.LISTE_J2);
                if (extras.getInt("compteur") > 0) {
                    compteur = extras.getInt("compteur");
                }
                if (extras.getInt("scoreJ1") > 0) {
                    score1Joueur = extras.getInt("scoreJ1");
                }
                if (extras.getInt("scoreJ2") > 0) {
                    scoreJ2 = extras.getInt("scoreJ2");
                }
            } else {
                // info mode 1 joueur
                categorie = extras.getInt(Difficultes.CATEGORIE_PARTIE);
                difficulte = extras.getInt(Difficultes.DIFFICULTE_PARTIE);
                nomJoueur = extras.getString(Difficultes.JOUEUR_PARTIE);
                if (extras.getInt("score") > 0) {
                    score1Joueur = extras.getInt("score");
                }
            }
        }

        tvScore.setText("Score : " + String.valueOf(score1Joueur));

        if(!modeJeu) {
            listeMot1Joueur = new ArrayList<String>();
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
            // Mode 2 joueurs
            // Affichage du nom du joueur actif et de son score
            if (compteur % 2 == 0) {
                // Joueur 1
                tvScore.setText(nomJoueur + " " + String.valueOf(score1Joueur) + "pts\n");
            } else {
                // Joueur 2
                tvScore.setText(joueur2 + " " + String.valueOf(scoreJ2) + "pts\n");
            }

            String nbTirets = "";
            // Définition du mot à chercher en fonction de la parité du compteur
            final String motAChercher = initMot(listeMot1Joueur,listeMot2Joueurs,compteur);

            // La partie est terminer
            if (motAChercher.equals("")) {
                finPartie2Joueur(score1Joueur, scoreJ2, nomJoueur, joueur2);
            }

            for (int i = 0 ; i<motAChercher.length() ; i++){
                nbTirets += "_";
            }
            // Affichage des "_" suivant la taille du mot
            TextViewMotAChercher.setText(nbTirets);

            // Mise en place de l'évènement lors du clique sur une des lettres du clavier
            for (final ImageButton ib : listImageButton) {
                ib.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                modeJeu2Joueurs(v, motAChercher.toUpperCase());
                            }
                        }
                );
            }
        }

        // Définition de l'écouteur sur l'imageButton pour quiter le jeu
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

    /**
     * Traitement pour le mode 2 joueurs
     * @param v            ImageButton sur lequel a cliqué l'utilisateur
     * @param motAChercher le mot que devra trouvé le joueur
     */
    public void modeJeu2Joueurs(View v, String motAChercher) {
        // Récupération de la lettre sur lauelle a cliqué le joueur
        String id = String.valueOf(getResources().getResourceEntryName(v.getId()));
        System.out.println(id);
        char lettre = id.charAt(2);
        // Vérification qu'il n'a pas déja perdu
        if (nbErreurs < 10) {
            // si la lettre est présente dans le mot
            if (motAChercher.indexOf(lettre) != -1) {
                // On va chercher l'indice ou les indices auxquelles se trouvent la lettre dans le mot
                for (int i = 0; i < motAChercher.length(); i++)
                    if (motAChercher.charAt(i) == lettre) {
                        // On complète le "_" avec la ou les lettres correspondantes
                        StringBuilder tmp = new StringBuilder(TextViewMotAChercher.getText());
                        tmp.setCharAt(i, lettre);
                        TextViewMotAChercher.setText(tmp);
                        // Le joueur a réussi à trouver le mot
                        if (motAChercher.equals(TextViewMotAChercher.getText())) {
                            // Incrementation du score en fonction du tor du joueur
                            if (compteur % 2 == 0) {
                                score1Joueur += 1;
                            } else {
                                scoreJ2 += 1;
                            }

                            // Pour que l'autre joueur puisse joué au prochain tour
                            compteur++;

                            Intent intent = new Intent(MainJeu.this, MainJeu.class);
                            intent.putExtra("scoreJ1", score1Joueur);
                            intent.putExtra("scoreJ2", scoreJ2);
                            intent.putExtra(Mots2Joueurs.LISTE_J1, listeMot1Joueur);
                            intent.putExtra(Mots2Joueurs.LISTE_J2, listeMot2Joueurs);
                            intent.putExtra(Accueil2Joueurs.NOM_JOUEUR1, nomJoueur);
                            intent.putExtra(Accueil2Joueurs.NOM_JOUEUR2, joueur2);
                            intent.putExtra(Difficultes.MODE_PARTIE, modeJeu);
                            intent.putExtra("compteur", compteur);
                            startActivity(intent);
                        }
                    }
            } else {
                // clique sur la mauvaise lettre modification de l'image du pendu
                nbErreurs++;
                String nomImage = "pendu" + nbErreurs;
                int resID = getResources().getIdentifier(nomImage, "drawable", getPackageName());
                imagePendu.setImageResource(resID);
            }
        } else {
            // Le joueur n'a pas trouvé le mot
            // on passe donc au tour de l'autre joueur
            compteur++;
            Intent intent = new Intent(MainJeu.this, MainJeu.class);
            intent.putExtra("scoreJ1", score1Joueur);
            intent.putExtra("scoreJ2", scoreJ2);
            intent.putExtra(Mots2Joueurs.LISTE_J1, listeMot1Joueur);
            intent.putExtra(Mots2Joueurs.LISTE_J2, listeMot2Joueurs);
            intent.putExtra(Accueil2Joueurs.NOM_JOUEUR1, nomJoueur);
            intent.putExtra(Accueil2Joueurs.NOM_JOUEUR2, joueur2);
            intent.putExtra(Difficultes.MODE_PARTIE, modeJeu);
            intent.putExtra("compteur", compteur);
            startActivity(intent);
        }
    }

    /**
     * Définition du mot à afficher en fonction du joueur
     * Compteur paire -> joueur 1 doit trouvé le mot
     * compteur impaire c'est le joueur 2
     * @param listeJ1 Liste des mots que devra trouvé le joueur 1
     * @param listeJ2 Liste des mots que devra touvé le joueur 2
     * @return
     */
    private String initMot(ArrayList<String> listeJ1, ArrayList<String> listeJ2, int compteur) {
        String mot= "";
        // Le joueur 1 va joué on choisit donc un mot saisie par le joueur 2
        if (compteur % 2 == 0) {
            if(listeJ1.size() > 0) {
                mot = listeJ2.get(0);
                listeJ2.remove(0);
            }
            // Le joueur 2 va joué on choisit donc un mot saisie par le joueur 1
        }else {
            if(listeJ1.size() > 0) {
                mot = listeJ1.get(0);
                listeJ1.remove(0);
            }
        }
        return mot;
    }

    /**
     *  Affichage de la boite de dialogue de fin de jeu avec le score des deux joueurs ainsi
     *  que le résultat de la partie : un gagnant (J1 ou J2) ou une égalité
     * @param scoreJ1 score du joueur1
     * @param scoreJ2 score du joueur2
     * @param j1      nom du joueur 1
     * @param j2      nom du joueur 2
     */
    public void finPartie2Joueur(int scoreJ1, int scoreJ2, String j1, String j2) {
        // Résultat de la partie
        String resultat = "";

        //Définiton du résultat de la partie
        if (scoreJ1 > scoreJ2) {
            // Victoire du joueur 1
            resultat = "Félicitation " + j1 + " !\nVous avez gagné la partie.";
        } else if (scoreJ1 < scoreJ2) {
            // Victoire du joueur 2
            resultat = "Félicitation " + j2 + " !\nVous avez gagné la partie.";
        } else {
            // Résultat en cas d'égalité
            resultat = "C'est une égalité.";
        }
        // Création de la boite de dialogue d'affichage
        AlertDialog.Builder builder = new AlertDialog.Builder(MainJeu.this);
        builder.setTitle("Fin de la partie")
                .setMessage(j1 + " a " + scoreJ1 + " point(s).\n"
                        + j2 + " a " + scoreJ2 + " point(s).\n"
                        + resultat)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intention = new Intent(MainJeu.this, MainActivity.class);
                        startActivity(intention);
                    }
                });
        builder.show();
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
