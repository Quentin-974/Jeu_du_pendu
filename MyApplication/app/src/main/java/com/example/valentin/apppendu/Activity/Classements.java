package com.example.valentin.apppendu.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.valentin.apppendu.R;

public class Classements extends AppCompatActivity {

    private TabHost lesOnglets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classements);

        lesOnglets = (TabHost) findViewById(R.id.tableOnglet);
        lesOnglets.setup();

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
                        if(tabId.contains("Facile")){
                            Toast.makeText(Classements.this,"Facile",Toast.LENGTH_SHORT).show();
                        } else if (tabId.contains("Normal")){
                            Toast.makeText(Classements.this,"Normal",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Classements.this,"Difficile",Toast.LENGTH_SHORT).show();
                        }
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
                Toast.makeText(Classements.this, et.getText(), Toast.LENGTH_SHORT);
            }

        });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        adb.setNegativeButton(R.string.boutonNegatif, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Lorsque l'on cliquera sur annuler
                Toast.makeText(Classements.this,R.string.messageSansScore,Toast.LENGTH_SHORT).show();
            }
        });
        adb.show();
    }


}