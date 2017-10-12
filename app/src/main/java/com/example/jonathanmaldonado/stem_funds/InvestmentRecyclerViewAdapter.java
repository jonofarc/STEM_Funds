package com.example.jonathanmaldonado.stem_funds;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jonathanmaldonado.stem_funds.stem_funds.InvestmentNames;
import com.example.jonathanmaldonado.stem_funds.stem_funds.Stem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.ArrayList;

/**
 * Created by Jonathan Maldonado on 9/9/2017.
 */

public class InvestmentRecyclerViewAdapter extends RecyclerView.Adapter <InvestmentRecyclerViewAdapter.ViewHolder> {

    public static final String RECYCLER_VIEW_EXTRA="com.example.jonathanmaldonado.stem_funds.RECYCLER_VIEW_EXTRA";
    private ArrayList<Stem> mDataset;
    private Context mContext;






    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.investmentName_tv.setText( mDataset.get(position).getInvestmentName().toString());
        holder.id_tv.setText(mDataset.get(position).getId().toString());




    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView investmentName_tv;
        TextView id_tv;
        CardView myCardView;


        public ViewHolder(View v) {
            super(v);

            investmentName_tv= (TextView) v.findViewById(R.id.tv_investment_name);
            id_tv= (TextView) v.findViewById(R.id.tv_id);

            myCardView= (CardView) v.findViewById(R.id.cardview);

            final View.OnClickListener mOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext , FundDetailActivity.class);
                    intent.putExtra(RECYCLER_VIEW_EXTRA, id_tv.getText().toString());


                    mContext.startActivity(intent);

                }
            };
            v.setOnClickListener(mOnClickListener);

        }

    }


    public InvestmentRecyclerViewAdapter(Context context, ArrayList<Stem> mData) {


        mContext =context;

        mDataset = mData;
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
