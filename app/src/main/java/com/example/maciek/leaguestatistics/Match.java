package com.example.maciek.leaguestatistics;


import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Date;
import java.sql.Time;

/**
 * Created by Maciek on 2018-01-11.
 */

public class Match {
    private int[] kills = new int[10];
    private int[] deaths = new int[10];
    private int[] assists = new int[10];
    private int goldEarned;
    private int totalMinionsKilled;
    private int gameDuration;
    private String gameResult;
    private String[] summonersNames = new String[10];
    //private String gameType;
    //private Time gameDuration;
    private int championId;
    private String lane;
    //private long timestamp;
    //private String date;
    //private champpionImage;
    private String gameMode;
    private int mapId;
    private String highestAchievedSeasonTier;
    //private int spell1Id;
    //private int spell2Id;
    //private int kills;
    //private int deaths;
    //private int assists;

    /*
    public Match(String gT, Time gD, int cI, String r, int s1, int s2, int k, int d, int a) {
        this.gameType = gT;
        this.gameDuration = gD;
        this.championId = cI;
        this.role = r;
        this.spell1Id = s1;
        this.spell2Id = s2;
        this.kills = k;
        this.deaths = d;
        this.assists = a;
    }
    */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Match(int cI, String r, long t) {
        this.championId = cI;
        this.role = r;
        this.date = getDate(t);
        //if (gR < 300) then gameResult = "remake"
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getDate(long t) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    public String getChampionName(int cI) {
        String championName;

        return championName;
    }

}
