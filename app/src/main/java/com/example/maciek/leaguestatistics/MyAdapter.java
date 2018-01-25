package com.example.maciek.leaguestatistics;

import android.content.Context;
import android.media.Image;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciek on 2018-01-11.
 */

public class MyAdapter extends ArrayAdapter<Match> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Match> matchList;
    private ImageView mainChampionImage;
    private TextView mainChampionName;
    private TextView kills;
    private TextView deaths;
    private TextView time;
    private TextView assists;
    private TextView gold;
    private TextView minions;
    private TextView result;
    private Match match;

    public MyAdapter(@NonNull Context context, @NonNull ArrayList<Match> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Match match = getItem(position);
        match = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_match_win, parent, false);

        mainChampionImage = convertView.findViewById(R.id.main_champion_image);
        mainChampionName = convertView.findViewById(R.id.main_champion_name);
        kills = convertView.findViewById(R.id.kills);
        deaths = convertView.findViewById(R.id.deaths);
        time = convertView.findViewById(R.id.time);
        assists = convertView.findViewById(R.id.assists);
        gold = convertView.findViewById(R.id.gold);
        minions = convertView.findViewById(R.id.minions);
        result = convertView.findViewById(R.id.result);

        int mainParticipantId = match.getMainParticipantId();
        mainChampionImage.setImageBitmap(match.getBitmaps()[mainParticipantId - 1]);
        mainChampionName.setText(String.valueOf(match.getChampionIds()[mainParticipantId - 1]));
        kills.setText(String.valueOf(match.getKills()[mainParticipantId - 1]));
        deaths.setText(String.valueOf(match.getDeaths()[mainParticipantId - 1]));
        assists.setText(String.valueOf(match.getAssists()[mainParticipantId - 1]));
        gold.setText(String.valueOf(match.getGoldEarned()));
        minions.setText(String.valueOf(match.getTotalMinionsKilled()));
        result.setText(match.getGameResult());

        //champion = convertView.findViewById(R.id.main_champion_name);
        //time = convertView.findViewById(R.id.time);
        //role = convertView.findViewById(R.id.role);

        //champion.setText();

        return convertView;
    }

    /*public MyAdapter(Context c, int lRI, List<Match> mL) {
        this.context = c;
        this.layoutResourceId = lRI;
        this.matchList = mL;
        //super(context, layoutResourceId, ty, ti, cI, cN, r, fS, sS, k);
    }*/
}
