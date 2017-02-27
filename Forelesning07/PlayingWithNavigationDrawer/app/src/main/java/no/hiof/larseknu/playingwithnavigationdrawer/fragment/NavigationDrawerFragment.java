package no.hiof.larseknu.playingwithnavigationdrawer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import no.hiof.larseknu.playingwithnavigationdrawer.ListActivity;
import no.hiof.larseknu.playingwithnavigationdrawer.ShowActivity;
import no.hiof.larseknu.playingwithnavigationdrawer.MovieActivity;
import no.hiof.larseknu.playingwithnavigationdrawer.R;

/**
 * Created by larseknu on 27.02.2017.
 */

public class NavigationDrawerFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mNavigationView = (NavigationView) view.findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        return view;
    }

    public void setUpDrawer(DrawerLayout drawerLayout, Toolbar toolbar, int menuItemId) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {};

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mNavigationView.setCheckedItem(menuItemId);
    }

    public void updateCheckedItem(int menuItemId) {
        mNavigationView.setCheckedItem(menuItemId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_shows:
                Intent mainIntent = new Intent(getActivity(), ShowActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainIntent);
                break;
            case R.id.nav_movies:
                Intent movieIntent = new Intent(getActivity(), MovieActivity.class);
                movieIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(movieIntent);
                break;
            case R.id.nav_lists:
                Intent listIntent = new Intent(getActivity(), ListActivity.class);
                listIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(listIntent);
                break;
            case R.id.nav_settings:
                Toast.makeText(getActivity(), "Opening settings...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_help_feedback:
                Toast.makeText(getActivity(), "No help needed", Toast.LENGTH_SHORT).show();
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
