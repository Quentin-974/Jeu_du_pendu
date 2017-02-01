package com.example.valentin.apppendu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

public class Classements extends AppCompatActivity {

    private TabHost lesOnglets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classements);

        lesOnglets = (TabHost) findViewById(R.id.tableOnglet);
        lesOnglets.setup();

        TabHost.TabSpec specification = lesOnglets.newTabSpec("onglet_1");
        specification.setIndicator(getResources().getString(R.string.label_onglet1));
        specification.setContent(R.id.tab1);
        lesOnglets.addTab(specification);

        lesOnglets.addTab(lesOnglets.newTabSpec("onglet_2")
                                    .setIndicator(getResources().getString(R.string.label_onglet2))
                                    .setContent(R.id.tab2));

        lesOnglets.addTab(lesOnglets.newTabSpec("onglet_3")
                                    .setIndicator(getResources().getString(R.string.label_onglet3))
                                    .setContent(R.id.tab3));

        lesOnglets.setOnTabChangedListener(
                new TabHost.OnTabChangeListener(){
                    @Override
                    public void onTabChanged(String tabId){

                    }
                }
        );
        lesOnglets.setCurrentTab(0);
    }


}
