package com.example.a2018msivakum.collegechooser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 2018msivakum on 9/28/2017.
 */

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ViewHolder> {

    private List<College> mColleges;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;;

        public ViewHolder(View view) {
            super(view);

            nameTextView = (TextView) view.findViewById(R.id.collegename);
        }
    }

    public CollegeAdapter(List<College> listcolleges) {
        mColleges = listcolleges;
        //mContext = context;
    }

    @Override
    public CollegeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View collegeView = inflater.inflate(R.layout.item_college, parent, false);
        ViewHolder viewHolder = new ViewHolder(collegeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CollegeAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        College col = mColleges.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(col.getName());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mColleges.size();
    }
}
