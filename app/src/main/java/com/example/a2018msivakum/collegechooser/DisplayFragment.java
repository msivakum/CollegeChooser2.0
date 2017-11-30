package com.example.a2018msivakum.collegechooser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 2018msivakum on 10/26/2017.
 */

public class DisplayFragment extends Fragment implements RecyclerView.OnItemTouchListener{

    private String TAG = "DISPLAYFRAG";

    private View mRootView;
    private RecyclerView rvColleges;
    private DisplayFragment.DisplayFragmentInterface mCallback;
    private CollegeAdapter mAdapter;
    private ArrayList<College> collegeList, updateList, tempList, clickList;

    private College mCollege;

    private Integer mCount = 1;

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

        rvColleges = (RecyclerView) mRootView.findViewById(R.id.collegerv);
        CollegeAdapter cAdapter = new CollegeAdapter((updateList));
        rvColleges.setAdapter(cAdapter);
        rvColleges.addOnItemTouchListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager((Context) mCallback, LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        rvColleges.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration((Context) mCallback, DividerItemDecoration.VERTICAL);
        rvColleges.addItemDecoration(itemDecoration);

        /*rvColleges.addOnItemTouchListener(new RecyclerTouchListener((Context) mCallback, rvColleges, new RecyclerTouchListener.ClickListener(){
            @Override
            public void myOnClick(View view, int position) {
                Log.i(TAG, "onClick is called from DisplayFragment");
                Log.i(TAG, "position: " + position);
                Log.i(TAG, "name: " + clickList.get(position).getName());
                Toast.makeText((Context) mCallback, position + ": " + clickList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        }));*/

        return mRootView;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        int position = rv.getChildPosition(child);
        if(position != -1) {
            myOnClick(child, position);
        }
        return true;
    }

    public void myOnClick(View view, int pos){
        //Toast.makeText((Context) mCallback, pos + ": " + clickList.get(pos).getName(), Toast.LENGTH_SHORT).show();
        mCallback.switchToThirdFrag();
        mCallback.setCol(clickList.get(pos));
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public void getData(College col) {
        Log.i(TAG, "editText numbers are: " + col.getRank() + " and " + col.getAdmitTot());

        sortRank(Integer.parseInt(col.getRank()));
        sortTotAdmitRate(Integer.parseInt(col.getAdmitTot()));
        rvColleges.setAdapter(new CollegeAdapter(updateList));

        Log.i(TAG, "FINALupdateList has: " + updateList.size());

        clickList = updateList;

        resetLists();
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

    public interface DisplayFragmentInterface {
        void setDisplayFragmentActive();
        ViewPager getVP();
        void switchToThirdFrag();
        void setCol(College c);
    }
}
