package org.tmcrafz.flipiconcheckerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView m_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        m_recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        m_recyclerView.setLayoutManager(layoutManager);

        String[] cities = getResources().getStringArray(R.array.cities);
        AdapterRecyclerView adapter = new AdapterRecyclerView(Arrays.asList(cities));
        m_recyclerView.setAdapter(adapter);
    }
}
