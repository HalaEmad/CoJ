package com.ir.android.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.activities.Activity;
import com.ir.android.R;

import org.w3c.dom.Text;


/**
 * Created by bassam on 09-07-2016.
 */
public class HomeScreen extends Activity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    private TextView mNameTextView;
    private TextView mDateTextView;
    private TextView mShiftTextView;
    private TextView mAreaTextView;
    private TextView mRegTextView;
    private TextView mOccTextView;
    private TextView mDriverTextView;

    private Button mOnDutyBtn;
    private Button mOnBreakBtn;

    @Override
    protected Controller createController() {
        return new HomeCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "ctrl.home";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    @Override
    protected void initViews() {

        mAreaTextView = (TextView) findViewById(R.id.area);
        mDateTextView = (TextView) findViewById(R.id.date);
        mDriverTextView = (TextView) findViewById(R.id.driver);
        mNameTextView = (TextView) findViewById(R.id.surname_text);

        mRegTextView = (TextView) findViewById(R.id.reg_label);
        mShiftTextView = (TextView) findViewById(R.id.shift);
        mOccTextView = (TextView) findViewById(R.id.occ_label);

        mOnBreakBtn = (Button) findViewById(R.id.home_onbreak_btn);
        mOnBreakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        mOnDutyBtn = (Button) findViewById(R.id.home_onduty_btn);
        mOnDutyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                R.string.home_open_navigation,  /* "open drawer" description */
//                R.string.home_close_navigation  /* "close drawer" description */
//        );
//
//        // Set the drawer toggle as the DrawerListener
//        mDrawerLayout.addDrawerListener(mDrawerToggle);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//
//        String[] titles = getResources().getStringArray(R.array.navigation_array);
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//        // Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_item, titles));
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Pass the event to ActionBarDrawerToggle, if it returns
//        // true, then it has handled the app icon touch event
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        // Handle your other action bar items...
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void bindViews(ViewModel viewModel) {
        // TODO replace static data with real one
        mAreaTextView.setText("Area: Melville");
        mDateTextView.setText("Date: 20/06/2016");
        mDriverTextView.setText("Driver: Yes");


        mRegTextView.setText("Reg: GVN63GP");
        mShiftTextView.setText("Shift: 07:00 - 19:00");
        mOccTextView.setText("Occ: 3");
    }
}
