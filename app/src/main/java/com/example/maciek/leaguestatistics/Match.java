package com.example.maciek.leaguestatistics;


import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Date;

/**
 * Created by Maciek on 2018-01-11.
 */

public class Match {
    private int gameId;
    //private String gameMode;
    //private int queueId;
    private String queueName;
    //private int mapId;
    private String mapName;
    //private int gameDuration;
    private String gameTime;

    //private boolean win;
    private String gameResult;
    private int goldEarned;
    private int totalMinionsKilled;

    private String[] summonersNames = new String[10];
    private String[] lanes = new String[10];
    private int[] championIds = new int[10];
    private int[] kills = new int[10];
    private int[] deaths = new int[10];
    private int[] assists = new int[10];
    private String[] highestAchievedSeasonTiers = new String[10];
    private Bitmap[] bitmaps = new Bitmap[10];

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Match(int gI, int qI, /*String gM,*/ int mI, int gD, boolean gR, int gE, int tMK, String[] sN, String[] l, int[] cI, int[] k, int[] d, int[] a, String[] hAST, Bitmap[] b) {
        this.gameId = gI;
        //this.gameMode = gM;
        this.queueName = getQueueName(qI);
        this.mapName = getMapName(mI);
        this.gameTime = getGameDuration(gD);
        this.gameResult = getResult(gR, gD);
        this.goldEarned = gE;
        this.totalMinionsKilled = tMK;
        this.summonersNames = sN;
        this.lanes = l;
        this.championIds = cI;
        this.kills = k;
        this.deaths = d;
        this.assists = a;
        this.highestAchievedSeasonTiers = hAST;
        this.bitmaps = b;
        //this.championId = cI;
        /*switch (r) {
            case "BOTTOM":
                this.role = "BOT";
        }*/
        //this.role = r;
        //this.date = getDate(t);
        //if (gR < 300) then gameResult = "remake"
    }

    public String getQueueName(int qI) {
        switch(qI) {
            case 400:
                return "Klasyczna draft";
            case 420:
                return "Rankingowa SOLO/DUO";
            case 430:
                return "Klasyczna w ciemno (5 vs 5)";
            case 440:
                return "Rankingowa FLEX (5 vs 5)";
            case 450:
                return "ARAM";
            case 460:
                return "Klasyczna w ciemno (3 vs 3)";
            case 470:
                return "Rankingowa FLEX (3 vs 3)";
            default:
                return "Niestandardowa";
        }
    }

    public String getMapName(int mI) {
        switch(mI) {
            case 3:
                return "The Proving Grounds";
            case 10:
                return "Twisted Treeline";
            case 11:
                return "Summoner's Rift";
            case 12:
                return "Howling Abyss";
            default:
                return "Nieznana mapa";
        }
    }

    public String getGameDuration(int gD) {
        int minutes = gD / 60;
        int seconds = gD % 60;
        return String.format("%dmin %ds", minutes, seconds);
    }

    public String getResult(boolean gR, int gD) {
        if (gD < 300)
            return "REMAKE";
        else if (gR)
            return "WYGRANA";
        return "PRZEGRANA";
    }
    //private String gameType;
    //private Time gameDuration;

    //private long timestamp;
    //private String date;
    //private champpionImage;



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


    /*
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
    */
}
