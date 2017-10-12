package com.example.jonathanmaldonado.stem_funds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
   // public static final String BASE_URL = "http://iwg-prod-web-interview.azurewebsites.net/stem/v1/funds?offset=0&limit=100";
   public static final String BASE_URL = "http://iwg-prod-web-interview.azurewebsites.net/stem/v1/funds?";
    public int offset=0;
    public int limit =10;
    OkHttpClient client;
    TextView respTV;
    SeekBar simpleSeekBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient.Builder().build();
        respTV= (TextView) findViewById(R.id.resp_tv);
        simpleSeekBar=(SeekBar)findViewById(R.id.simpleSeekBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        setSeekerBAr();

       // recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
       // recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        connectAndGetApiData();


    }

    private void setSeekerBAr() {
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
                limit=progressChangedValue;// we remove 1 to correctly display results
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
                                //Stem2 StemResults = StemGson.fromJson(resp,Stem2.class);
                                Stem[] StemResults = StemGson.fromJson(resp,Stem[].class);
                                //channelSearchEnum[] enums = gson.fromJson(yourJson, channelSearchEnum[].class);

                                final String results=StemResults.toString();


                                Log.d(TAG, "onResponse: 1" +StemResults);
                                List<Stem> resultsInvestNames= new ArrayList<>();

                                for (int i=0; i<StemResults.length; i++){
                                    resultsInvestNames.add(StemResults[i]);
                                }
                                // used to be able to modify view
                                final List<Stem> InvestNames= resultsInvestNames;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        respTV.setText(InvestNames.toString());
                                        ParseResults(InvestNames);
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

    private void ParseResults(List<Stem> list) {
        List<String> results = new ArrayList<>();
       // List<String> ids = new ArrayList<>();
        for(Stem item:list){

            results.add(item.getInvestmentName().toString()+"\n");
           // ids.add(item.getId().toString());
        }
        respTV.setText(results.toString());
        //setRecyclerView(results);
        setRecyclerView(list);
    }

    public void setRecyclerView(  List<Stem> results){



        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new InvestmentRecyclerViewAdapter(this, (ArrayList) results);
        mRecyclerView.setAdapter(mAdapter);
    }

}
