package com.example.valentin.apppendu.Activity;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.valentin.apppendu.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Aide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);


        TextView txtFileName=(TextView)findViewById(R.id.Aide);
        try{
            InputStream fichier = getResources().openRawResource(R.raw.aide);

            BufferedReader fichierTexte = new BufferedReader(new InputStreamReader(fichier));
            while((fichierTexte.readLine()) != null){
                txtFileName.setText(txtFileName.getText() + fichierTexte.readLine() +"\n");
            }
            fichier.close();
        } catch (FileNotFoundException e){
            Log.e("Zen","Le fichier n'existe pas");
        } catch (IOException e){
            Log.e("Zen", "Problème de lecture");
        }
    }

    public void vueAccueil(View view){
        Intent intent = new Intent(Aide.this, MainActivity.class);
        startActivity(intent);
    }


}
