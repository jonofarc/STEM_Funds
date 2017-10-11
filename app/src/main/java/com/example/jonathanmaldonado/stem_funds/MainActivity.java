package com.example.jonathanmaldonado.stem_funds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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
    public static final String BASE_URL = "http://iwg-prod-web-interview.azurewebsites.net/stem/v1/funds";
    OkHttpClient client;
    TextView respTV;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient.Builder().build();
        respTV= (TextView) findViewById(R.id.resp_tv);


       // recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
       // recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        connectAndGetApiData();


    }

    // This method create an instance of Retrofit
    // set the base url
    public void connectAndGetApiData(){

        Request request = new Request.Builder().url(BASE_URL).build();

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
        for(Stem item:list){

            results.add(item.getInvestmentName().toString()+"\n");

        }
        respTV.setText(results.toString());
        setRecyclerView(results);
    }

    public void setRecyclerView(  List<String> results){

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new InvestmentRecyclerViewAdapter(this, (ArrayList) results);
        mRecyclerView.setAdapter(mAdapter);
    }

}
