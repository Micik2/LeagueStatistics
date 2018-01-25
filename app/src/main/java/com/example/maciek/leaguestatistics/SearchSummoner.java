package com.example.maciek.leaguestatistics;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Maciek on 2017-11-30.
 */

public class SearchSummoner extends AppCompatActivity {
    private Button searchButton;
    private EditText searchText;
    private TextView accountIdTest;
    private ListView listView;
    private int id;
    private int status = -1;
    private int amountMatches = 0;
    private ArrayList<Match> matches = new ArrayList<Match>();
    private MyAdapter myAdapter;
    private String[] summonersNames = new String[10];
    private String summonerName;
    private int[] kills = new int[10];
    private int[] deaths = new int[10];
    private int[] assists = new int[10];
    private String[] highestAchievedSeasonTiers = new String[10];
    private String[] lanes = new String[10];
    //private String lane;
    private int mapId;
    //private int[] gameId = new int[10];
    private int gameId;
    private int[] championIds = new int[10];
    private String[] imagesNames = new String[10];
    private String fullImageUrl;
    private Bitmap[] bitmaps = new Bitmap[10];

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
            summonerName = searchText.getText().toString();
            new API().execute(new String[]{summonerName});
            //accountIdTest.setText(api.getAccountId(summonerName));
            }
        });
    }

    private class API extends AsyncTask<String, Void, String> {
        private final int accountId = 31084320;
        private final String APIkey = "RGAPI-a7328b83-555c-47e9-9a69-0a87c5804064";
        private String accountUrl = "https://eun1.api.riotgames.com/lol/summoner/v3/summoners/by-name/%s?api_key=" + APIkey;
        private String matchesUrl = "https://eun1.api.riotgames.com/lol/match/v3/matchlists/by-account/%d/recent?api_key=" + APIkey;
        private String matchUrl = "https://eun1.api.riotgames.com/lol/match/v3/matches/%d?api_key=" + APIkey;
        private String championsUrl = "https://eun1.api.riotgames.com/lol/static-data/v3/champions?locale=en_US&tags=image&dataById=true&api_key=" + APIkey;
        private String ddragonImageUrl = "https://ddragon.leagueoflegends.com/cdn/8.2.1/img/champion/";
        private ProgressDialog progressDialog = new ProgressDialog(SearchSummoner.this);
        private ArrayList<Match> matches = new ArrayList<Match>();

        public String getAccountUrl(String sN) {
            String fullAccountUrl = String.format(accountUrl, sN);
            return fullAccountUrl;
        }

        public String getMatchesUrl(int aI) {
            String fullMatchesUrl = String.format(matchesUrl, aI);
            return fullMatchesUrl;
        }

        public String getMatchUrl(long gI) {
            String fullMatchUrl = String.format(matchUrl, gI);
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
                    if (amountMatches < 11) {
                        url = new URL(getMatchUrl(Long.parseLong(params[0])));
                        //amountMatches += 1;
                        //status = 3;
                    }
                    else
                        status = 4;
                }
                else if (status == 1) {
                    url = new URL(getMatchesUrl(Integer.parseInt(params[0])));
                    status = 2;
                }
                else if (status == 0) {
                    url = new URL(getAccountUrl(params[0]));
                    status = 1;
                }
                else if (status == -1) {
                    url = new URL(championsUrl);
                    status = 0;
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
            //int gameId = 0;
            try {
                JSONObject jsonObject = new JSONObject(result);

                if (status == 3) {
                    JSONArray jsonArray = jsonObject.getJSONArray("participantIdentities");
                    for (int j = 0; j < 10; j++) {
                        JSONObject participantId = jsonArray.getJSONObject(j);
                        JSONObject player = participantId.getJSONObject("player");
                        summonersNames[j] = player.getString("summonerName");
                        // numer porządkowy osoby (od 1 do 10)
                        int partId = -1;
                        if (summonersNames[j].equals(summonerName))
                            partId = participantId.getInt("participantId");
                        JSONArray participants = jsonObject.getJSONArray("participants");
                        //int kills = -1;
                        //int deaths = -1;
                        //int assists = -1;
                        boolean win;
                        int goldEarned = -1;
                        int totalMinionsKilled = -1;
                        int gameDuration = -1;
                        //String highestAchievedSeasonTier = "";
                        for (int k = 0; k < 10; k++) {
                            JSONObject object = participants.getJSONObject(k);
                            JSONObject stats = object.getJSONObject("stats");
                            kills[k] = stats.getInt("kills");
                            deaths[k] = stats.getInt("deaths");
                            assists[k] = stats.getInt("assists");
                            int pI = object.getInt("participantId");
                            highestAchievedSeasonTiers[k] = object.getString("highestAchievedSeasonTier");
                            championIds[k] = object.getInt("championId");
                            JSONObject timeline = object.getJSONObject("timeline");
                            lanes[k] = timeline.getString("lane");
                            if (partId == pI) {
                                win = stats.getBoolean("win");
                                goldEarned = stats.getInt("goldEarned");
                                totalMinionsKilled = stats.getInt("totalMinionsKilled");
                            }
                        }
                        // CLASSIC lub RANKED
                        String gameMode = jsonObject.getString("gameMode");
                        // id mapy
                        mapId = jsonObject.getInt("mapId");
                        gameDuration = jsonObject.getInt("gameDuration");
                    }
                }
                //JSONObject jsonObject = new JSONObject(result);
                else if (status == 2) {
                    JSONArray jsonArray = jsonObject.getJSONArray("matches");
                    for (int i = 0; i < 10; i++) {
                        JSONObject singleMatch = jsonArray.getJSONObject(i);
                        gameId = singleMatch.getInt("gameId");
                    }

                }
                else if (status == 1) {
                    id = jsonObject.getInt("accountId");
                }
                else if (status == 0) {
                    JSONObject datas = jsonObject.getJSONObject("data");
                    for (int n = 0; n < 10; n++) {
                        JSONObject data = datas.getJSONObject(String.valueOf(championIds[n]));
                        JSONObject images = data.getJSONObject("image");
                        imagesNames[n] = images.getString("full");
                        URL fullImageUrl = new URL(ddragonImageUrl + imagesNames[n]);
                        bitmaps[n] = BitmapFactory.decodeStream(fullImageUrl.openConnection().getInputStream());
                    }

                }


                //JSONArray jsonArray = jsonObject.getJSONArray("matches");
                //JSONObject singleMatchJSON = jsonArray.getJSONObject(0);
                //Match singleMatch = new Match(singleMatchJSON.getInt("champion"), singleMatchJSON.getString("role"), singleMatchJSON.getLong("date"));
                //matches.add(singleMatch);
                //myAdapter = new MyAdapter(getApplicationContext(), matches);
                //listView = (ListView) findViewById(R.id.listview);
                //listView.setAdapter(myAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (status == 3) {
                for (int m = 0; m < 10; m++) {
                    matches.add(new Match())
                }
                progressDialog.dismiss();
            }
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
