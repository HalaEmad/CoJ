package com.ir.android.tabbar;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.ir.android.R;
import com.ir.android.map.IncidentActivity;

public class TabBarActivity extends TabActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbar);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, IncidentActivity.class);
        spec = tabHost.newTabSpec("IncidentActivity").setIndicator("IncidentActivity")
                .setContent(intent);
        tabHost.addTab(spec);

//        intent = new Intent().setClass(this, IncidentActivity.class);
//        spec = tabHost.newTabSpec("IncidentActivity").setIndicator("IncidentActivity")
//                .setContent(intent);
//        tabHost.addTab(spec);
//
//        intent = new Intent().setClass(this, IncidentActivity.class);
//        spec = tabHost.newTabSpec("Third").setIndicator("Third")
//                .setContent(intent);
//        tabHost.addTab(spec);

    }
}