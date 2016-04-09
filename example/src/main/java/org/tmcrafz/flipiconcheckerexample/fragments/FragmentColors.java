package org.tmcrafz.flipiconcheckerexample.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tmcrafz.flipiconcheckerexample.R;
import org.tmcrafz.flipiconcheckerexample.adapter.AdapterRecyclerViewCity;
import org.tmcrafz.flipiconcheckerexample.adapter.AdapterRecyclerViewColors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentColors extends Fragment {

    public static final String TAG = FragmentColors.class.getSimpleName();

    private RecyclerView m_recyclerView;

    public FragmentColors() {

    }

    public static FragmentColors newInstance() {
        FragmentColors fragment = new FragmentColors();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_colors, container, false);


        m_recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewColors);
        m_recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        m_recyclerView.setLayoutManager(layoutManager);


        // Load color names and resources
        //String[] colorNames = getResources().getStringArray(R.array.colors_names);
        //int[] colorResources = getResources().getIntArray(R.array.color_resources);

        List<Pair<String, Integer>> colors = new ArrayList<Pair<String, Integer>>();
        colors.add(new Pair<String, Integer>(getString(R.string.red), R.color.red));
        colors.add(new Pair<String, Integer>(getString(R.string.pink), R.color.pink));
        colors.add(new Pair<String, Integer>(getString(R.string.purple), R.color.purple));
        colors.add(new Pair<String, Integer>(getString(R.string.deep_purple), R.color.deep_purple));
        colors.add(new Pair<String, Integer>(getString(R.string.indigo), R.color.indigo));
        colors.add(new Pair<String, Integer>(getString(R.string.blue), R.color.blue));
        colors.add(new Pair<String, Integer>(getString(R.string.light_blue), R.color.light_blue));
        colors.add(new Pair<String, Integer>(getString(R.string.cyan), R.color.cyan));
        colors.add(new Pair<String, Integer>(getString(R.string.teal), R.color.teal));
        colors.add(new Pair<String, Integer>(getString(R.string.green), R.color.green));
        colors.add(new Pair<String, Integer>(getString(R.string.light_green), R.color.light_green));
        colors.add(new Pair<String, Integer>(getString(R.string.lime), R.color.lime));
        colors.add(new Pair<String, Integer>(getString(R.string.yellow), R.color.yellow));
        colors.add(new Pair<String, Integer>(getString(R.string.amber), R.color.amber));
        colors.add(new Pair<String, Integer>(getString(R.string.orange), R.color.orange));
        colors.add(new Pair<String, Integer>(getString(R.string.deep_orange), R.color.deep_orange));

        // Add name and resources to list
        /*
        for (int i = 0; i != colorResources.length; i++) {
            Log.d(TAG, "Color resource: " + colorResources[i] + " Name: " + colorNames[i]);
            colors.add(new Pair<String, Integer>(colorNames[i], colorResources[i]));
        }
        */
        AdapterRecyclerViewColors adapter = new AdapterRecyclerViewColors(colors, getContext());
        m_recyclerView.setAdapter(adapter);

        return view;
    }

}
