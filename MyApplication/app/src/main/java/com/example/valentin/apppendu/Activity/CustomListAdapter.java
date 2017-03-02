package com.example.valentin.apppendu.Activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.valentin.apppendu.ClasseMetier.Score;
import com.example.valentin.apppendu.R;

import java.util.ArrayList;

/**
 * Created by syldu on 01/03/2017.
 */

public class CustomListAdapter extends ArrayAdapter<Score> {

    private final Activity context;
    private final ArrayList<Score> itemname;

    public CustomListAdapter(Activity context, ArrayList<Score> itemname) {
        super(context, R.layout.liste_historique, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.liste_historique, null,true);

        TextView txtJoueur = (TextView) rowView.findViewById(R.id.nomJoueur);
        txtJoueur.setText(itemname.get(position).getJoueur().getNom());

        TextView txtDate = (TextView) rowView.findViewById(R.id.datePartie);
        txtDate.setText(itemname.get(position).getDate() + " " + itemname.get(position).getHeure());

        TextView txtScore = (TextView) rowView.findViewById(R.id.scoreJoueur);
        txtScore.setText(String.valueOf(itemname.get(position).getNbMotsTrouve()));

        return rowView;

    }
}