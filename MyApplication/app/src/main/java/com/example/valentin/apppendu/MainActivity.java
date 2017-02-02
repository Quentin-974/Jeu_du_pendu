package com.example.valentin.apppendu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void page2Joueur(View view){
        Intent intent = new Intent(MainActivity.this, Accueil2Joueurs.class);
        startActivity(intent);
    }

    public void parametres(View view){
        Intent intent = new Intent(MainActivity.this, GestionCategories.class);
        startActivity(intent);
    }
}
