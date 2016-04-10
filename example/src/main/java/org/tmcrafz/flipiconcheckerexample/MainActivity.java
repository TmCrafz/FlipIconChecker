package org.tmcrafz.flipiconcheckerexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.tmcrafz.flipiconchecker.FlipIconChecker;
import org.tmcrafz.flipiconcheckerexample.fragments.FragmentCities;
import org.tmcrafz.flipiconcheckerexample.fragments.FragmentColors;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Fragment m_actualFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "TAG: " + TAG);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Select first menu item of drawer
        navigationView.getMenu().performIdentifierAction(R.id.nav_example_1, 0);

        /*
        FlipIconChecker flipIconChecker = (FlipIconChecker) findViewById(R.id.flipChecker);
        View view = getLayoutInflater().inflate(R.layout.custom_front_color, flipIconChecker, false);
        flipIconChecker.setFrontView(view);
        */

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_example_1) {
            if (!(m_actualFragment instanceof FragmentColors)) {
                FragmentColors fragmentColors = FragmentColors.newInstance();
                m_actualFragment = fragmentColors;
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragmentColors, FragmentCities.TAG)
                        .commit();
            }
        }
        else if (id == R.id.nav_example_2) {
            if (!(m_actualFragment instanceof FragmentCities)) {
                FragmentCities fragmentCities = FragmentCities.newInstance();
                m_actualFragment = fragmentCities;
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragmentCities, FragmentCities.TAG)
                        .commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
