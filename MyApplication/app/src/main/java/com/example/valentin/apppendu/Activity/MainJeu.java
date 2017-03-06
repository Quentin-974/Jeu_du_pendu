package com.example.valentin.apppendu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private ArrayList<Button> listButton;

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
            String motTmp = "";

            if (nombreMot != 0) {
                Random r = new Random();
                int tmp = r.nextInt(nombreMot);
                motTmp = listeMot1Joueur.get(tmp);
                listeMot1Joueur.remove(tmp);
            } else {
                finPartie1Joueur();
            }

            final String motAChercher = motTmp;

            String nbTirets = "";

            for (int i = 0 ; i<motAChercher.length() ; i++){
                nbTirets += "_";
            }
            TextViewMotAChercher.setText(nbTirets);

            for (final Button ib : listButton) {
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
            for (final Button ib : listButton) {
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
        listButton = new ArrayList<>();

        listButton.add((Button) findViewById(R.id.ibA));
        listButton.add((Button) findViewById(R.id.ibB));
        listButton.add((Button) findViewById(R.id.ibC));
        listButton.add((Button) findViewById(R.id.ibD));
        listButton.add((Button) findViewById(R.id.ibE));
        listButton.add((Button) findViewById(R.id.ibF));
        listButton.add((Button) findViewById(R.id.ibG));
        listButton.add((Button) findViewById(R.id.ibH));
        listButton.add((Button) findViewById(R.id.ibI));
        listButton.add((Button) findViewById(R.id.ibJ));
        listButton.add((Button) findViewById(R.id.ibK));
        listButton.add((Button) findViewById(R.id.ibL));
        listButton.add((Button) findViewById(R.id.ibM));
        listButton.add((Button) findViewById(R.id.ibN));
        listButton.add((Button) findViewById(R.id.ibO));
        listButton.add((Button) findViewById(R.id.ibP));
        listButton.add((Button) findViewById(R.id.ibQ));
        listButton.add((Button) findViewById(R.id.ibR));
        listButton.add((Button) findViewById(R.id.ibS));
        listButton.add((Button) findViewById(R.id.ibT));
        listButton.add((Button) findViewById(R.id.ibU));
        listButton.add((Button) findViewById(R.id.ibV));
        listButton.add((Button) findViewById(R.id.ibW));
        listButton.add((Button) findViewById(R.id.ibX));
        listButton.add((Button) findViewById(R.id.ibY));
        listButton.add((Button) findViewById(R.id.ibZ));
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
                            Toast.makeText(MainJeu.this, "Manche gagnée ("+ nbErreurs + " erreur(s))", Toast.LENGTH_SHORT).show();
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
            // Si le joueur n'as pas entrer son nom
            if (nomJoueur == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainJeu.this);
                builder.setTitle("Vous avez perdu")
                        .setMessage("Votre score est de :" + score1Joueur + " puntos.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainJeu.this, "Vous n'avez pas entrer votre nom, votre score n'as donc pas été pris en compte.", Toast.LENGTH_SHORT).show();
                                Intent intention = new Intent(MainJeu.this, MainActivity.class);
                                startActivity(intention);
                            }
                        });
                builder.show();
            } else {
                finPartie1Joueur();
            }
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
     * Méthode appellée àa la fin de la partie en mode 1 joueur
     * Elle aafiche une boite de dialogue récapitulant le score du joueur.
     */
    public void finPartie1Joueur() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainJeu.this);
        builder.setTitle("Vous avez perdu")
                .setMessage("Votre score est de :" + score1Joueur + " puntos.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HistoriqueDAO historiqueDAO = new HistoriqueDAO(MainJeu.this);
                        historiqueDAO.open();
                        JoueurDAO joueurDAO = new JoueurDAO(MainJeu.this);
                        joueurDAO.open();

                        long idJoueur = joueurDAO.createJoueur(nomJoueur);
                        Joueur joueur = new Joueur((int) idJoueur, nomJoueur);

                        Calendar c = Calendar.getInstance();
                        int hour = c.get(Calendar.HOUR);
                        int min = c.get(Calendar.MINUTE);
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int date = c.get(Calendar.DATE);

                        String currrentDate = date + "/" + month + "/" + year;

                        historiqueDAO.createHistorique(currrentDate, hour+1 + "h" + min, score1Joueur, joueur, difficulte);
                        Intent intention = new Intent(MainJeu.this, MainActivity.class);
                        startActivity(intention);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainJeu.this, "Votre score n'a pas été enregitré.", Toast.LENGTH_SHORT).show();
                        Intent intention = new Intent(MainJeu.this, MainActivity.class);
                        startActivity(intention);
                    }
                })
        ;
        builder.show();
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

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("score", score1Joueur);
        savedInstanceState.putStringArrayList("listeMot", listeMot1Joueur);
        savedInstanceState.putString("nomJoueur", nomJoueur);
        savedInstanceState.putInt("nbErreurs", nbErreurs);
        savedInstanceState.putInt("difficulte", difficulte);
        savedInstanceState.putInt("categorie", categorie);
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.score1Joueur = savedInstanceState.getInt("score");
        this.listeMot1Joueur = savedInstanceState.getStringArrayList("listeMot");
        this.nomJoueur = savedInstanceState.getString("nomJoueur");
        this.nbErreurs = savedInstanceState.getInt("nbErreurs");
        this.difficulte = savedInstanceState.getInt("difficulte");
        this.categorie = savedInstanceState.getInt("categorie");
    }
}
