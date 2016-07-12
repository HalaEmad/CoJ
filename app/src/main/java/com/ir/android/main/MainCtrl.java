package com.ir.android.main;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ir.android.NavigationHelper;
import com.ir.android.R;
import com.ir.android.active_call.ActiveCallScreen;
import com.ir.android.home.HomeScreen;
import com.ir.android.incidents.IncidentScreen;
import com.ir.android.walkthrough.WalkthroughScreen;

/**
 * Created by bassam on 12-07-2016.
 */
public class MainCtrl extends Controller implements MainViewListener {

    @Override
    public ViewModel initViewModel() {
        return new MainViewModel();
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);

        selectFragment(0);
    }

    @Override
    public void selectFragment(int position) {

        Fragment fragment = null;
        String title = null;
        String tag = null;
        switch (position) {
            case 0:
                tag = "screen.home";
                fragment = getScreen().getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null)
                    fragment = new HomeScreen();
                title = getScreen().getString(R.string.home_title);
                break;
            case 1:
                tag = "screen.incident";
                fragment = getScreen().getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null)
                    fragment = new IncidentScreen();
                title = getScreen().getString(R.string.incident_title);
                break;
            case 2:
                tag = "screen.active.call";
                fragment = getScreen().getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null)
                    fragment = new ActiveCallScreen();
                title = getScreen().getString(R.string.active_call_title);
                break;
            case 3:
                tag = "screen.walkthrough";
                fragment = getScreen().getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null)
                    fragment = new WalkthroughScreen();
                title = getScreen().getString(R.string.walkthrough_title);
                break;
            case 4:
                finish();
                NavigationHelper.showLogin(getContext());
                return;

        }

        if (fragment != null && title != null) {
            ((MainViewModel) viewModel).setFragment(fragment);
            ((MainViewModel) viewModel).setTitle(title);
            ((MainViewModel) viewModel).setTag(tag);
            ((MainViewModel) viewModel).setSelected(position);
        }

    }
}
