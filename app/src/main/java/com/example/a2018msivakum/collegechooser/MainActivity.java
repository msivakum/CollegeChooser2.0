package com.example.a2018msivakum.collegechooser;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SurveyFragment.SurveyFragmentInterface, DisplayFragment.DisplayFragmentInterface, CollegeItemFragment.CollegeItemInterface, CollegeAdapter.CAInterface{
//https://www.androidhive.info/2016/01/android-working-with-recycler-view/

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ArrayList<College> collegeList, updateList, tempList;

    private ViewPager mViewPager;
    private Button mButton;

    private SurveyFragment surveyFrag;
    private DisplayFragment displayFrag;
    private CollegeItemFragment citemFrag;

    private College mCollege;
    private int mInd;

    private Fragment mActiveFragment;
    private String TAG = "MAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.backbutton);
        fab.setOnClickListener(this);

        collegeList = new ArrayList<>();
        tempList = new ArrayList<>();
        updateList = new ArrayList<>();

        readData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.backbutton){
            if(mInd == 1) {
                mViewPager.setCurrentItem(0, true);
                resetLists();
            }
            if(mInd == 2) {
                mViewPager.setCurrentItem(1, true);
                mInd = 1;
            }
        }
    }

    public void readData() {
        InputStream is = getResources().openRawResource(R.raw.collegedata);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {

                String[] items = line.split(",");

                College colleges = new College();
                colleges.setId(items[0]);
                colleges.setName(items[1]);
                colleges.setInPrice(items[2]);
                colleges.setOutPrice(items[3]);
                colleges.setAdmitTot(items[4]);
                colleges.setAdmitMen(items[5]);
                colleges.setAdmitWomen(items[6]);
                colleges.setEnrolled(items[7]);
                colleges.setSatRead25(items[8]);
                colleges.setSatRead75(items[9]);
                colleges.setSatMath25(items[10]);
                colleges.setSatMath75(items[11]);
                colleges.setSatWrit25(items[12]);
                colleges.setSatWrit75(items[13]);
                colleges.setAct25(items[14]);
                colleges.setAct75(items[15]);
                colleges.setRank(items[16]);
                collegeList.add(colleges);
            }
        } catch (IOException err) {
            Log.e(TAG, "Error" + line, err);
            err.printStackTrace();
        }
        tempList = collegeList;
        Log.i(TAG, "Number of Colleges " + collegeList.size());
    }

    @Override
    public void switchToThirdFrag(int i) {
        mViewPager.setCurrentItem(2, true);
        citemFrag.receiveCol(updateList.get(i));
        mInd = 2;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
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
        public Fragment getItem(int position) {
            switch (position) {
                case 0 :
                    surveyFrag = SurveyFragment.newInstance();
                    return surveyFrag;
                case 1 :
                    displayFrag = DisplayFragment.newInstance();;
                    return displayFrag;
                case 2 :
                    citemFrag = CollegeItemFragment.newInstance();
                    return citemFrag;
                default :
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }



    @Override
    public void setSurveyFragmentActive() {
        mActiveFragment = surveyFrag;
        mInd = 0;
    }

    @Override
    public void setDisplayFragmentActive() {
        mActiveFragment = displayFrag;
        mInd = 1;
    }

    @Override
    public void setCollegeItemFragmentActive() {
        mActiveFragment = citemFrag;
        mInd = 2;
    }

    @Override
    public void passData(College col){
        Log.i(TAG, "passData is called");

        Log.i(TAG, "editText numbers are: " + col.getRank() + " and " + col.getAdmitTot());

        sortRank(Integer.parseInt(col.getRank()));
        sortTotAdmitRate(Integer.parseInt(col.getAdmitTot()));

        displayFrag.getRV().setAdapter(new CollegeAdapter(updateList));

        Log.i(TAG, "FINALupdateList has: " + updateList.size());
        //displayFrag.getData(col);
    }

    public void resetLists(){
        updateList = new ArrayList<>();
        tempList = collegeList;
    }

    public void sortRank(int a){
        Log.i(TAG, "sortRank is called");
        for (int k = 0; k < tempList.size(); k++) {
            if (Integer.parseInt(tempList.get(k).getRank()) < a && Integer.parseInt(tempList.get(k).getRank()) != -1) {
                updateList.add(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    public void sortTotAdmitRate(int a) {
        Log.i(TAG, "sortAdmitRate is called");
        for (int k = tempList.size() - 1; k >= 0; k--) {
            if (Integer.parseInt(tempList.get(k).getAdmitTot()) > a || Integer.parseInt(tempList.get(k).getAdmitTot()) == -1) {
                updateList.remove(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    public void sortEnrollment(int a){
        Log.i(TAG, "sortEnrollment is called");
        for(int k = tempList.size()-1; k >= 0; k--) {
            if(Integer.parseInt(tempList.get(k).getEnrolled()) > a || Integer.parseInt(tempList.get(k).getEnrolled()) == -1){
                updateList.remove(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    @Override
    public void switchToSecondFrag(){
        mViewPager.setCurrentItem(1, true);
        mInd = 1;
    }

    /*@Override
    public void switchToThirdFrag(){
        mViewPager.setCurrentItem(2, true);
        mInd = 2;
    }*/

    @Override
    public void setCol(College c){
        mCollege = c;
        citemFrag.receiveCol(mCollege);
    }

    @Override
    public ViewPager getVP(){
        return mViewPager;
    }
}
