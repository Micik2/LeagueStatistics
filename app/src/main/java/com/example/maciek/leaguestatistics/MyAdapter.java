package com.example.maciek.leaguestatistics;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private TextView time;
    private TextView champion;
    private TextView role;

    public MyAdapter(@NonNull Context context, @NonNull ArrayList<Match> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Match match = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_match_win, parent, false);

        champion = convertView.findViewById(R.id.main_champion_name);
        time = convertView.findViewById(R.id.time);
        role = convertView.findViewById(R.id.role);

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
