package com.example.a2018msivakum.collegechooser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * Created by 2018msivakum on 10/24/2017.
 */

public class SurveyFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private View mRootView;
    private SurveyFragmentInterface mCallback;
    private Button mButton;
    private EditText mEditTextRank;
    private EditText mEditTextTotAdmit;
    private Spinner mSpinnerRank, mSpinnerTotRate;
    private College col;
    private Integer mInt;

    private Integer[] mArray;

    private String[] ranks, totrates;

    private String TAG = "SURVEYFRAG";

    public SurveyFragment() { }

    public static SurveyFragment newInstance() {
        SurveyFragment fragment1 = new SurveyFragment();
        return fragment1;
    }
    
    public College getCollege(Integer[] a){
        Log.i(TAG, "getCollege called");
        col.setRank(a[0].toString());
        col.setAdmitTot(a[1].toString());
        return col;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Survey Fragment", "onAttach");

        try {
            mCallback = (SurveyFragmentInterface) context;
            if (this.getUserVisibleHint()) {
                //NOTIFY ACTIVITY THAT THIS IS THE ACTIVE FRAGMENT
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

        mArray = new Integer[15];

        mSpinnerRank = (Spinner) mRootView.findViewById(R.id.rankmenu);
        mSpinnerRank.setOnItemSelectedListener(this);

        ranks = new String[]{"10", "20", "30", "40", "50", "-1"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>((Context) mCallback, android.R.layout.simple_spinner_dropdown_item, ranks);
        mSpinnerRank.setAdapter(adapter1);

        mSpinnerTotRate = (Spinner) mRootView.findViewById(R.id.totratemenu);
        mSpinnerTotRate.setOnItemSelectedListener(this);

        totrates = new String[]{"5", "10", "15", "20", "25", "30", "40", "50", "100", "-1"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>((Context) mCallback, android.R.layout.simple_spinner_dropdown_item, totrates);
        mSpinnerTotRate.setAdapter(adapter2);

        mButton = (Button) mRootView.findViewById(R.id.button1);
        mButton.setOnClickListener(this);

        col = new College();

        return mRootView;
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick called");
        mCallback.passData(getCollege(mArray));
        mCallback.switchToSecondFrag();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "onItemSelected is called" + adapterView.getId());
        switch (adapterView.getId()) {
            case R.id.rankmenu:
                mArray[0] = Integer.parseInt(ranks[i]);
                Log.i(TAG, "rank selected = " + ranks[i]);
                break;
            case R.id.totratemenu:
                mArray[1] = Integer.parseInt(totrates[i]);
                Log.i(TAG, "tot rate selected = " + totrates[i]);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface SurveyFragmentInterface {
        void setSurveyFragmentActive();
        void passData(College col);
        void switchToSecondFrag();
    }
}
