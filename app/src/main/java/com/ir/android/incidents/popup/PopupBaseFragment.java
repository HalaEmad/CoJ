package com.ir.android.incidents.popup;

import android.view.View;
import android.view.ViewGroup;

import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;

/**
 * Created by bassam on 29-07-2016.
 */
public abstract class PopupBaseFragment extends Fragment {

    private ViewGroup popupContainer;

    @Override
    protected void initViews() {
        popupContainer = (ViewGroup) getView().findViewById(R.id.popup_container);
        popupContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFragmentBackStack();
            }
        });
    }

    private boolean clearFragmentBackStack() {
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getActivity().getSupportFragmentManager().popBackStack();
            return true;
        }

        return false;
    }
}
