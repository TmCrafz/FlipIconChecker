package org.tmcrafz.flipiconcheckerexample;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tmcrafz.flipiconchecker.FlipIconChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.CitiesViewHolder> {

    private final static String TAG = AdapterRecyclerView.class.getSimpleName();

    private List<String> m_cities;
    private SparseBooleanArray m_selections;

    public AdapterRecyclerView(List<String> cities) {
        m_cities = cities;
        m_selections = new SparseBooleanArray();
    }

    @Override
    public CitiesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_entry, viewGroup, false);

        return new CitiesViewHolder(itemView, m_selections);
    }

    @Override
    public void onBindViewHolder(CitiesViewHolder viewHolder, int i) {
        String cityName = m_cities.get(i);

        viewHolder.m_txtCityName.setText(cityName);
        if (m_selections.get(i)) {
            viewHolder.m_flipIconChecker.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        return m_cities.size();
    }

    public static class CitiesViewHolder extends RecyclerView.ViewHolder
            implements FlipIconChecker.OnFlipIconCheckerClickedListener {

        private FlipIconChecker m_flipIconChecker;
        private TextView m_txtCityName;

        private SparseBooleanArray m_selections;


        public CitiesViewHolder(final View itemView, SparseBooleanArray selections) {
            super(itemView);
            m_flipIconChecker = (FlipIconChecker) itemView.findViewById(R.id.flipChecker);
            m_txtCityName = (TextView) itemView.findViewById(R.id.txt_cityName);
            m_selections = selections;
        }

        @Override
        public void onFlipIconCheckerClicked() {
            m_selections.put(getAdapterPosition(), m_flipIconChecker.isChecked());
        }
    }


}

