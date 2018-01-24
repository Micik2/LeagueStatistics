package com.example.maciek.leaguestatistics;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> d290120363ec1a505046aaa063921d7cb31fb996

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Maciek on 2017-11-30.
 */

public class SearchSummoner extends AppCompatActivity {
    private Button searchButton;
    private EditText searchText;
    private TextView accountIdTest;
    private ListView listView;
<<<<<<< HEAD
    private int id;
    private int status = 0;
    private int amountMatches = 0;
=======
    private ArrayList<Match> matches = new ArrayList<Match>();
    private MyAdapter myAdapter;
>>>>>>> d290120363ec1a505046aaa063921d7cb31fb996

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
        private final int accountId = 31084320;
<<<<<<< HEAD
        private final String APIkey = "RGAPI-a7328b83-555c-47e9-9a69-0a87c5804064";
=======
        private final String APIkey = "RGAPI-24434203-a0b0-44ab-850c-9b1d90305e4c";
>>>>>>> d290120363ec1a505046aaa063921d7cb31fb996
        private String accountUrl = "https://eun1.api.riotgames.com/lol/summoner/v3/summoners/by-name/%s?api_key=" + APIkey;
        private String matchesUrl = "https://eun1.api.riotgames.com/lol/match/v3/matchlists/by-account/%d/recent?api_key=" + APIkey;
        private String matchUrl = "https://eun1.api.riotgames.com/lol/match/v3/matches/%d?api_key=" + APIkey;
        private ProgressDialog progressDialog = new ProgressDialog(SearchSummoner.this);
        private ArrayList<Match> matches = new ArrayList<Match>();

        public String getAccountUrl(String summonerName) {
            String fullAccountUrl = String.format(accountUrl, summonerName);
            return fullAccountUrl;
        }

        public String getMatchesUrl(int accountId) {
            String fullMatchesUrl = String.format(matchesUrl, accountId);
            return fullMatchesUrl;
        }

        public String getMatchUrl(long gameId) {
            String fullMatchUrl = String.format(matchUrl, gameId);
            return fullMatchUrl;
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
            progressDialog.setMessage("Poczekaj na pobranie statystyk...");
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
                if (status == 2) {
                    if (amountMatches < 10) {
                        url = new URL(getMatchUrl(Long.parseLong(params[0])));
                        amountMatches += 1;
                        status = 3;
                    }
                }
                else if (status == 1) {
                    url = new URL(getMatchesUrl(Integer.parseInt(params[0])));
                    status = 2;
                }
                else if (status == 0) {
                    url = new URL(getAccountUrl(params[0]));
                    status = 1;
                }
                //url = new URL(getMatchesUrl(accountId));
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

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String result) {
            /*
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
            */
            int gameId = 0;
            try {
                JSONObject jsonObject = new JSONObject(result);
<<<<<<< HEAD
                if (status == 3) {

                }
                //JSONObject jsonObject = new JSONObject(result);
                else if (status == 2) {
                    JSONArray jsonArray = jsonObject.getJSONArray("matches");
                    for (int i = 0; i < 10; i++) {
                        JSONObject singleMatch = jsonArray.getJSONObject(i);
                        String lane = singleMatch.getString("lane");
                        gameId = singleMatch.getInt("gameId");
                        int champion = singleMatch.getInt("champion");
                    }

                }
                else if (status == 1) {
                    id = jsonObject.getInt("accountId");
                }


=======
                JSONArray jsonArray = jsonObject.getJSONArray("matches");
                JSONObject singleMatchJSON = jsonArray.getJSONObject(0);
                Match singleMatch = new Match(singleMatchJSON.getInt("champion"), singleMatchJSON.getString("role"), singleMatchJSON.getLong("date"));
                matches.add(singleMatch);
                myAdapter = new MyAdapter(getApplicationContext(), matches);
                listView = (ListView) findViewById(R.id.listview);
                listView.setAdapter(myAdapter);
>>>>>>> d290120363ec1a505046aaa063921d7cb31fb996
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status == 3)
                progressDialog.dismiss();
            else if (status == 2) {
                new API().execute(new String[]{Integer.toString(gameId)});
                //if (amountMatches == 10)
                //    status = 3;
            }
            else if (status == 1) {
                new API().execute(new String[]{Integer.toString(id)});
            }
        }


    }

}
