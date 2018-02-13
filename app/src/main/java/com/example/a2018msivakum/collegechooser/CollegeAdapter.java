package com.example.a2018msivakum.collegechooser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by 2018msivakum on 9/28/2017.
 */

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ViewHolder>{

    private List<College> mColleges;
    private Context mContext;
    private College mColInst;

    private CAInterface mCallback;

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{

        public TextView nameTextView;;

        public ViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);
            nameTextView = (TextView) view.findViewById(R.id.collegename);
        }

        /*@Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), getPosition() + ": " + mColleges.get(getPosition()).getName(), Toast.LENGTH_SHORT).show();
            mColInst = mColleges.get(getPosition());
        }*/
    }

    public CollegeAdapter(List<College> listcolleges) {
        mColleges = listcolleges;
        //mContext = context;
    }


    public College getPositionCollege(){
        return mColInst;
    }

    @Override
    public CollegeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mCallback = (CAInterface) context;

        View collegeView = inflater.inflate(R.layout.item_college, parent, false);
        ViewHolder viewHolder = new ViewHolder(collegeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CollegeAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        College col = mColleges.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(col.getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("asdf","here");
                mCallback.switchToThirdFrag(position);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mColleges.size();
    }

    public interface CAInterface {
        void switchToThirdFrag(int i);
    }
}
