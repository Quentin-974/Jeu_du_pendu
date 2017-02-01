package com.example.valentin.apppendu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

public class Accueil2Joueur extends AppCompatActivity {

    private NumberPicker nbMots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil2_joueur);
        nbMots = (NumberPicker) findViewById(R.id.nombreMots);
        nbMots.setMinValue(1);
        nbMots.setMaxValue(100);
        nbMots.setWrapSelectorWheel(true);
    }
}
