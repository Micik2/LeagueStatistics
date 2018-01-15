package com.example.maciek.leaguestatistics;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciek on 2018-01-11.
 */

public class MyAdapter extends ArrayAdapter<Match> {
    private Context context;
    private int layoutResourceId;
    private List<Match> matchList;

    public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Match> objects) {
        super(context, resource, objects);
    }



    /*public MyAdapter(Context c, int lRI, List<Match> mL) {
        this.context = c;
        this.layoutResourceId = lRI;
        this.matchList = mL;
        //super(context, layoutResourceId, ty, ti, cI, cN, r, fS, sS, k);
    }*/
}
