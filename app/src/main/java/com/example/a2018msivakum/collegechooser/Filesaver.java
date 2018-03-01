package com.example.a2018msivakum.collegechooser;


import java.util.List;

/**
 * Created by 2018msivakum on 11/8/2017.
 */

public class Filesaver {

    private College mCollege;
    private Integer[] mArray;
    private List<College> mList;

    public Filesaver(College b) {
        mCollege = b;
    }

    public Filesaver(){
        mCollege = null;
    }

    public College getFile(){
        return mCollege;
    }

    public void setFile(College b){
        mCollege = b;
    }

    public Integer [] getArray(){
        return mArray;
    }

    public void setArray(Integer [] a){
        mArray = a;
    }

    public List<College> getList() {
        return mList;
    }

    public void setList(List<College> l){
        mList = l;
    }

}
