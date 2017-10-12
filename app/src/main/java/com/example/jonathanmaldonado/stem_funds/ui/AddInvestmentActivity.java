package com.example.jonathanmaldonado.stem_funds.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddInvestmentActivity extends AppCompatActivity {

    private static final String TAG = FundDetailActivity.class.getSimpleName();
    public static final String BASE_URL = "http://iwg-prod-web-interview.azurewebsites.net/stem/v1/funds/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    EditText investmentNameET;
    EditText agencyET;
    EditText subagencyET;
    EditText briefDescriptionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_investment);

        investmentNameET = (EditText) findViewById(R.id.investmentName_et);
        agencyET = (EditText) findViewById(R.id.agency_et);
        subagencyET = (EditText) findViewById(R.id.subagency_et);
        briefDescriptionET = (EditText) findViewById(R.id.briefDescription_et);


    }

    public void PostItem(String url, String json) throws IOException {
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

                        if (response.isSuccessful()) {


                            String resp = response.body().string();
                            try {


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Toast.makeText(AddInvestmentActivity.this, "New Investment Created", Toast.LENGTH_SHORT).show();
                                        startMainActivity();

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

    public void postInvestment(View view) {
        if (!investmentNameET.getText().toString().isEmpty() && !agencyET.getText().toString().isEmpty() && !subagencyET.getText().toString().isEmpty() && !briefDescriptionET.getText().toString().isEmpty()) {

            String myJsonString = "{\n" +
                    "  \"InvestmentName\": \"" + investmentNameET.getText().toString() + "\",\n" +
                    "  \"Agency\": \"" + agencyET.getText().toString() + "\",\n" +
                    "  \"Subagency\": \"" + subagencyET.getText().toString() + "\",\n" +
                    "  \"BriefDescription\": \"" + briefDescriptionET.getText().toString() + "\",\n" +
                    "}";


            try {
                PostItem(BASE_URL, myJsonString);

            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(this, R.string.lbl_no_empty_fields, Toast.LENGTH_SHORT).show();
        }


    }

    public void startMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
