package com.example.valentin.apppendu.Activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.valentin.apppendu.ClasseMetier.Joueur;
import com.example.valentin.apppendu.ClasseMetier.Score;
import com.example.valentin.apppendu.DAO.HistoriqueDAO;
import com.example.valentin.apppendu.DAO.JoueurDAO;
import com.example.valentin.apppendu.GestionBD.GestionBDJoueur;
import com.example.valentin.apppendu.R;

import java.util.ArrayList;

public class Historique extends AppCompatActivity {

    private ListView listeHistorique;
    private ArrayList<Score> historique = new ArrayList<Score>();
    private HistoriqueDAO daoHistorique;
    private JoueurDAO joueurDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        daoHistorique = new HistoriqueDAO(this);
        joueurDAO = new JoueurDAO(this);
        //historique.add(new Score(1,"01/03/2017","11h06",5,new Joueur(1,"Michel"), 1));
        //historique.add(new Score(2,"01/03/2017","11h00",9,new Joueur(2,"Robert"), 1));
        daoHistorique.open();
        joueurDAO.open();
        //historique = daoHistorique.recuperer10();
        Cursor cursor = daoHistorique.getAllHistorique();
        // Cursor cursor = joueurDAO.getAllJoueurs();
        cursor.moveToFirst();
        // Toast.makeText(this, "" + cursor.getString(cursor.getColumnIndex(GestionBDJoueur.JOUEUR_NOM)), Toast.LENGTH_SHORT).show();
        if(historique.size() > 0 ){
            CustomListAdapter adapter = new CustomListAdapter(this,historique);
            listeHistorique = (ListView) findViewById(R.id.listeHistorique);
            listeHistorique.setAdapter(adapter);
        }
        daoHistorique.close();

    }
}
