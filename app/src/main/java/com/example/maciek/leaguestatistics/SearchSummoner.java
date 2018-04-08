package com.example.maciek.leaguestatistics;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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
    //privateint  status = -1;
    private String status = "0";
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
    private int[] gameId = new int[10];
    //private int gameId;
    private int[] championIds = new int[10];
    private String[] championNames = new String[10];
    private String[] imagesNames = new String[10];
    private String fullImageUrl;
    private Bitmap[] bitmaps = new Bitmap[10];
    private int queueId;
    private boolean win;
    private int goldEarned = -1;
    private int totalMinionsKilled = -1;
    private int gameDuration = -1;
    private int mainParticipantId;
    //private API api = new API();

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
            //new API().execute(new String[]{summonerName});
                new API().execute(new String[]{summonerName, status});
            //accountIdTest.setText(api.getAccountId(summonerName));
            }
        });
    }
    public static class ImageHelper {
        public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }
    }

    private class API extends AsyncTask<String, Void, String> {
        //private final int accountId = 12345678;
        private final String APIkey = "";
        private String accountUrl = "https://eun1.api.riotgames.com/lol/summoner/v3/summoners/by-name/%s?api_key=" + APIkey;
        private String matchesUrl = "https://eun1.api.riotgames.com/lol/match/v3/matchlists/by-account/%d/recent?api_key=" + APIkey;
        private String matchUrl = "https://eun1.api.riotgames.com/lol/match/v3/matches/%d?api_key=" + APIkey;
        private String championsUrl = "https://eun1.api.riotgames.com/lol/static-data/v3/champions?locale=en_US&tags=image&dataById=true&api_key=" + APIkey;
        private String ddragonImageUrl = "https://ddragon.leagueoflegends.com/cdn/8.2.1/img/champion/";
        //private ProgressDialog progressDialog = new ProgressDialog(SearchSummoner.this);
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
        protected void onPreExecute() {/*
            progressDialog.setMessage("Poczekaj na pobranie statystyk...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    .this.cancel(true);
                }
            });*/
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            URL url = null;
            status = params[1];
            try {
                if (status.equals("3")) {
                    //Log.d(status, "JESTEM TUTAJ");
                    url = new URL(championsUrl);
                    status = "4";
                }
                else if (status.equals("2")) {
                    //Log.d(status, "JESTEM TUTAJ");
                    //if (amountMatches < 11) {
                        url = new URL(getMatchUrl(Long.parseLong(params[0])));
                        //amountMatches += 1;
                        //status = 3;
                    //}
                    //else
                    //    status = 4;
                    status = "3";
                }
                else if (status.equals("1")) {
                    //Log.d(status, "JESTEM TUTAJ");
                    url = new URL(getMatchesUrl(Integer.parseInt(params[0])));
                    status = "2";
                }
                else if (status.equals("0")) {
                    //Log.d(status, "JESTEM TUTAJ");
                    url = new URL(getAccountUrl(params[0]));
                    status = "1";
                }
                //url = new URL(getMatchesUrl(accountId));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpsURLConnection httpsURLConnection = null;
            InputStream inputStream = null;
            try {
                Log.d(status, "NULL POINTER I TAK DALEJ");
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


                if (status.equals("4")) {
                    //Log.d(status, "JESTEM TUTAJ");
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    JSONObject datas = jsonObject.getJSONObject("data");
                    for (int n = 0; n < 10; n++) {
                        JSONObject data = datas.getJSONObject(String.valueOf(championIds[n]));
                        JSONObject images = data.getJSONObject("image");
                        imagesNames[n] = images.getString("full");
                        URL fullImageUrl = new URL(ddragonImageUrl + imagesNames[n]);
                        bitmaps[n] = BitmapFactory.decodeStream(fullImageUrl.openConnection().getInputStream());
                        championNames[n] = data.getString("name");
                    }

                }
                else if (status.equals("3")) {
                    //Log.d(status, "JESTEM TUTAJ");
                    JSONArray jsonArray = jsonObject.getJSONArray("participantIdentities");
                    int partId = -1;
                    for (int j = 0; j < 10; j++) {
                        JSONObject participantId = jsonArray.getJSONObject(j);
                        JSONObject player = participantId.getJSONObject("player");
                        summonersNames[j] = player.getString("summonerName");
                        // numer porządkowy osoby (od 1 do 10)
                        if (summonersNames[j].equals(summonerName))
                            partId = participantId.getInt("participantId");
                    }
                    JSONArray participants = jsonObject.getJSONArray("participants");
                    //int kills = -1;
                    //int deaths = -1;
                    //int assists = -1;
                    //boolean win;
                    //int goldEarned = -1;
                    //int totalMinionsKilled = -1;
                    //int gameDuration = -1;
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
                            mainParticipantId = partId;
                        }
                    }
                    // CLASSIC lub RANKED
                    String gameMode = jsonObject.getString("gameMode");
                    // id mapy
                    mapId = jsonObject.getInt("mapId");
                    gameDuration = jsonObject.getInt("gameDuration");
                    queueId = jsonObject.getInt("queueId");
                }
                //JSONObject jsonObject = new JSONObject(result);
                else if (status.equals("2")) {
                    //Log.d(status, "JESTEM TUTAJ");
                    JSONArray jsonArray = jsonObject.getJSONArray("matches");
                    for (int i = 0; i < 10; i++) {
                        JSONObject singleMatch = jsonArray.getJSONObject(i);
                        gameId[i] = singleMatch.getInt("gameId");
                    }

                }
                else if (status.equals("1")) {
                    //Log.d(status, "JESTEM TUTAJ");
                    id = jsonObject.getInt("accountId");
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

            if (status.equals("4")) {
                matches.add(new Match(gameId[0], queueId, mapId, gameDuration, win, goldEarned, totalMinionsKilled, summonersNames, lanes, championIds, championNames, kills, deaths, assists, highestAchievedSeasonTiers, bitmaps, mainParticipantId));
                //}
                myAdapter = new MyAdapter(getApplicationContext(), matches);
                listView = (ListView) findViewById(R.id.listview);
                listView.setAdapter(myAdapter);
                this.cancel(true);
                //this.cancel(true);

                //new API().execute(new String[]{summonerName, status});
            }
            else if (status.equals("3")) {
                //Log.d(status, "JESTEM TUTAJ");
                //for (int m = 0; m < 10; m++) {
                    /*matches.add(new Match(gameId[0], queueId, mapId, gameDuration, win, goldEarned, totalMinionsKilled, summonersNames, lanes, championIds, championNames, kills, deaths, assists, highestAchievedSeasonTiers, bitmaps, mainParticipantId));
                //}
                myAdapter = new MyAdapter(getApplicationContext(), matches);
                listView = (ListView) findViewById(R.id.listview);
                listView.setAdapter(myAdapter);*/
                //progressDialog.dismiss();
                this.cancel(true);
                new API().execute(new String[]{summonerName, status});
            }
            else if (status.equals("2")) {
                //Log.d(status, "JESTEM TUTAJ");
                this.cancel(true);
                new API().execute(new String[]{Integer.toString(gameId[0]), status});
                //if (amountMatches == 10)
                //    status = 3;
            }
            else if (status.equals("1")) {
                //Log.d(status, "JESTEM TUTAJ");
                this.cancel(true);
                new API().execute(new String[]{Integer.toString(id), status});
            }
        }


    }

}
