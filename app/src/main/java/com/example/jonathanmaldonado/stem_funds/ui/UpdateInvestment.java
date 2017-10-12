package com.example.jonathanmaldonado.stem_funds.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jonathanmaldonado.stem_funds.R;
import com.google.gson.JsonParseException;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UpdateInvestment extends AppCompatActivity {

    public static final String UPDATE_INVESTMENT_ACTIVITY_ID_EXTRA = "com.example.jonathanmaldonado.stem_funds.UPDATE_INVESTMENT_ACTIVITY_ID_EXTRA";
    public static final String UPDATE_INVESTMENT_ACTIVITY_INVESTMENT_NAME_EXTRA = "com.example.jonathanmaldonado.stem_funds.UPDATE_INVESTMENT_ACTIVITY_INVESTMENT_NAME_EXTRA";

    private String currentID;
    private String currentAgency;
    private String currentSubagency;
    private String currentDescription;
    private static final String TAG = FundDetailActivity.class.getSimpleName();
    public static final String BASE_URL = "http://iwg-prod-web-interview.azurewebsites.net/stem/v1/funds";
    private EditText investmentNameET;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_investment);
        investmentNameET = (EditText) findViewById(R.id.investmentName_update_et);

    }

    public void putRequestWithHeaderAndBody(String url, String jsonBody) throws IOException {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody);
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

                        if (response.isSuccessful()) {

                            String resp = response.body().string();
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startFundDetailActivity();
                                    }


                                });

                            } catch (JsonParseException e) {
                                e.printStackTrace();
                            }

                            Log.d(TAG, "onResponse resp:  " + resp);

                        } else {
                            Log.d(TAG, "onResponse: Application Error");
                        }

                    }
                }
        );
    }


    public void updateInvestmentName(View view) {

        Intent intent = getIntent();
        currentID = intent.getStringExtra(FundDetailActivity.FUND_DETAIL_ACTIVITY_VIEW_ID_EXTRA);
        currentAgency = intent.getStringExtra(FundDetailActivity.FUND_DETAIL_ACTIVITY_VIEW_AGENCY_EXTRA);
        currentSubagency = intent.getStringExtra(FundDetailActivity.FUND_DETAIL_ACTIVITY_VIEW_SUBAGENCY_EXTRA);
        currentDescription = intent.getStringExtra(FundDetailActivity.FUND_DETAIL_ACTIVITY_VIEW_DESCRIPTION_EXTRA);

        if (intent != null && !TextUtils.isEmpty(currentID)) {


            String myJsonString = "{\n" +
                    "  \"Id\": \"" + currentID.toString() + "\",\n" +
                    "  \"InvestmentName\": \"" + investmentNameET.getText().toString() + "\",\n" +
                    "  \"Agency\": \"" + currentAgency.toString() + "\",\n" +
                    "  \"Subagency\": \"" + currentSubagency.toString() + "\",\n" +
                    "  \"BriefDescription\": \"" + currentDescription.toString() + "\",\n" +
                    "}";


            try {

                putRequestWithHeaderAndBody(BASE_URL + "/" + currentID, myJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //connectAndSetApiData();
        } else {
            Toast.makeText(this, R.string.failed_request, Toast.LENGTH_SHORT).show();
        }

    }

    public void startFundDetailActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(UPDATE_INVESTMENT_ACTIVITY_ID_EXTRA, currentID.toString());
        intent.putExtra(UPDATE_INVESTMENT_ACTIVITY_INVESTMENT_NAME_EXTRA, investmentNameET.getText().toString());
        startActivity(intent);
    }
}
