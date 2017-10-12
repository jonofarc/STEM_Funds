package com.example.jonathanmaldonado.stem_funds.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonathanmaldonado.stem_funds.InvestmentRecyclerViewAdapter;
import com.example.jonathanmaldonado.stem_funds.R;
import com.example.jonathanmaldonado.stem_funds.stem_funds.Stem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class MainActivity extends AppCompatActivity {



    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://iwg-prod-web-interview.azurewebsites.net/stem/v1/funds?";
    public int offset=0;
    public int limit =10;
    private OkHttpClient client;

    private TextView resultsNumberTV;
    private SeekBar simpleSeekBar;
    private EditText filterET;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Stem[] StemResults;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient.Builder().build();

        resultsNumberTV= (TextView) findViewById(R.id.tv_results_number);
        simpleSeekBar=(SeekBar)findViewById(R.id.simpleSeekBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        filterET = (EditText) findViewById(R.id.et_filter);



        setSeekerBarListener();
        setEditTextListener();

        connectAndGetApiData();


    }

    private void setEditTextListener() {
        filterET.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                dataFilter();

            }
        });
    }

    private void setSeekerBarListener() {
        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                limit=progressChangedValue;
                StringBuilder results= new StringBuilder();
                results.append("Number of results: ").append(String.valueOf(limit+1));
                resultsNumberTV.setText(results.toString());// we add 1 to correctly show results
                connectAndGetApiData();
            }
        });
    }


    public void connectAndGetApiData(){


        StringBuilder completeURL = new StringBuilder();
        completeURL.append(BASE_URL).append("offset=").append(String.valueOf(offset)).append("&limit=").append(String.valueOf(limit));




        Request request = new Request.Builder().url(completeURL.toString()).build();



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
                                StemResults = StemGson.fromJson(resp,Stem[].class);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub

                                        dataFilter();

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

    public void dataFilter(){

        List<Stem> resultsInvest= new ArrayList<>();

        for (int i=0; i<StemResults.length; i++){
            String str1=filterET.getText().toString();
            String str2=StemResults[i].getInvestmentName();

            if(!TextUtils.isEmpty(str1)){
                if(str2.toLowerCase().contains(str1.toLowerCase())){
                    resultsInvest.add(StemResults[i]);
                }
            }else{
                resultsInvest.add(StemResults[i]);
            }

        }

        final List<Stem> InvestNames= resultsInvest;



        setRecyclerView(InvestNames);
    }



    public void setRecyclerView(  List<Stem> results){



        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new InvestmentRecyclerViewAdapter(this, (ArrayList) results);
        mRecyclerView.setAdapter(mAdapter);
    }

}