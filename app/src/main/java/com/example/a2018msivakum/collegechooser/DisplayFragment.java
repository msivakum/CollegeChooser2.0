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

//public class DisplayFragment extends Fragment implements RecyclerView.OnItemTouchListener{
public class DisplayFragment extends Fragment {

    private String TAG = "DISPLAYFRAG";

    private View mRootView;
    private RecyclerView rvColleges;
    private DisplayFragment.DisplayFragmentInterface mCallback;
    private CollegeAdapter mAdapter;
    private ArrayList<College> collegeList, updateList, tempList, clickList;


    private College mCollege;

    private Integer mCount = 1;

    public DisplayFragment() {}

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

        rvColleges = (RecyclerView) mRootView.findViewById(R.id.collegerv);
        CollegeAdapter newAdapt = new CollegeAdapter(updateList);
        rvColleges.setAdapter(newAdapt);

        LinearLayoutManager layoutManager = new LinearLayoutManager((Context) mCallback, LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        rvColleges.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration((Context) mCallback, DividerItemDecoration.VERTICAL);
        rvColleges.addItemDecoration(itemDecoration);

        Log.i(TAG, ((MainActivity) mCallback).getFileSaver().getList() + "");

        return mRootView;
    }

//    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        int position = rv.getChildPosition(child);
        if(position != -1) {
//            myOnClick(child, position);
        }
        return false;
    }

    public void myOnClick(View view, int pos){
        //Toast.makeText((Context) mCallback, pos + ": " + clickList.get(pos).getName(), Toast.LENGTH_SHORT).show();
        //mCallback.switchToThirdFrag();
        mCallback.setCol(clickList.get(pos));
    }

//    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

//    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public RecyclerView getRV() {
        return rvColleges;
    }


    public interface DisplayFragmentInterface {
        void setDisplayFragmentActive();
        ViewPager getVP();
        void setCol(College c);
    }
}
