package com.example.valentin.apppendu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.valentin.apppendu.R;

public class MainActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (ImageView) findViewById(R.id.imageViewPendu);

        imageView.setBackgroundResource(R.drawable.anim);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getBackground();
        // Start the animation (looped playback by default).
        frameAnimation.start();


    }

    public void choixCategorie(View view){
        Intent intent = new Intent(MainActivity.this, MainCategories.class);
        startActivity(intent);
    }

    public void page2Joueur(View view){
        Intent intent = new Intent(MainActivity.this, Accueil2Joueurs.class);
        startActivity(intent);
    }

    public void parametres(View view){
        Intent intent = new Intent(MainActivity.this, GestionCategories.class);
        startActivity(intent);
    }

    public void pageAide(View view) {
        Intent intent = new Intent(MainActivity.this, Aide.class);
        startActivity(intent);
    }

    public void voirScore(View view){
        Intent intent = new Intent(MainActivity.this, Classements.class);
        startActivity(intent);
    }

    public void historique(View view){
        Intent intent = new Intent(MainActivity.this,Historique.class);
        startActivity(intent);
    }
}
