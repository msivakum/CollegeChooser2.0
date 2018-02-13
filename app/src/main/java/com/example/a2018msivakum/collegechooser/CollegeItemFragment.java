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

    private TextView mColNameView, mRankView, mTotAdmitView, mEnrollView, mOutPriceView, mInPriceView, mMenAdmitView, mWomenAdmitView, mSATRead25View, mSATRead75View, mSATMath25View, mSATMath75View, mSATWrite25View, mSATWrite75View, mACTComp25View, mACTComp75View;
   
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
        mMenAdmitView = (TextView) mRootView.findViewById(R.id.menadmitstats);
        mWomenAdmitView = (TextView) mRootView.findViewById(R.id.womenadmitstats);
        mEnrollView = (TextView) mRootView.findViewById(R.id.enrollstats);
        mOutPriceView = (TextView) mRootView.findViewById(R.id.outpricestats);
        mInPriceView = (TextView) mRootView.findViewById(R.id.inpricestats);

        mSATRead25View = (TextView) mRootView.findViewById(R.id.satread25);
        mSATRead75View = (TextView) mRootView.findViewById(R.id.satread75);
        mSATMath25View = (TextView) mRootView.findViewById(R.id.satmath25);
        mSATMath75View = (TextView) mRootView.findViewById(R.id.satmath75);
        mSATWrite25View = (TextView) mRootView.findViewById(R.id.satwrite25);
        mSATWrite75View = (TextView) mRootView.findViewById(R.id.satwrite75);
        mACTComp25View = (TextView) mRootView.findViewById(R.id.actcomp25);
        mACTComp75View = (TextView) mRootView.findViewById(R.id.actcomp75);

        collegeList = new ArrayList<>();
        updateList = new ArrayList<>();
        tempList = new ArrayList<>();

        return mRootView;
    }

    public void receiveCol(College c){
        mColNameView.setText(c.getName());
        mRankView.setText("Rank: #" + c.getRank());
        mTotAdmitView.setText("Total Admission Rate: " + c.getAdmitTot() + "%");
        mMenAdmitView.setText("Admission Rate (Men): " + c.getAdmitMen() + "%");
        mWomenAdmitView.setText("Admission Rate (Women): " + c.getAdmitWomen() + "%");
        mEnrollView.setText("Size: " + c.getEnrolled() + " students");
        mOutPriceView.setText("Out of State Tuition: $" +  c.getOutPrice());
        mInPriceView.setText("In-State Tuition: $" +  c.getInPrice());

        mSATRead25View.setText("SAT Reading 25th Percentile: " + c.getSatRead25());
        mSATRead75View.setText("            75th Percentile: " + c.getSatRead75());

        mSATMath25View.setText("SAT Math 25th Percentile: " + c.getSatMath25());
        mSATMath75View.setText("         75th Percentile: " + c.getSatMath75());

        mSATWrite25View.setText("SAT Writing 25th Percentile: " + c.getSatWrit25());
        mSATWrite75View.setText("            75th Percentile: " + c.getSatWrit75());

        mACTComp25View.setText("ACT Comp. 25th Percentile: " + c.getAct25());
        mACTComp75View.setText("          75th Percentile: " + c.getAct75());
    }

    public interface CollegeItemInterface{
        void setCollegeItemFragmentActive();
    }
}
