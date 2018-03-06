package com.example.a2018msivakum.collegechooser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 2018msivakum on 9/28/2017.
 */

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ViewHolder>{

    private List<College> mColleges, mFavorites;
    private Context mContext;
    private College mColInst, col;
    private Filesaver mFilesaver;

    private CAInterface mCallback;

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{

        public TextView nameTextView;
        public CheckBox favorite;

        public ViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.collegename);
            favorite = (CheckBox) view.findViewById(R.id.star);
        }
    }

    public CollegeAdapter(List<College> listcolleges) {
        mColleges = listcolleges;
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
    public void onBindViewHolder(final CollegeAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        col = mColleges.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(col.getName());

        mFavorites = ((MainActivity) mCallback).getFavorites();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("COLLEGEADAPT", position + " else");
                mCallback.switchToThirdFrag(position);
            }
        });

        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()) {
                    Log.i("COLLEGEADAPT", position + " favorited");
                    ((MainActivity) mCallback).addToFaves(mColleges.get(position));
                    Log.i("COLLEGEADAPT", mFavorites.get(mFavorites.size()-1).getName() + "");
                }
                else {
                    Log.i("COLLEGEADAPT", position + " removed from favorites");
                    ((MainActivity) mCallback).removeFromFaves(mColleges.get(position));
                    Log.i("COLLEGEADAPT", mFavorites.get(mFavorites.size()-1).getName() + "");
                }
                Log.i("COLLEGEADAPT", ((MainActivity) mCallback).getFavorites() + "");
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
