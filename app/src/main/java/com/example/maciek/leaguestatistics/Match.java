package com.example.maciek.leaguestatistics;


import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Maciek on 2018-01-11.
 */

public class Match {
    //private String gameType;
    //private Time gameDuration;
    private int championId;
    private String role;
    //private long timestamp;
    private String date;
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
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getDate(long t) {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getDefault();
        calendar.setTimeInMillis(t * 1000);
        calendar.add(Calendar.MILLISECOND, timeZone.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = (Date) calendar.getTime();
        return simpleDateFormat.format(date);
    }
}
