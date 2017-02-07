package com.example.valentin.apppendu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.valentin.apppendu.R;

public class MainJeu extends AppCompatActivity {

    private ImageButton ibQuitter;

    //Image Button des lettres
    private ImageButton ibA;
    private ImageButton ibB;
    private ImageButton ibC;
    private ImageButton ibD;
    private ImageButton ibE;
    private ImageButton ibF;
    private ImageButton ibG;
    private ImageButton ibH;
    private ImageButton ibI;
    private ImageButton ibJ;
    private ImageButton ibK;
    private ImageButton ibL;
    private ImageButton ibM;
    private ImageButton ibN;
    private ImageButton ibO;
    private ImageButton ibP;
    private ImageButton ibQ;
    private ImageButton ibR;
    private ImageButton ibS;
    private ImageButton ibT;
    private ImageButton ibU;
    private ImageButton ibV;
    private ImageButton ibW;
    private ImageButton ibX;
    private ImageButton ibY;
    private ImageButton ibZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jeu);
        /* lol*/
        ibQuitter = (ImageButton) findViewById(R.id.imageButtonQuitter);

        ibQuitter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainJeu.this);
                        builder.setTitle("QuitterS")
                                .setMessage("Voulez-vous quitter la partie?")
                                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intention = new Intent(MainJeu.this, MainActivity.class);
                                        startActivity(intention);
                                    }
                                })
                                .setNegativeButton("Non",null);
                    }
                }

        );
    }


}
