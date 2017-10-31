package com.example.a2018msivakum.collegechooser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 2018msivakum on 10/26/2017.
 */

public class DisplayFragment extends Fragment implements View.OnClickListener{

    private String TAG = "DISPLAYFRAG";

    private View mRootView;
    private DisplayFragment.DisplayFragmentInterface mCallback;

    private ArrayList<College> collegeList, updateList, tempList;

    public String DATA_RECEIVE = "store";
    private String argString;

    public DisplayFragment() { }

    public static DisplayFragment newInstance() {
        DisplayFragment fragment2 = new DisplayFragment();

        return fragment2;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Survey Fragment", "onAttach");

        try {
            mCallback = (DisplayFragment.DisplayFragmentInterface) context;
            if (this.getUserVisibleHint()) {
                // NOTIFY ACTIVITY THAT THIS IS THE ACTIVE FRAGMENT
                mCallback.setDisplayFragmentActive();
            }

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DisplayFragmentInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recyclerlayout, container, false);

        collegeList = new ArrayList<>();
        updateList = new ArrayList<>();
        tempList = new ArrayList<>();

        readData();
        //sortRank(Integer.parseInt(args.getString(DATA_RECEIVE)));

        //sortTotAdmitRate(15);
        //sortEnrollment(2000);

        RecyclerView rvColleges = (RecyclerView) mRootView.findViewById(R.id.collegerv);
        CollegeAdapter adapter = new CollegeAdapter((Context) mCallback, updateList);
        rvColleges.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager((Context) mCallback, LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        rvColleges.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration((Context) mCallback, DividerItemDecoration.VERTICAL);
        rvColleges.addItemDecoration(itemDecoration);

        //sortRank(Integer.parseInt(argString));

        return mRootView;
    }

    /*@Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            Log.i("DISPLAYFRAG", "args is not null");
            argString = args.getString(DATA_RECEIVE);
            sortRank(Integer.parseInt(argString));
        }
    }*/

    public void getData(String s, String s2) {
        /*Bundle args = getArguments();
        if (args != null) {
            Log.i(TAG, "args is not null");
            argString = args.getString(DATA_RECEIVE);*/
        Log.i(TAG, "editText numbers are: " + s + " and " + s2);
        sortRank(Integer.parseInt(s));
        sortTotAdmitRate(Integer.parseInt(s2));
        //}
    }

    @Override
    public void onClick(View view) {

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

                //Log.i("MainActivity", "Just Created " + colleges.getId());
            }
        } catch (IOException err) {
            Log.e(TAG, "Error" + line, err);
            err.printStackTrace();
        }

        tempList = collegeList;
        Log.i(TAG, "Number of Colleges " + collegeList.size());
    }

    public void sortRank(int a){
        for(int k = 0; k < tempList.size(); k++){
            if(Integer.parseInt(tempList.get(k).getRank()) < a && Integer.parseInt(tempList.get(k).getRank()) != -1){
                updateList.add(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    public void sortTotAdmitRate(int a) {
        for(int k = tempList.size()-1; k >= 0; k--) {
            if(Integer.parseInt(tempList.get(k).getAdmitTot()) > a || Integer.parseInt(tempList.get(k).getAdmitTot()) == -1){
                updateList.remove(tempList.get(k));
            }
        }
        tempList = updateList;
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    public void sortEnrollment(int a){
        for(int k = tempList.size()-1; k >= 0; k--) {
            if(Integer.parseInt(tempList.get(k).getEnrolled()) > a || Integer.parseInt(tempList.get(k).getEnrolled()) == -1){
                updateList.remove(tempList.get(k));
            }
        }
        Log.i(TAG, "NEWupdateList has: " + updateList.size());
    }

    public interface DisplayFragmentInterface {
        void setDisplayFragmentActive();
    }
}
