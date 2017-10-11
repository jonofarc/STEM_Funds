package com.example.jonathanmaldonado.stem_funds;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by Jonathan Maldonado on 9/9/2017.
 */

public class InvestmentRecyclerViewAdapter extends RecyclerView.Adapter <InvestmentRecyclerViewAdapter.ViewHolder> {


    private ArrayList mDataset;
    private Context mContext;



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.investmentName_tv.setText(mDataset.get(position).toString());




    }


    public static int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {



        TextView investmentName_tv;
        CardView myCardView;


        public ViewHolder(View v) {
            super(v);

            investmentName_tv= (TextView) v.findViewById(R.id.tv_investment_name);

            myCardView= (CardView) v.findViewById(R.id.cardview);



        }

    }


    public InvestmentRecyclerViewAdapter(Context context, ArrayList daysData) {

        mContext =context;

        mDataset = daysData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {


        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_item, parent, false);



        ViewHolder vh = new ViewHolder(v);

        return vh;
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}
