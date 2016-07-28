package com.ir.android.modalbottomSheet;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ir.android.R;


/**
 * Created by gabriel on 16/07/26.
 */
public class modalbottomsheet
        extends BottomSheetDialogFragment {

    private static BottomSheetDialogFragment btmSheet;
    private static boolean Chooser;

    public static BottomSheetDialogFragment newInstance(boolean Chosen) {
        btmSheet = new BottomSheetDialogFragment();
        Chooser = Chosen;
        return btmSheet;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (Chooser == true){
            View v = inflater.inflate(
                    R.layout.popup_dialog_assault, container, false);
            return v;
        } else {
            View v = inflater.inflate(
                    R.layout.popup_dialog_officer, container, false);
            return v;
        }
    }



}
