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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 2018msivakum on 11/30/2017.
 */

public class CollegeItemFragment extends Fragment {

    private View mRootView;
    //private RecyclerView rvColleges;
    private CollegeItemFragment.CollegeItemInterface mCallback;
    private CollegeAdapter mAdapter;
    private ArrayList<College> collegeList, updateList, tempList, clickList;

    private TextView mColNameView;
    private TextView mRankView;
    private TextView mTotAdmitView;
   
    public CollegeItemFragment() { }

    public static CollegeItemFragment newInstance() {
        CollegeItemFragment fragment3 = new CollegeItemFragment();

        return fragment3;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Survey Fragment", "onAttach");

        try {
            mCallback = (CollegeItemFragment.CollegeItemInterface) context;
            if (this.getUserVisibleHint()) {
                mCallback.setCollegeItemFragmentActive();
            }

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement CollegeItemFragmentInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.collegeitemlayout, container, false);

        mColNameView = (TextView) mRootView.findViewById(R.id.collegeitem);
        mRankView = (TextView) mRootView.findViewById(R.id.rankstats);
        mTotAdmitView = (TextView) mRootView.findViewById(R.id.totadmitstats);

        collegeList = new ArrayList<>();
        updateList = new ArrayList<>();
        tempList = new ArrayList<>();

        return mRootView;
    }

    public void receiveCol(College c){
        mColNameView.setText(c.getName());
        mRankView.setText("Rank: " + c.getRank());
        mTotAdmitView.setText("Total Admission Rate: " + c.getAdmitTot());

    }

    public interface CollegeItemInterface{
        void setCollegeItemFragmentActive();
    }
}
