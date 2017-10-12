package com.example.jonathanmaldonado.stem_funds.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonathanmaldonado.stem_funds.InvestmentRecyclerViewAdapter;
import com.example.jonathanmaldonado.stem_funds.R;
import com.example.jonathanmaldonado.stem_funds.stem_funds.InvestmentNames;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FundDetailActivity extends AppCompatActivity {

    public static final String FUND_DETAIL_ACTIVITY_VIEW_ID_EXTRA="com.example.jonathanmaldonado.stem_funds.FUND_DETAIL_ACTIVITY_VIEW_ID_EXTRA";
    public static final String FUND_DETAIL_ACTIVITY_VIEW_AGENCY_EXTRA="com.example.jonathanmaldonado.stem_funds.FUND_DETAIL_ACTIVITY_VIEW_AGENCY_EXTRA";
    public static final String FUND_DETAIL_ACTIVITY_VIEW_SUBAGENCY_EXTRA="com.example.jonathanmaldonado.stem_funds.FUND_DETAIL_ACTIVITY_VIEW_SUBAGENCY_EXTRA";
    public static final String FUND_DETAIL_ACTIVITY_VIEW_DESCRIPTION_EXTRA="com.example.jonathanmaldonado.stem_funds.FUND_DETAIL_ACTIVITY_VIEW_DESCRIPTION_EXTRA";

    private String message;
    OkHttpClient client;
    TextView investmentNameTV;
    TextView agencyTV;
    TextView subagencyTV;
    TextView descriptionTV;
    private static final String TAG = FundDetailActivity.class.getSimpleName();
    public static final String BASE_URL = "http://iwg-prod-web-interview.azurewebsites.net/stem/v1/funds/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_detail);

        investmentNameTV= (TextView) findViewById(R.id.investment_name_tv);
        agencyTV= (TextView) findViewById(R.id.agency_tv);
        subagencyTV= (TextView) findViewById(R.id.subagency_tv);
        descriptionTV= (TextView) findViewById(R.id.briefDescription_tv);
        client = new OkHttpClient.Builder().build();
        Intent intent = getIntent();
        message = intent.getStringExtra(InvestmentRecyclerViewAdapter.RECYCLER_VIEW_EXTRA);
        if(intent != null && !TextUtils.isEmpty(message)){



            investmentNameTV.setText("");
            agencyTV.setText("");
            subagencyTV.setText("");
            descriptionTV.setText("");
            connectAndGetApiData();
        }


    }

    public void connectAndGetApiData(){
        StringBuilder completeURL = new StringBuilder();
        completeURL.append(BASE_URL);
        completeURL.append(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Request request = new Request.Builder().url(completeURL.toString()).build();

        // thys is a Synchoronous request
        // this needs a separate thread
        // Response response = client.newCall(request).execute();

        client.newCall(request).enqueue(
                new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                        if(response.isSuccessful()){



                            String resp= response.body().string();
                            try {


                                Gson StemGson =new GsonBuilder().create();
                                final InvestmentNames StemResults = StemGson.fromJson(resp,InvestmentNames.class);





                                Log.d(TAG, "onResponse: 1" +StemResults);

                                // used to be able to modify view

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        investmentNameTV.setText(StemResults.getInvestmentName().toString());
                                        agencyTV.setText(StemResults.getAgency().toString());
                                        subagencyTV.setText(StemResults.getSubagency());
                                        descriptionTV.setText(StemResults.getBriefDescription());

                                    }


                                });

                            }catch (JsonParseException e){
                                e.printStackTrace();
                            }



                            Log.d(TAG, "onResponse resp:  "+ resp);
                        }else{
                            Log.d(TAG, "onResponse: Application Error");
                        }





                    }
                }
        );


    }

    public void editInvestmentName(View view) {
        Intent intent = new Intent(this , UpdateInvestment.class);
        intent.putExtra(FUND_DETAIL_ACTIVITY_VIEW_ID_EXTRA, message.toString());
        intent.putExtra(FUND_DETAIL_ACTIVITY_VIEW_AGENCY_EXTRA, agencyTV.getText().toString());
        intent.putExtra(FUND_DETAIL_ACTIVITY_VIEW_SUBAGENCY_EXTRA, subagencyTV.getText().toString());
        intent.putExtra(FUND_DETAIL_ACTIVITY_VIEW_DESCRIPTION_EXTRA, descriptionTV.getText().toString());
        startActivity(intent);
    }
}
