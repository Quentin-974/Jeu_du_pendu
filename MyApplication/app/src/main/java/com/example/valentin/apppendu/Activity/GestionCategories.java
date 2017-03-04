package com.example.valentin.apppendu.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.valentin.apppendu.DAO.CategorieDAO;
import com.example.valentin.apppendu.GestionBD.GestionBD;
import com.example.valentin.apppendu.GestionBD.GestionBDCategorie;
import com.example.valentin.apppendu.R;

public class GestionCategories extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    /** Boouton d'ajout d'une catégorie */
    private FloatingActionButton floatingActionButton;

    /** Bouton de retour vers le menu */
    private ImageButton buttonRetour;

    /** DAO pour les catégories */
    private CategorieDAO categorieDAO;

    /** Curseur utilisé pour la lecture de données dans la base de données */
    private Cursor curseur;

    /** Adaptateur servant d'intermédiare entre le curseur et la vue qui affiche les catégories */
    private SimpleCursorAdapter adaptateur;

    /** Liste contenant les valeurs présentes dans la table catégorie */
    private ListView listeView;

    private Categorie categorieModif;

    private Categorie categorieSupprimer;

    /** Extra */
    public final static String ID_CATEGORIE = "id_categorie" ;
    public final static String NOM_CATEGORIE = "nom_categorie" ;

    @Override
    protected void onResume() {
        categorieDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        categorieDAO.close();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_categories);

        // Floating Action Button -> Ajouter une catégorie
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabCategories);
        floatingActionButton.setOnClickListener(GestionCategories.this);

        categorieDAO = new CategorieDAO(this);
        categorieDAO.open();

        // Bouton de retour vers le menu principal
        buttonRetour = (ImageButton) findViewById(R.id.buttonRetourGestionCategoriesMenu);
        buttonRetour.setOnClickListener(this);

        // Listview affichant toutes les catégories
        listeView = (ListView) findViewById(R.id.listeView_categorie);

        // Initialisation d'un curseur pour récupérer toutes les données de la table
        curseur = categorieDAO.getAllCategories();

        // Adapatateur qui permet de faire le lien entre la listeView et le curseur
        adaptateur = new SimpleCursorAdapter(this,
                                                R.layout.ligne_liste, curseur,
                                                new String[] {GestionBDCategorie.CATEGORIE_NOM},
                                                new int[] {R.id.nom_categorie}, 0);

        listeView.setAdapter(adaptateur);

        // Gestion du click sur un des items de la liste
        listeView.setOnItemClickListener(this);

        // Association du menu à la liste
        registerForContextMenu(listeView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabCategories) {
            ajouterCategorie();
        } else if (view.getId() == R.id.buttonRetourGestionCategoriesMenu) {
            // Arrêt de l'activité
            this.finish();
        }
    }

    public void refreshListe() {
        curseur = categorieDAO.getAllCategories();
        adaptateur.swapCursor(curseur);
    }

    /*
       =================== C L I C  L I S T E =====================
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int identifiant = curseur.getInt(curseur.getColumnIndex(GestionBDCategorie.CATEGORIE_CLEF));
        String nomCategorie = curseur.getString(curseur.getColumnIndex(GestionBDCategorie.CATEGORIE_NOM));

        // Lancement de l'activité de gestion des mots d'une catégorie
        Intent intent = new Intent(GestionCategories.this, GestionMots.class);
        intent.putExtra(ID_CATEGORIE, identifiant);
        intent.putExtra(NOM_CATEGORIE, nomCategorie);
        startActivity(intent);

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
            modifierCategorieDialog(information.position);

        } else if (item.getItemId() == R.id.menuContextSupprimer) {
            supprimerCategorie(information.position);
        }

        return (super.onContextItemSelected(item));
    }


    private void modifierCategorieDialog(final int position) {
        final View boiteDialog =
                        getLayoutInflater().inflate(R.layout.modification_categorie_dialog, null);

        // Valeur sélectionnée
        if (curseur != null) {
            if (curseur.moveToPosition(position)) {
                categorieModif = new Categorie(
                        curseur.getInt(curseur.getColumnIndex(GestionBDCategorie.CATEGORIE_CLEF)),
                        curseur.getString(curseur.getColumnIndex(GestionBDCategorie.CATEGORIE_NOM)));
            }
        }

        // Alert Dialog
        AlertDialog.Builder dialogModif = new AlertDialog.Builder(this);

        // Titre custom du alertdialog
        TextView title = new TextView(this);
        title.setText(getResources().getString(R.string.titre_boite_dialog_modification));
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        dialogModif.setCustomTitle(title);

        dialogModif.setView(boiteDialog);

        EditText editTextDialog = (EditText) boiteDialog.findViewById(R.id.editTextDialog);
        editTextDialog.setText(categorieModif.getLibelle());

        dialogModif.setPositiveButton(getResources().getString(R.string.bouton_positif_Valider),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int leBouton) {
                        EditText modifSaisie = (EditText) boiteDialog.findViewById(R.id.editTextDialog);
                        String saisie = modifSaisie.getText().toString();   // Nom de la catégorie modifiée
                        // APPEL METHODE BD (modifier)
                        categorieModif.setLibelle(saisie);
                        categorieDAO.updateCategorie(categorieModif);
                        refreshListe();
                        Toast.makeText(GestionCategories.this, "Catégorie modifiée", Toast.LENGTH_SHORT).show();
                    }
                });

        dialogModif.setNegativeButton(getResources()
                          .getString(R.string.bouton_negatif_Annuler), null);
        dialogModif.show();
    }

    private void supprimerCategorie(final int position) {
        final View boiteDialog =
                getLayoutInflater().inflate(R.layout.suppression_categorie_dialog, null);

        String nom = "";
        int idCategorie = -1;

        // Valeur sélectionnée
        if (curseur != null) {
            if (curseur.moveToPosition(position)) {
                categorieSupprimer = new Categorie(
                        curseur.getInt(curseur.getColumnIndex(GestionBDCategorie.CATEGORIE_CLEF)),
                        curseur.getString(curseur.getColumnIndex(GestionBDCategorie.CATEGORIE_NOM)));
            }
        }

        // Alert Dialog
        AlertDialog.Builder dialogSuppression = new AlertDialog.Builder(this);


        // Titre custom du alert dialog
        TextView title = new TextView(this);
        title.setText(getResources().getString(R.string.titre_boite_dialog_suppression));
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        dialogSuppression.setCustomTitle(title);


        dialogSuppression.setView(boiteDialog);

        TextView textViewDialog = (TextView) boiteDialog.findViewById(R.id.textViewSuppressionDialog);
        textViewDialog.setText("Voulez vous vraiment supprimer " + categorieSupprimer.getLibelle() + " des catégories ?");

        dialogSuppression.setPositiveButton(getResources().getString(R.string.oui),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int leBouton) {
                        // APPEL METHODE BD (supprimer)
                        int nbLignesSupp = 0;
                        // TODO Suppression en cascade
                        nbLignesSupp = categorieDAO.deleteCategorie(categorieSupprimer);

                        if (nbLignesSupp >= 1) {
                            Toast.makeText(GestionCategories.this, "\"" + categorieSupprimer.getLibelle() + "\" supprimée", Toast.LENGTH_SHORT).show();
                            refreshListe();
                        } else {
                            Toast.makeText(GestionCategories.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        dialogSuppression.setNegativeButton(getResources()
                .getString(R.string.non), null);
        dialogSuppression.show();
    }

    private void ajouterCategorie() {
        final View boiteDialog =
                getLayoutInflater().inflate(R.layout.ajout_categorie_dialog, null);

        // Alert Dialog
        AlertDialog.Builder dialogAjout = new AlertDialog.Builder(this);

        // Titre custom du alertdialog
        TextView title = new TextView(this);
        title.setText(getResources().getString(R.string.titre_boite_dialog_ajout));
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
                            // APPEL METHODE BD (ajout)
                            long id = categorieDAO.createCategorie(saisie);
                            refreshListe();
                            Toast.makeText(GestionCategories.this, "\"" + saisie + "\" a été ajoutée", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GestionCategories.this, "Erreur lors de l'ajout de la catégorie", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        dialogAjout.setNegativeButton(getResources()
                .getString(R.string.bouton_negatif_Annuler), null);
        dialogAjout.show();
    }

    public Categorie getCategorieModif() {
        return categorieModif;
    }

    public Categorie getCategorieSupprimer() {
        return categorieSupprimer;
    }
}
