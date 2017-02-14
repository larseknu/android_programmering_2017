package no.hiof.larseknu.playingwithfragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by larseknu on 31/01/2017.
 */

public class MovieDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        int id = getIntent().getIntExtra("movie_id", 1);

        FragmentManager fragmentManager = getFragmentManager();
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) fragmentManager.findFragmentById(R.id.detail);

        movieDetailFragment.setDisplayedDetail(id);
    }

}
