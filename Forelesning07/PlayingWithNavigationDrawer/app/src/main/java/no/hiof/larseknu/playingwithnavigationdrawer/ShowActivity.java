package no.hiof.larseknu.playingwithnavigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import no.hiof.larseknu.playingwithnavigationdrawer.fragment.NavigationDrawerFragment;

public class ShowActivity extends AppCompatActivity {

    Toolbar mToolbar;
    NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MovieActivity.class));
            }
        });

        setUpDrawer();
    }

    private void setUpDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment.setUpDrawer(drawerLayout, mToolbar, R.id.nav_shows);
    }

    @Override
    protected void onStart() {
        mNavigationDrawerFragment.updateCheckedItem(R.id.nav_shows);

        super.onStart();
    }
}
