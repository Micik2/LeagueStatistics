package com.example.maciek.leaguestatistics;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Maciek on 2017-11-30.
 */

public class SearchSummoner extends AppCompatActivity {
    private Button searchButton;
    private EditText searchText;
    private TextView accountIdTest;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);
        listView = (ListView) findViewById(R.id.listview);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String summonerName = searchText.getText().toString();
            new API().execute(new String[]{summonerName});
            //accountIdTest.setText(api.getAccountId(summonerName));
            }
        });
    }

    private class API extends AsyncTask<String, Void, String> {
        private final String APIkey = "RGAPI-eaf25ff0-bc1e-4116-ac6f-a05cc0f12403";
        private String accountUrl = "https://eun1.api.riotgames.com/lol/summoner/v3/summoners/by-name/%s?api_key=" + APIkey;
        private String matchesUrl = "https://eun1.api.riotgames.com/lol/match/v3/matchlists/by-account/%s/recent?api_key=" + APIkey;
        private ProgressDialog progressDialog = new ProgressDialog(SearchSummoner.this);

        public String getAccountUrl(String summonerName) {
            String fullAccountUrl = String.format(accountUrl, summonerName);
            return fullAccountUrl;
        }

        public String getMatchesUrl(String accountId) {
            String fullMatchesUrl = String.format(matchesUrl, accountId);
            return fullMatchesUrl;
        }

        /*public String getAccountId(String name) throws IOException, JSONException {
            //String  = getAccountUrl(name);
            //String json =
            StringBuilder stringBuilder = new StringBuilder();
            URL url = new URL(getAccountUrl(name));
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(result);
            String accountId = (String) jsonObject.get("accountId");
            return accountId;
        }*/

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Downloading current rotation...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    API.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            URL url = null;
            try {
                url = new URL(getAccountUrl(params[0]));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpsURLConnection httpsURLConnection = null;
            InputStream inputStream = null;
            try {
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            try {
                while ((line = bufferedReader.readLine()) != null)
                    stringBuilder.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject = null;
            Integer accountId = null;
            try {
                jsonObject = new JSONObject(result);

                accountId = (Integer) jsonObject.get("accountId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            accountIdTest = (TextView) findViewById(R.id.accountIdTest);
            accountIdTest.setText(Integer.toString(accountId));
            progressDialog.dismiss();
        }


    }

}
