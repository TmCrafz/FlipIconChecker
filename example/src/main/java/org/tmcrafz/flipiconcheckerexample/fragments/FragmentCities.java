package org.tmcrafz.flipiconcheckerexample.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tmcrafz.flipiconcheckerexample.R;
import org.tmcrafz.flipiconcheckerexample.adapter.AdapterRecyclerViewCity;

import java.util.Arrays;

public class FragmentCities extends Fragment {

    public static final String TAG = FragmentCities.class.getSimpleName();

    private RecyclerView m_recyclerView;

    public FragmentCities() {
        // Required empty public constructor
    }

    public static FragmentCities newInstance() {
        FragmentCities fragment = new FragmentCities();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cities, container, false);

        m_recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCities);
        m_recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        m_recyclerView.setLayoutManager(layoutManager);

        String[] cities = getResources().getStringArray(R.array.cities);
        AdapterRecyclerViewCity adapter = new AdapterRecyclerViewCity(Arrays.asList(cities));
        m_recyclerView.setAdapter(adapter);

        return view;
    }

}
