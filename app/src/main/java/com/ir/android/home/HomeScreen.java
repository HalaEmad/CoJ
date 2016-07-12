package com.ir.android.home;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;


/**
 * Created by bassam on 09-07-2016.
 */
public class HomeScreen extends Fragment {

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
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {

        mAreaTextView = (TextView) getView().findViewById(R.id.area);
        mDateTextView = (TextView) getView().findViewById(R.id.date);
        mDriverTextView = (TextView) getView().findViewById(R.id.driver);
        mNameTextView = (TextView) getView().findViewById(R.id.surname_text);

        mRegTextView = (TextView) getView().findViewById(R.id.reg_label);
        mShiftTextView = (TextView) getView().findViewById(R.id.shift);
        mOccTextView = (TextView) getView().findViewById(R.id.occ_label);

        mOnBreakBtn = (Button) getView().findViewById(R.id.home_onbreak_btn);
        mOnBreakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        mOnDutyBtn = (Button) getView().findViewById(R.id.home_onduty_btn);
        mOnDutyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

    }

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
