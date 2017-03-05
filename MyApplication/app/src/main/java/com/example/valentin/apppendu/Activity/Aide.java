package com.example.valentin.apppendu.Activity;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.valentin.apppendu.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Aide extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txtFileName=(TextView)findViewById(R.id.Aide);
        try{
            InputStream fichier = getResources().openRawResource(R.raw.aide);
            String ligne;

            BufferedReader fichierTexte = new BufferedReader(new InputStreamReader(fichier));
            while((ligne = fichierTexte.readLine()) != null){
                txtFileName.setText(txtFileName.getText() + ligne +"\n");
            }
            fichier.close();
        } catch (FileNotFoundException e){
            Log.e("Zen","Le fichier n'existe pas");
        } catch (IOException e){
            Log.e("Zen", "Problème de lecture");
        }
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
}
