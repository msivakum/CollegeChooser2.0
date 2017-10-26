package com.example.a2018msivakum.collegechooser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * Created by 2018msivakum on 10/26/2017.
 */

public class DisplayFragment extends Fragment implements View.OnClickListener{

    private View mRootView;
    private DisplayFragment.DisplayFragmentInterface mCallback;

    private ArrayList<College> collegeList, updateList;

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
        /*viewList = new ArrayList<>();
        idList = new ArrayList<>();
        inPriceList = new ArrayList<>();
        outPriceList = new ArrayList<>();
        totAdmitList = new ArrayList<>();
        menAdmitList = new ArrayList<>();
        womenAdmitList = new ArrayList<>();
        enrollList = new ArrayList<>();
        satMath25List = new ArrayList<>();
        satRead25List = new ArrayList<>();
        satWrit25List = new ArrayList<>();
        satMath75List = new ArrayList<>();
        satRead75List = new ArrayList<>();
        satWrit75List = new ArrayList<>();
        act25List = new ArrayList<>();
        act75List = new ArrayList<>();
        rankList = new ArrayList<>();*/

        readData();
        sortRank(MainActivity.getSurveyData());
        //sortTotAdmitRate(15);
        //sortEnrollment(2000);

        RecyclerView rvColleges = (RecyclerView) mRootView.findViewById(R.id.collegerv);

        CollegeAdapter adapter = new CollegeAdapter((Context) mCallback, updateList);
        // Attach the adapter to the recyclerview to populate items
        rvColleges.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager((Context) mCallback, LinearLayoutManager.VERTICAL, false);
        // Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0);
        // Attach layout manager to the RecyclerView
        rvColleges.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration((Context) mCallback, DividerItemDecoration.VERTICAL);
        rvColleges.addItemDecoration(itemDecoration);

        return mRootView;
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
            Log.e("MainActivity", "Error" + line, err);
            err.printStackTrace();
        }

        updateList = collegeList;
        Log.i("MainActivity", "Number of Colleges " + collegeList.size());
    }

    public void sortRank(int a){
        for(int k = updateList.size()-1; k >= 0; k--) {
            if(Integer.parseInt(updateList.get(k).getRank()) > a || Integer.parseInt(updateList.get(k).getRank()) < 0) {
                updateList.remove(updateList.get(k));
                //Log.i("MainActivity", "Rank is " + updateList.get(k).getRank());
            }
        }
    }

    public void sortTotAdmitRate(int a){
        for(int k = updateList.size()-1; k >= 0; k--) {
            if(Integer.parseInt(updateList.get(k).getAdmitTot()) > a || Integer.parseInt(updateList.get(k).getAdmitTot()) < 0) {
                updateList.remove(updateList.get(k));
                //Log.i("MainActivity", "Total admit rate is " + updateList.get(k).getAdmitTot());
            }
        }
    }

    public void sortEnrollment(int a){
        for(int k = updateList.size()-1; k >= 0; k--) {
            if(Integer.parseInt(updateList.get(k).getEnrolled()) > a || Integer.parseInt(updateList.get(k).getEnrolled()) < 0) {
                updateList.remove(updateList.get(k));
                //Log.i("MainActivity", "Enrollment size is " + updateList.get(k).getEnrolled());
            }
        }
    }

    public interface DisplayFragmentInterface {
        void setDisplayFragmentActive();
    }
}
