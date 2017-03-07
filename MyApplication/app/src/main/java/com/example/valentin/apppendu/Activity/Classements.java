package com.example.valentin.apppendu.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentin.apppendu.ClasseMetier.Categorie;
import com.example.valentin.apppendu.ClasseMetier.Score;
import com.example.valentin.apppendu.DAO.HistoriqueDAO;
import com.example.valentin.apppendu.DAO.JoueurDAO;
import com.example.valentin.apppendu.GestionBD.GestionBDCategorie;
import com.example.valentin.apppendu.R;

import java.util.ArrayList;
import java.util.List;

public class Classements extends AppCompatActivity {

    private TabHost lesOnglets;

    private ArrayList<Score> scores = new ArrayList<Score>();

    private ListView listeFacile;
    private ListView listeNormal;
    private ListView listeDifficile;

    private HistoriqueDAO daoHistorique;

    private JoueurDAO joueurDAO;

    private String nomJoueur;

    /** Bouton de suppression des scores du joueur*/
    private FloatingActionButton floatingActionButton;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Scores");

        setContentView(R.layout.activity_classements);
        daoHistorique = new HistoriqueDAO(this);
        daoHistorique.open();
        lesOnglets = (TabHost) findViewById(R.id.tableOnglet);
        lesOnglets.setup();

        joueurDAO = new JoueurDAO(this);
        joueurDAO.open();


        TabHost.TabSpec specification = lesOnglets.newTabSpec("Facile");
        specification.setIndicator(getResources().getString(R.string.label_onglet1));
        specification.setContent(R.id.tab1);
        lesOnglets.addTab(specification);

        lesOnglets.addTab(lesOnglets.newTabSpec("Normal")
                                    .setIndicator(getResources().getString(R.string.label_onglet2))
                                    .setContent(R.id.tab2));

        lesOnglets.addTab(lesOnglets.newTabSpec("Difficile")
                                    .setIndicator(getResources().getString(R.string.label_onglet3))
                                    .setContent(R.id.tab3));

        lesOnglets.setOnTabChangedListener(
                new TabHost.OnTabChangeListener(){
                    @Override
                    public void onTabChanged(String tabId){
                        //if(tabId.contains("Facile")){
                          //  Toast.makeText(Classements.this,"Facile",Toast.LENGTH_SHORT).show();
                        //} else if (tabId.contains("Normal")){
                          //  Toast.makeText(Classements.this,"Normal",Toast.LENGTH_SHORT).show();
                        //} else {
                         //   Toast.makeText(Classements.this,"Difficile",Toast.LENGTH_SHORT).show();
                        //}
                    }
                }
        );
        lesOnglets.setCurrentTab(0);

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.saisie_nom_dialog, null);

        //Création de l'AlertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        adb.setTitle(R.string.titreNom);


        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton(R.string.boutonPositif, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
                EditText et = (EditText) alertDialogView.findViewById(R.id.EditText1);
                nomJoueur = et.getText().toString();
                Toast.makeText(Classements.this, et.getText(), Toast.LENGTH_SHORT);
                scores = daoHistorique.recupererScore(et.getText().toString(),0);
                if(scores != null ){
                    CustomListAdapter adapter=new CustomListAdapter(Classements.this,scores,false);
                    listeFacile = (ListView) findViewById(R.id.ListeFacile);
                    listeFacile.setAdapter(adapter);
                }

                scores = daoHistorique.recupererScore(et.getText().toString(),1);
                if(scores != null ){
                    CustomListAdapter adapter=new CustomListAdapter(Classements.this,scores,false);
                    listeNormal = (ListView) findViewById(R.id.ListeNormal);
                    listeNormal.setAdapter(adapter);
                }

                scores = daoHistorique.recupererScore(et.getText().toString(),2);
                if(scores != null ){
                    CustomListAdapter adapter=new CustomListAdapter(Classements.this,scores,false);
                    listeDifficile = (ListView) findViewById(R.id.ListeDifficile);
                    listeDifficile.setAdapter(adapter);
                }
                daoHistorique.close();
            }

        });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        adb.setNegativeButton(R.string.boutonNegatif, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Lorsque l'on cliquera sur annuler
                // Toast.makeText(Classements.this,R.string.messageSansScore,Toast.LENGTH_SHORT).show();
                Classements.this.finish();
            }
        });
        adb.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void supprimerScores(View view) {

        final View boiteDialog =
                getLayoutInflater().inflate(R.layout.suppression_categorie_dialog, null);

        // Alert Dialog
        android.app.AlertDialog.Builder dialogSuppression = new android.app.AlertDialog.Builder(this);


        // Titre custom du alert dialog
        TextView title = new TextView(this);
        title.setText(getResources().getString(R.string.titre_boite_dialog_suppression_Scores));
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        dialogSuppression.setCustomTitle(title);


        dialogSuppression.setView(boiteDialog);

        TextView textViewDialog = (TextView) boiteDialog.findViewById(R.id.textViewSuppressionDialog);
        textViewDialog.setText("Voulez vous vraiment supprimer les scores de " + nomJoueur);

        dialogSuppression.setPositiveButton(getResources().getString(R.string.oui),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int leBouton) {
                        daoHistorique.open();
                        int nbLignesSupp = daoHistorique.deleteAllHistoriqueString(nomJoueur);
                        daoHistorique.close();

                        if (nbLignesSupp > 0) {
                            Toast.makeText(Classements.this, "Les scores de " + nomJoueur + " ont été supprimés avec succès", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Classements.this, "Aucun score supprimé", Toast.LENGTH_SHORT).show();
                        }
                        Classements.this.finish();
                    }
                });

        dialogSuppression.setNegativeButton(getResources()
                .getString(R.string.non), null);
        dialogSuppression.show();
    }
 }

