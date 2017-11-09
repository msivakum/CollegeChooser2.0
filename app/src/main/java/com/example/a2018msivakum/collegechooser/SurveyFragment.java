package com.example.a2018msivakum.collegechooser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 2018msivakum on 10/24/2017.
 */

public class SurveyFragment extends Fragment implements View.OnClickListener{

    private View mRootView;
    private SurveyFragmentInterface mCallback;
    private Button mButton;
    private EditText mEditTextRank;
    private EditText mEditTextTotAdmit;
    private College col;

    private String TAG = "SURVEYFRAG";

    public SurveyFragment() { }

    public static SurveyFragment newInstance() {
        SurveyFragment fragment1 = new SurveyFragment();

        return fragment1;
    }

    public String getRankData(){
        return mEditTextRank.getText().toString();
    }
    public String getTotAdmitData(){
        return mEditTextTotAdmit.getText().toString();
    }
    
    public College getCollege(){
        Log.i(TAG, "getCollege called");
        col.setRank(mEditTextRank.getText().toString());
        col.setAdmitTot(mEditTextTotAdmit.getText().toString());
        return col;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Survey Fragment", "onAttach");

        try {
            mCallback = (SurveyFragmentInterface) context;
            if (this.getUserVisibleHint()) {
                // NOTIFY ACTIVITY THAT THIS IS THE ACTIVE FRAGMENT
                mCallback.setSurveyFragmentActive();
            }

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement SurveyFragmentInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.surveylayout, container, false);
        mEditTextRank = (EditText) mRootView.findViewById(R.id.edittext1);
        mEditTextTotAdmit = (EditText) mRootView.findViewById(R.id.edittext2);

        mButton = (Button) mRootView.findViewById(R.id.button1);
        mButton.setOnClickListener(this);

        col = new College();

        return mRootView;
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick called");
        mCallback.passData(getCollege());
    }

    public interface SurveyFragmentInterface {
        void setSurveyFragmentActive();
        void passData(College col);
    }
}
