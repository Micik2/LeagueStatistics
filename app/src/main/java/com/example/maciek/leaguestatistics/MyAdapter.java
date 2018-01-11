package com.example.maciek.leaguestatistics;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Maciek on 2018-01-11.
 */

public class MyAdapter extends ArrayAdapter<String> {
    private Context context;
    private int layoutResourceId;
    private List<String> types;
    private List<String> times;
    private List<String> champImages;
    private List<String> champNames;
    private List<String> roles;
    private List<String> firstSpells;
    private List<String> secondSpells;
    private List<String> KDAs;

    public MyAdapter(Context c, int lRI, List<String> ty, List<String> ti, List<String> cI, List<String> cN, List<String> r, List<String> fS, List<String> sS, List<String> k) {
        this.context = c;
        this.layoutResourceId = lRI;
        this.types = ty;
        this.times = ti;
        this.champImages = cI;
        this.champNames = cN;
        this.roles = r;
        this.firstSpells = fS;
        this.secondSpells = sS;
        this.KDAs = k;
        //super(context, layoutResourceId, ty, ti, cI, cN, r, fS, sS, k);
    }
}
