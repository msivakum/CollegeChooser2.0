package com.example.a2018msivakum.collegechooser;

import android.app.Activity;
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
import android.view.WindowManager;
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
    private EditText mSATread, mSATmath, mSATwrite, mACTcomp;
    private Spinner mSpinnerRank, mSpinnerTotRate, mSpinnerEnroll, mSpinnerOutPrice;
    private College col;
    private Integer mInt;
    private static Filesaver mfs;

    private Integer[] mArray;

    private String[] ranks, totrates, enrolls, outPrices;

    private String TAG = "SURVEYFRAG";

    public SurveyFragment() { }

    public SurveyFragment(Filesaver fs) {
        mfs = fs;
    }

    public static SurveyFragment newInstance(Filesaver fs) {
        SurveyFragment fragment1 = new SurveyFragment(fs);
        mfs = fs;
        return fragment1;
    }

    public Filesaver getFS(){
        return mfs;
    }
    
    public College getCollege(Integer[] a){
        Log.i(TAG, "getCollege called");
        col.setRank(a[0].toString());
        col.setAdmitTot(a[1].toString());
        col.setEnrolled(a[2].toString());
        col.setOutPrice(a[3].toString());
        if(a[4] != null) {
            col.setSatRead75(a[4].toString());
            col.setSatMath75(a[5].toString());
            col.setSatWrit75(a[6].toString());
        }
        else if(a[7] != null) {
            col.setAct75(a[7].toString());
        }

        col.setName("Current Sort College");

        mfs.setFile(col);
        mfs.setArray(a);
        mCallback.writeInternalFile(mfs);
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

        ((Activity) mCallback).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mArray = new Integer[15];

        //----------------------------------------------------
        mSpinnerRank = (Spinner) mRootView.findViewById(R.id.rankmenu);
        mSpinnerRank.setOnItemSelectedListener(this);

        ranks = new String[]{"10", "20", "30", "40", "50", "-1"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>((Context) mCallback, android.R.layout.simple_spinner_dropdown_item, ranks);
        mSpinnerRank.setAdapter(adapter1);
        //----------------------------------------------------
        mSpinnerTotRate = (Spinner) mRootView.findViewById(R.id.totratemenu);
        mSpinnerTotRate.setOnItemSelectedListener(this);

        totrates = new String[]{"5", "10", "15", "20", "25", "30", "40", "50", "100", "-1"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>((Context) mCallback, android.R.layout.simple_spinner_dropdown_item, totrates);
        mSpinnerTotRate.setAdapter(adapter2);
        //----------------------------------------------------
        mSpinnerEnroll = (Spinner) mRootView.findViewById(R.id.enrollmenu);
        mSpinnerEnroll.setOnItemSelectedListener(this);

        enrolls = new String[]{"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000", "-1"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>((Context) mCallback, android.R.layout.simple_spinner_dropdown_item, enrolls);
        mSpinnerEnroll.setAdapter(adapter3);
        //----------------------------------------------------
        mSpinnerOutPrice = (Spinner) mRootView.findViewById(R.id.outpricemenu);
        mSpinnerOutPrice.setOnItemSelectedListener(this);

        outPrices = new String[]{"10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "-1"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>((Context) mCallback, android.R.layout.simple_spinner_dropdown_item, outPrices);
        mSpinnerOutPrice.setAdapter(adapter4);
        //----------------------------------------------------
        mSATread = (EditText) mRootView.findViewById(R.id.satreadedit);
        mSATmath = (EditText) mRootView.findViewById(R.id.satmathedit);
        mSATwrite = (EditText) mRootView.findViewById(R.id.satwriteedit);
        mACTcomp = (EditText) mRootView.findViewById(R.id.actedit);

        mButton = (Button) mRootView.findViewById(R.id.button1);
        mButton.setOnClickListener(this);

        col = new College();

        if(mCallback.readInternalFile() != null){
            mSpinnerRank.setSelection(getIndex(mSpinnerRank, mfs.getArray()[0] + ""));
            mSpinnerTotRate.setSelection(getIndex(mSpinnerTotRate, mfs.getArray()[1] + ""));
            mSpinnerEnroll.setSelection(getIndex(mSpinnerEnroll, mfs.getArray()[2] + ""));
            mSpinnerOutPrice.setSelection(getIndex(mSpinnerOutPrice, mfs.getArray()[3] + ""));
            if(!mSATread.getText().toString().equals("")) {
                mSATread.setText(mfs.getArray()[4] + "");
                mSATmath.setText(mfs.getArray()[5] + "");
                mSATwrite.setText(mfs.getArray()[6] + "");
            }
            if(!mACTcomp.getText().toString().equals("")){
                mACTcomp.setText(mfs.getArray()[7] + "");
            }
        }

        return mRootView;
    }


    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick called");
        Log.i(TAG, "satread text " + mSATread.getText());

        if(!mSATread.getText().toString().equals("")) {
            mArray[4] = Integer.parseInt(mSATread.getText().toString());
            mArray[5] = Integer.parseInt(mSATmath.getText().toString());
            mArray[6] = Integer.parseInt(mSATwrite.getText().toString());
            mArray[7] = null;
        }
        else if(!mACTcomp.getText().toString().equals("")){
            mArray[4] = null;
            mArray[5] = null;
            mArray[6] = null;
            mArray[7] = Integer.parseInt(mACTcomp.getText().toString());
        }

        
        //fix this to take care of not changing Json file after initial save. Maybe display the saved numbers in the Spinners and EditTexts so that mArray mirrors Json


        if(mCallback.readInternalFile() != null && mArray == mfs.getArray()) {
            Log.i(TAG, "criteria taken from previous run");
            mCallback.passData(mfs.getFile());
        }
        else {
            Log.i(TAG, "NEWrun");
            mCallback.passData(getCollege(mArray));
        }

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
            case R.id.enrollmenu:
                mArray[2] = Integer.parseInt(enrolls[i]);
                Log.i(TAG, "enrollment selected = " + enrolls[i]);
                break;
            case R.id.outpricemenu:
                mArray[3] = Integer.parseInt(outPrices[i]);
                Log.i(TAG, "out price selected = " + outPrices[i]);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    public int getIndex(Spinner spinner, String str){
        int index = 0;

        for(int k = 0; k < spinner.getCount(); k++){
            if(spinner.getItemAtPosition(k).equals(str)){
                index = k;
                break;
            }
        }

        return index;
    }

    public interface SurveyFragmentInterface {
        void setSurveyFragmentActive();
        void passData(College col);
        void switchToSecondFrag();
        String readInternalFile();
        void writeInternalFile(Filesaver s);
    }
}
