package com.example.valentin.apppendu.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.valentin.apppendu.R;

public class Classements extends AppCompatActivity {

    private TabHost lesOnglets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classements);

        lesOnglets = (TabHost) findViewById(R.id.tableOnglet);
        lesOnglets.setup();

        TabHost.TabSpec specification = lesOnglets.newTabSpec("Facile");
        specification.setIndicator(getResources().getString(R.string.label_onglet1));
        specification.setContent(R.id.tab1);
        lesOnglets.addTab(specification);

        lesOnglets.addTab(lesOnglets.newTabSpec("Normal")
                                    .setIndicator(getResources().getString(R.string.label_onglet2))
                                    .setContent(R.id.tab2));

        lesOnglets.addTab(lesOnglets.newTabSpec("Difficile")
                                    .setIndicator(getResources().getString(R.string.label_onglet3))
                                    .setContent(R.id.tab3));

        lesOnglets.setOnTabChangedListener(
                new TabHost.OnTabChangeListener(){
                    @Override
                    public void onTabChanged(String tabId){
                        if(tabId.contains("Facile")){
                            Toast.makeText(Classements.this,"Facile",Toast.LENGTH_SHORT).show();
                        } else if (tabId.contains("Normal")){
                            Toast.makeText(Classements.this,"Normal",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Classements.this,"Difficile",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        lesOnglets.setCurrentTab(0);
    }


}
