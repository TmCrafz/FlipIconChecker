package org.tmcrafz.flipiconcheckerexample.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tmcrafz.flipiconchecker.FlipIconChecker;
import org.tmcrafz.flipiconcheckerexample.R;

import java.util.List;

public class AdapterRecyclerViewCity extends RecyclerView.Adapter<AdapterRecyclerViewCity.CitiesViewHolder> {

    private final static String TAG = AdapterRecyclerViewCity.class.getSimpleName();

    private List<String> m_cities;
    private SparseBooleanArray m_selections;

    public AdapterRecyclerViewCity(List<String> cities) {
        m_cities = cities;
        m_selections = new SparseBooleanArray();
    }

    @Override
    public CitiesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_recyclerview_city, viewGroup, false);

        return new CitiesViewHolder(itemView, m_selections);
    }

    @Override
    public void onBindViewHolder(CitiesViewHolder viewHolder, int i) {
        String cityName = m_cities.get(i);

        viewHolder.m_txtCityName.setText(cityName);

        /* We want to show the first letter of the city name in the flipIconChecker.
         * The custom front layout which we have defined before and have added to the flipIconChecker
         * has a TextView for this purpose.
         */
        String firstLetter = cityName.substring(0, 1);
        // Get the front view (which includes the TextView)
        View frontView = viewHolder.m_flipIconChecker.getFrontView();
        // Now we can get the TextView of the view
        TextView txt_cityFirstChar = (TextView) frontView.findViewById(R.id.txt_cityFirstLetter);
        // Add the first letter to the TextView. Now it is shown at the front of the FlipIconChecker
        txt_cityFirstChar.setText(firstLetter);

        viewHolder.m_flipIconChecker.setChecked(m_selections.get(i));
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
            m_flipIconChecker.setOnFlipIconCheckerClickedListener(this);
        }

        @Override
        public void onFlipIconCheckerClicked() {
            m_selections.put(getAdapterPosition(), m_flipIconChecker.isChecked());
        }
    }


}
