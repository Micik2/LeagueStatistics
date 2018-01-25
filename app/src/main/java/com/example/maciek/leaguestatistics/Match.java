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

    private int mainParticipantId;
    //private boolean win;
    private String gameResult;
    private int goldEarned;
    private int totalMinionsKilled;

    private String[] summonersNames = new String[10];
    private String[] lanes = new String[10];
    private int[] championIds = new int[10];
    private String[] championNames = new String[10];
    private int[] kills = new int[10];
    private int[] deaths = new int[10];
    private int[] assists = new int[10];
    private String[] highestAchievedSeasonTiers = new String[10];
    private Bitmap[] bitmaps = new Bitmap[10];

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Match(int gI, int qI, /*String gM,*/ int mI, int gD, boolean gR, int gE, int tMK, String[] sN, String[] l, int[] cI, String[] cN, int[] k, int[] d, int[] a, String[] hAST, Bitmap[] b, int mPI) {
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
        this.championNames = cN;
        this.kills = k;
        this.deaths = d;
        this.assists = a;
        this.highestAchievedSeasonTiers = getDivisionsImages(hAST);
        this.bitmaps = b;
        this.mainParticipantId = mPI;
        //this.championId = cI;
        /*switch (r) {
            case "BOTTOM":
                this.role = "BOT";
        }*/
        //this.role = r;
        //this.date = getDate(t);
        //if (gR < 300) then gameResult = "remake"
    }

    public int getGameId() {
        return gameId;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getMapName() {
        return mapName;
    }

    public String getGameTime() {
        return gameTime;
    }

    public String getGameResult() {
        return gameResult;
    }

    public int getGoldEarned() {
        return goldEarned;
    }

    public int getTotalMinionsKilled() {
        return totalMinionsKilled;
    }

    public String[] getSummonersNames() {
        return summonersNames;
    }

    public String[] getLanes() {
        return lanes;
    }

    public int[] getChampionIds() {
        return championIds;
    }

    public String[] getChampionNames() {
        return championNames;
    }

    public int[] getKills() {
        return kills;
    }

    public int[] getDeaths() {
        return deaths;
    }

    public int[] getAssists() {
        return assists;
    }

    public String[] getHighestAchievedSeasonTiers() {
        return highestAchievedSeasonTiers;
    }

    public Bitmap[] getBitmaps() {
        return bitmaps;
    }

    public int getMainParticipantId() {
        return mainParticipantId;
    }

    public String getQueueName(int qI) {
        switch (qI) {
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
        switch (mI) {
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

    public String[] getDivisionsImages(String[] hAST) {
        for (int i = 0; i < 10; i++) {
            switch (hAST[i]) {
                case "BRONZE":
                    hAST[i] = "bronze.png";
                    break;
                case "SILVER":
                    hAST[i] = "silver.png";
                    break;
                case "GOLD":
                    hAST[i] = "gold.png";
                    break;
                case "PLATINUM":
                    hAST[i] = "platinum.png";
                    break;
                case "DIAMOND":
                    hAST[i] = "diamond.png";
                    break;
                case "MASTER":
                    hAST[i] = "master.png";
                    break;
                case "CHALLENGER":
                    hAST[i] = "challenger.png";
                    break;
                default:
                    hAST[i] = "provisional.png";
                    break;
            }
        }
        return hAST;
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
