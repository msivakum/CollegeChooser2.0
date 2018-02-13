package com.example.a2018msivakum.collegechooser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    private Filesaver fileSaver;
    private String INTERNAL_STORAGE_FILE = "storage.txt";
    private Gson gson;

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

        if(readInternalFile() != null && mActiveFragment == surveyFrag) {
            Toast.makeText(getApplicationContext(), "Sorting criteria taken from previous submission", Toast.LENGTH_LONG).show();
        }

        gson = new GsonBuilder().create();

        String s = readInternalFile();
        fileSaver = gson.fromJson(s, Filesaver.class);
        if(fileSaver == null) {
            Log.i(TAG, "fileSaver is null");
            fileSaver = new Filesaver();
        }
        else {
            Log.i(TAG, "fileSaver is NOT null");
            Log.i(TAG, fileSaver.getFile().getName());
        }
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
                Log.i(TAG, "backbutton is called on third fragment");
                mInd = 1;
            }
        }
    }

    //------------------------------------------//

    public void readData() {
        InputStream is = getResources().openRawResource(R.raw.collegedata);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {

                String[] items = line.split(",");

                College colleges = new College();
                colleges.setId(items[0]); //---
                colleges.setName(items[1]); //---
                colleges.setInPrice(items[2]); //---
                colleges.setOutPrice(items[3]); //sorted
                colleges.setAdmitTot(items[4]); //sorted
                colleges.setAdmitMen(items[5]); //---
                colleges.setAdmitWomen(items[6]); //---
                colleges.setEnrolled(items[7]); //sorted
                colleges.setSatRead25(items[8]); //---
                colleges.setSatRead75(items[9]);
                colleges.setSatMath25(items[10]); //---
                colleges.setSatMath75(items[11]);
                colleges.setSatWrit25(items[12]); //---
                colleges.setSatWrit75(items[13]);
                colleges.setAct25(items[14]); //---
                colleges.setAct75(items[15]);
                colleges.setRank(items[16]); //sorted
                collegeList.add(colleges);
            }
        } catch (IOException err) {
            Log.e(TAG, "Error" + line, err);
            err.printStackTrace();
        }
        tempList = collegeList;
        Log.i(TAG, "Number of Colleges " + collegeList.size());
    }

    //------------------------------------------//

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0 :
                    surveyFrag = SurveyFragment.newInstance(fileSaver);
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

    //------------------------------------------//

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

    //------------------------------------------//

    @Override
    public void passData(College col){
        Log.i(TAG, "passData is called");

        Log.i(TAG, "sort numbers are: " + col);

        sortRank(Integer.parseInt(col.getRank()) + 1);
        sortTotAdmitRate(Integer.parseInt(col.getAdmitTot()));
        sortEnrollment(Integer.parseInt(col.getEnrolled()));
        sortOutPrice(Integer.parseInt(col.getOutPrice()));
        if(col.getSatRead75() != null) {
            sortSATRead(Integer.parseInt(col.getSatRead75()));
            sortSATMath(Integer.parseInt(col.getSatMath75()));
            sortSATWrite(Integer.parseInt(col.getSatWrit75()));
        }
        else if(col.getAct75() != null)
            sortACTComp(Integer.parseInt(col.getAct75()));

        displayFrag.getRV().setAdapter(new CollegeAdapter(updateList));

        Log.i(TAG, "FINALupdateList has: " + updateList.size());
    }

    public void resetLists(){
        updateList = new ArrayList<>();
        tempList = collegeList;
    }

    //---------------SORTING METHODS---------------//

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

    public void sortOutPrice(int a){
        Log.i(TAG, "sortOutPrice is called");
        for(int k = tempList.size()-1; k >= 0; k--) {
            if(Integer.parseInt(tempList.get(k).getOutPrice()) > a || Integer.parseInt(tempList.get(k).getOutPrice()) == -1){
                updateList.remove(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    public void sortSATRead(int a){
        Log.i(TAG, "sortSATRead is called");
        for(int k = tempList.size()-1; k >= 0; k--) {
            if(Integer.parseInt(tempList.get(k).getSatRead75()) > a || Integer.parseInt(tempList.get(k).getSatRead75()) == -1){
                updateList.remove(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    public void sortSATMath(int a) {
        Log.i(TAG, "sortSATMath is called");
        for (int k = tempList.size() - 1; k >= 0; k--) {
            if (Integer.parseInt(tempList.get(k).getSatMath75()) > a || Integer.parseInt(tempList.get(k).getSatMath75()) == -1) {
                updateList.remove(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    public void sortSATWrite(int a) {
        Log.i(TAG, "sortSATWrite is called");
        for (int k = tempList.size() - 1; k >= 0; k--) {
            if (Integer.parseInt(tempList.get(k).getSatWrit75()) > a || Integer.parseInt(tempList.get(k).getSatWrit75()) == -1) {
                updateList.remove(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    public void sortACTComp(int a) {
        Log.i(TAG, "sortACTComp is called");
        for (int k = tempList.size() - 1; k >= 0; k--) {
            if (Integer.parseInt(tempList.get(k).getAct75()) > a || Integer.parseInt(tempList.get(k).getAct75()) == -1) {
                updateList.remove(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    //------------------------------------------//

    @Override
    public void switchToSecondFrag(){
        mViewPager.setCurrentItem(1, true);
        mInd = 1;
    }

    @Override
    public void switchToThirdFrag(int i) {
        mViewPager.setCurrentItem(2, true);
        citemFrag.receiveCol(updateList.get(i));
        mInd = 2;
    }

    //------------------------------------------//

    @Override
    public void setCol(College c){
        mCollege = c;
        citemFrag.receiveCol(mCollege);
    }

    @Override
    public ViewPager getVP(){
        return mViewPager;
    }

    //------------------------------------------//

    @Override
    public String readInternalFile() {
        Context context = getApplicationContext();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            FileInputStream fis;
            fis = context.openFileInput(INTERNAL_STORAGE_FILE);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            Log.i("MAINACT", result.toString());
            return result.toString("UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void writeInternalFile(Filesaver s) {
        String str = gson.toJson(s);
        Context context = getApplicationContext();
        try {
            FileOutputStream fos;
            fos = context.openFileOutput(INTERNAL_STORAGE_FILE, Context.MODE_PRIVATE);
            fos.write(str.getBytes());
            fos.close();
            Log.i("MAINACT2", s.getFile().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
