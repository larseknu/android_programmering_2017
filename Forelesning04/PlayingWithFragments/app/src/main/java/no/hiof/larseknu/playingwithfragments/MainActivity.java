package no.hiof.larseknu.playingwithfragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements MovieListFragment.OnFragmentInteractionListener {

    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mTwoPane = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_happysad:
                startSeparateActivity();
                return true;
            case R.id.action_button_handling:
                startButtonHandlingActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSeparateActivity() {
        Intent intent = new Intent(this, HappySadActivity.class);
        startActivity(intent);
    }

    private void startButtonHandlingActivity() {
        Intent intent = new Intent(this, ButtonHandlingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(int id) {
        //Toast.makeText(this, "Item number: " + id, Toast.LENGTH_SHORT).show();


        if (mTwoPane) {
            FragmentManager fragmentManager = getFragmentManager();
            MovieDetailFragment movieDetailFragment = (MovieDetailFragment) fragmentManager.findFragmentById(R.id.detail);

            movieDetailFragment.setDisplayedDetail(id);
        }
        else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra("movie_id", id);
            startActivity(intent);
        }
    }
}
