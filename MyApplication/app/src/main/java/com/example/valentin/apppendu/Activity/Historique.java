package com.example.valentin.apppendu.Activity;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(getResources().getString(R.string.libelleButtonHistorique));

        setContentView(R.layout.activity_historique);
        daoHistorique = new HistoriqueDAO(this);
        joueurDAO = new JoueurDAO(this);
        daoHistorique.open();
        joueurDAO.open();
        historique = daoHistorique.recuperer10();

        if(historique.size() > 0 ){
            CustomListAdapter adapter = new CustomListAdapter(this,historique,true);
            listeHistorique = (ListView) findViewById(R.id.listeHistorique);
            listeHistorique.setAdapter(adapter);
        }
        daoHistorique.close();

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
