package com.example.valentin.apppendu.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentin.apppendu.ClasseMetier.Categorie;
import com.example.valentin.apppendu.ClasseMetier.Mot;
import com.example.valentin.apppendu.DAO.MotDAO;
import com.example.valentin.apppendu.GestionBD.GestionBDMot;
import com.example.valentin.apppendu.R;

import static com.example.valentin.apppendu.Activity.Mots2Joueurs.sansAccent;

public class GestionMots extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    /** Bouton d'ajout d'un mot dans la catégorie */
    private FloatingActionButton floatingActionButton;

    /** Bouton de retour vers l'activité de gestion des catégories */
    private ImageButton buttonRetour;

    /** Titre de l'activité */
    private TextView textViewTitreGestionMot;

    /** DAO pour les mots */
    private MotDAO motDAO;

    /** Curseur utilisé pour la lecture de données dans la base */
    private Cursor curseur;

    /** Adaptateur servant d'intermédiare entre le curseru et la vue qui affiche les catgéories */
    private SimpleCursorAdapter adaptateur;

    /** Liste contenant les valeurs présentes dans la table catégorie */
    private ListView listeView;

    /** Identifiant de la catégorie sélectionnée */
    private int identifiantCategorie = -1;

    /** Nom de la catégorie sélectionnée */
    private String nomCategorie = "Undefined";

    private Mot motModif;

    private Mot motSupprimer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_mots);

        // Floating Action Button -> Ajouter un mot dans une catégorie
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabMots);
        floatingActionButton.setOnClickListener(this);

        motDAO = new MotDAO(this);
        motDAO.open();

        // Bouton de retour vers la gestion des activités
        buttonRetour = (ImageButton) findViewById(R.id.buttonRetourGestionCategories);
        buttonRetour.setOnClickListener(this);

        // Listview affichant tous les mots d'une catégorie
        listeView = (ListView) findViewById(R.id.listeView_mot);

        // Récupération de l'identifiant de la catégorie
        Intent intent = getIntent();
        identifiantCategorie = intent.getIntExtra(GestionCategories.ID_CATEGORIE, -1);
        nomCategorie = intent.getStringExtra(GestionCategories.NOM_CATEGORIE);

        // Titre de l'activité
        textViewTitreGestionMot = (TextView) findViewById(R.id.textViewTitreGestionMot);
        textViewTitreGestionMot.setText(nomCategorie);

        // Initialisation d'un curseur pour récupérer les données dans la table
        // curseur = base.rawQuery(GestionBDMot.REQUETE_MOT_CATEGORIE, param);
        curseur = motDAO.getMotsCategorie(identifiantCategorie);

        // Adaptateur qui permet de faire le lien entre la listView et le curseur
        adaptateur = new SimpleCursorAdapter(this,
                R.layout.ligne_liste_mots, curseur,
                new String[] {GestionBDMot.MOT_NOM},
                new int[] {R.id.nom_categorie}, 0);

        listeView.setAdapter(adaptateur);

        // Gestion du click sur un des items de la liste
        listeView.setOnItemClickListener(this);

        // Association du menu à la liste
        registerForContextMenu(listeView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabMots) {
            ajouterMot();
        } else if (view.getId() == R.id.buttonRetourGestionCategories) {
            // Arrêt de l'activité
            this.finish();
        }
    }

    public void refreshListe() {
        curseur = motDAO.getMotsCategorie(identifiantCategorie);
        adaptateur.swapCursor(curseur);
    }

    /*
       =================== C L I C  L I S T E =====================
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // TODO lancer alertdialog comme sur le context menu ?
        // TODO Fenetre récap du mot avec la difficulté affichée
    }

    /*
       =============== M E N U  C O N T E X T U E L ===============
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(this).inflate(R.menu.menu_contextuel_modifier_supprimer, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo information =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        if (item.getItemId() == R.id.menuContextModifier) {
            modifierMot(information.position);
        } else if (item.getItemId() == R.id.menuContextSupprimer) {
            supprimerMot(information.position);
        }

        return (super.onContextItemSelected(item));
    }

    public void modifierMot(final int position) {

        final View boiteDialog =
                getLayoutInflater().inflate(R.layout.modification_categorie_dialog, null);

        String nom = "";

        // Valeur sélectionnée
        if (curseur != null) {
            if (curseur.moveToPosition(position)) {
                motModif = new Mot(curseur.getInt(curseur.getColumnIndex(GestionBDMot.MOT_CLEF)),
                        curseur.getString(curseur.getColumnIndex(GestionBDMot.MOT_NOM)),
                        curseur.getInt(curseur.getColumnIndex(GestionBDMot.MOT_DIFFICULTE)),
                        new Categorie(identifiantCategorie, nomCategorie));
            }
        }

        // Alert Dialog
        AlertDialog.Builder dialogModif = new AlertDialog.Builder(this);

        // Titre custom du alertdialog
        TextView title = new TextView(this);
        title.setText(getResources().getString(R.string.titre_boite_dialog_modification_Mot));
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        dialogModif.setCustomTitle(title);

        dialogModif.setView(boiteDialog);

        EditText editTextDialog = (EditText) boiteDialog.findViewById(R.id.editTextDialog);
        editTextDialog.setText(motModif.getLibelle());

        dialogModif.setPositiveButton(getResources().getString(R.string.bouton_positif_Valider),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int leBouton) {
                        EditText modifSaisie = (EditText) boiteDialog.findViewById(R.id.editTextDialog);
                        String saisie = modifSaisie.getText().toString();   // Nom de la catégorie modifiée
                        // APPEL METHODE BD
                        motModif.setLibelle(saisie);
                        motDAO.updateMot(motModif);
                        refreshListe();
                        Toast.makeText(GestionMots.this, "Mot modifié", Toast.LENGTH_SHORT).show();
                    }
                });

        dialogModif.setNegativeButton(getResources()
                .getString(R.string.bouton_negatif_Annuler), null);
        dialogModif.show();
    }

    public void supprimerMot(final int position) {


        final View boiteDialog =
                getLayoutInflater().inflate(R.layout.suppression_categorie_dialog, null);

        String nom = "";
        int idCategorie = -1;

        // Valeur sélectionnée
        if (curseur != null) {
            if (curseur.moveToPosition(position)) {
                motSupprimer = new Mot(curseur.getInt(curseur.getColumnIndex(GestionBDMot.MOT_CLEF)),
                        curseur.getString(curseur.getColumnIndex(GestionBDMot.MOT_NOM)),
                        curseur.getInt(curseur.getColumnIndex(GestionBDMot.MOT_DIFFICULTE)),
                        new Categorie(identifiantCategorie, nomCategorie));
            }
        }

        // Alert Dialog
        AlertDialog.Builder dialogSuppression = new AlertDialog.Builder(this);

        // Titre custom du alert dialog
        TextView title = new TextView(this);
        title.setText(getResources().getString(R.string.titre_boite_dialog_suppression_Mot));
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        dialogSuppression.setCustomTitle(title);


        dialogSuppression.setView(boiteDialog);

        TextView textViewDialog = (TextView) boiteDialog.findViewById(R.id.textViewSuppressionDialog);
        textViewDialog.setText("Voulez vous vraiment supprimer \"" + motSupprimer.getLibelle() + "\" de la catégorie \""+ nomCategorie + "\" ?");

        dialogSuppression.setPositiveButton(getResources().getString(R.string.oui),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int leBouton) {
                        // APPEL METHODE BD (supprimer)
                        int nbLignesSupp = 0;
                        nbLignesSupp = motDAO.deleteMot(motSupprimer);

                        if (nbLignesSupp >= 1) {
                            Toast.makeText(GestionMots.this, "\"" + motSupprimer.getLibelle() + "\" supprimé", Toast.LENGTH_SHORT).show();
                            refreshListe();
                        } else {
                            Toast.makeText(GestionMots.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        dialogSuppression.setNegativeButton(getResources()
                .getString(R.string.non), null);
        dialogSuppression.show();
    }

    public void ajouterMot() {


        final View boiteDialog =
                getLayoutInflater().inflate(R.layout.ajout_mot_dialog, null);

        // Alert Dialog
        AlertDialog.Builder dialogAjout = new AlertDialog.Builder(this);

        // Titre custom du alertdialog
        TextView title = new TextView(this);
        title.setText(getResources().getString(R.string.titre_boite_dialog_ajout_Mot));
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        dialogAjout.setCustomTitle(title);

        dialogAjout.setView(boiteDialog);

        dialogAjout.setPositiveButton(getResources().getString(R.string.bouton_positif_Valider),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int leBouton) {
                        EditText ajout = (EditText) boiteDialog.findViewById(R.id.editTextAjoutCategorie);
                        String saisie = ajout.getText().toString();   // Nom de la catégorie ajoutée

                        if (!saisie.equals("")) {

                            String noAccent = sansAccent(saisie.trim());
                            if (noAccent.matches("^[a-zA-Z]+$")) {
                                // APPEL METHODE BD (ajout)
                                motDAO.createMot(saisie, identifiantCategorie);
                                refreshListe();
                                Toast.makeText(GestionMots.this, "\"" + saisie + "\" a été ajouté", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(GestionMots.this, "Le mot contient des caractères interdit", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(GestionMots.this, "Erreur lors de l'ajout du mot", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        dialogAjout.setNegativeButton(getResources()
                .getString(R.string.bouton_negatif_Annuler), null);
        dialogAjout.show();

    }
}
