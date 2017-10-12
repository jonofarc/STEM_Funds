package com.example.jonathanmaldonado.stem_funds.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.jonathanmaldonado.stem_funds.InvestmentRecyclerViewAdapter;
import com.example.jonathanmaldonado.stem_funds.R;
import com.example.jonathanmaldonado.stem_funds.stem_funds.InvestmentNames;
import com.example.jonathanmaldonado.stem_funds.stem_funds.Stem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateInvestment extends AppCompatActivity {

    private String message;
    private static final String TAG = FundDetailActivity.class.getSimpleName();
    public static final String BASE_URL = "http://iwg-prod-web-interview.azurewebsites.net/stem/v1/funds";


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_investment);




        Intent intent = getIntent();
        message = intent.getStringExtra(FundDetailActivity.FUND_DETAIL_ACTIVITY_VIEW_EXTRA);
        if(intent != null && !TextUtils.isEmpty(message)){


            String myJsonString="{\n" +
                    "  \"Id\": 2,\n" +
                    "  \"InvestmentName\": \"sample string jan\",\n" +
                    "  \"Agency\": \"sample string jonathan Agency3\",\n" +
                    "  \"Subagency\": \"sample string jonathan sub agency\",\n" +
                    "  \"BriefDescription\": \"sample string 5\",\n" +
                    "  \"YearEstablished\": 6,\n" +
                    "  \"FundingFY2008\": 7.0,\n" +
                    "  \"FundingFY2009\": 8.0,\n" +
                    "  \"FundingFY2010\": 9.0,\n" +
                    "  \"MissionSpecificOrGeneralStem\": \"sample string 10\",\n" +
                    "  \"AgencyOrMissionRelatedNeeds\": \"sample string 11\",\n" +
                    "  \"PrimaryInvestmentObjective\": \"sample string 12\"\n" +
                    "}";


            try {
                //myPost(BASE_URL,myJsonString);
                putRequestWithHeaderAndBody(BASE_URL+"/"+message,myJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //connectAndSetApiData();
        }
    }

    public void putRequestWithHeaderAndBody(String url, String jsonBody)throws IOException {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .put(body) //PUT
                .build();

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


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub


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

    public void myPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


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


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub


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

    private void connectAndSetApiData() {

        StringBuilder completeURL = new StringBuilder();
        completeURL.append(BASE_URL);
        completeURL.append(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();







        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("InvestmentName", "JonInvestment")
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

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


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub


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
}
