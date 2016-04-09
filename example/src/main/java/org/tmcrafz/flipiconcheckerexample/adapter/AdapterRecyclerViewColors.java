package org.tmcrafz.flipiconcheckerexample.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.tmcrafz.flipiconchecker.FlipIconChecker;
import org.tmcrafz.flipiconcheckerexample.R;

import java.util.List;

public class AdapterRecyclerViewColors extends RecyclerView.Adapter<AdapterRecyclerViewColors.ColorsViewHolder> {

    private final static String TAG = AdapterRecyclerViewColors.class.getSimpleName();

    // The name of the color and the resource id of the color
    private List<Pair<String, Integer>> m_colors;
    private SparseBooleanArray m_selections;
    private Context m_context;

    public AdapterRecyclerViewColors(List<Pair<String, Integer>> colors, Context context) {
        m_colors = colors;
        m_selections = new SparseBooleanArray();
        m_context = context;
    }

    @Override
    public ColorsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_recyclerview_colors, viewGroup, false);

        return new ColorsViewHolder(itemView, m_selections);
    }

    @Override
    public void onBindViewHolder(ColorsViewHolder viewHolder, int i) {
        String colorName = m_colors.get(i).first;
        int colorResource = m_colors.get(i).second;

        viewHolder.m_txtColorName.setText(colorName);

        /* We want to show the first letter of the color name in the flipIconChecker.
         * The custom front layout which we have defined before and have added to the flipIconChecker
         * has a TextView for this purpose.
         */
        String firstLetter = colorName.substring(0, 1);
        // Get the front view (which includes the TextView)
        View frontView = viewHolder.m_flipIconChecker.getFrontView();
        // Now we can get the TextView of the view
        TextView txt_cityFirstChar = (TextView) frontView.findViewById(R.id.txt_cityFirstLetter);
        // Add the first letter to the TextView. Now it is shown at the front of the FlipIconChecker
        txt_cityFirstChar.setText(firstLetter);

        // Load the parent layout of our custom front layout
        RelativeLayout custom_parent_layout = (RelativeLayout) frontView.findViewById(R.id.custom_round_parent_view);
        GradientDrawable bgShape = (GradientDrawable) custom_parent_layout.getBackground();
        // Add background of shape to color
        bgShape.setColor(ContextCompat.getColor(m_context, colorResource));

        viewHolder.m_flipIconChecker.setChecked(m_selections.get(i));
    }

    @Override
    public int getItemCount() {
        return m_colors.size();
    }

    public static class ColorsViewHolder extends RecyclerView.ViewHolder
            implements FlipIconChecker.OnFlipIconCheckerClickedListener {

        private FlipIconChecker m_flipIconChecker;
        private TextView m_txtColorName;

        private SparseBooleanArray m_selections;

        public ColorsViewHolder(final View itemView, SparseBooleanArray selections) {
            super(itemView);
            m_flipIconChecker = (FlipIconChecker) itemView.findViewById(R.id.flipChecker);
            m_txtColorName = (TextView) itemView.findViewById(R.id.txt_cityName);
            m_selections = selections;
            m_flipIconChecker.setOnFlipIconCheckerClickedListener(this);
        }

        @Override
        public void onFlipIconCheckerClicked() {
            m_selections.put(getAdapterPosition(), m_flipIconChecker.isChecked());
        }
    }


}
