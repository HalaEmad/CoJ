package com.ir.android.walkthrough;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;
import com.ir.android.main.MainScreen;

/**
 * Created by bassam on 09-07-2016.
 */
public class WalkthroughScreen extends Fragment {


    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private SectionsPagerAdapter mPagerAdapter;


    @Override
    protected Controller createController() {
        return new WalkthroughCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "walkthrough.ctrl";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_walkthrough;
    }

    @Override
    protected void initViews() {

        ActionBar actionBar = ((MainScreen)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);

        mPager = (ViewPager) getView().findViewById(R.id.walkthrough_pager);
        mPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    @Override
    protected void bindViews(ViewModel viewModel) {
        mPagerAdapter.notifyDataSetChanged();
        mPager.setAdapter(mPagerAdapter);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ImageView mPageImageView;
        private TextView mPageTitle;
        private TextView mPageDetails;
        private TextView mSubDetails;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.page_walkthrough, container, false);

            ImageView mPageImageView = (ImageView) rootView.findViewById(R.id.wt_icon);
            TextView mPageTitle = (TextView) rootView.findViewById(R.id.title_textView);
            TextView mPageDetails = (TextView) rootView.findViewById(R.id.details_textview);
            TextView mSubDetails = (TextView) rootView.findViewById(R.id.details_2_textview);
            int position = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (position) {
                case 0:
                    mPageImageView.setImageResource(R.mipmap.wt_alert_icon);
                    mPageTitle.setText(getResources().getString(R.string.wt_active_call_title));
                    mPageDetails.setText(getResources().getString(R.string.wt_active_call_detail));
                    mSubDetails.setText(getResources().getString(R.string.wt_active_call_detail_2));

                    break;

                case 1:
                    mPageImageView.setImageResource(R.mipmap.wt_location_icon);
                    mPageTitle.setText(getResources().getString(R.string.wt_location_title));
                    mPageDetails.setText(getResources().getString(R.string.wt_location_detail));
                    break;

                case 2:
                    mPageImageView.setImageResource(R.mipmap.wt_exclamation_mark);
                    mPageTitle.setText(getResources().getString(R.string.wt_incident_marker_title));
                    mPageDetails.setText(getResources().getString(R.string.wt_incident_marker_detail));

                    break;
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Log.i("pager", "" + position);
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }


    }
}
