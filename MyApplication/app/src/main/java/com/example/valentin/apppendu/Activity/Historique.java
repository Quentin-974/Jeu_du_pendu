package com.example.valentin.apppendu.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.valentin.apppendu.ClasseMetier.Joueur;
import com.example.valentin.apppendu.ClasseMetier.Score;
import com.example.valentin.apppendu.R;

import java.util.ArrayList;

public class Historique extends AppCompatActivity {

    private ListView listeHistorique;
    private ArrayList<Score> historique = new ArrayList<Score>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        historique.add(new Score(1,"01/03/2017","11h06",5,new Joueur(1,"Le Noir")));
        historique.add(new Score(2,"01/03/2017","11h00",9,new Joueur(2,"BAT")));
        CustomListAdapter adapter=new CustomListAdapter(this,historique);
        listeHistorique = (ListView) findViewById(R.id.listeHistorique);
        listeHistorique.setAdapter(adapter);
    }
}
