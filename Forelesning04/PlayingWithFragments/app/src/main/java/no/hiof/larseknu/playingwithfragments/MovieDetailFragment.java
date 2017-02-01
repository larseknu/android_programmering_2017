package no.hiof.larseknu.playingwithfragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import no.hiof.larseknu.playingwithfragments.dummy.MovieContent;

public class MovieDetailFragment extends Fragment {
    public final static String MOVIE_INDEX = "movieIndex";
    private static final int DEFAULT_MOVIE_INDEX = 1;

    private TextView mMovieDescriptionView;
    private ImageView mMoviePosterImageView;
    private int mMovieIndex;

    public static MovieDetailFragment newInstance(int movieIndex) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_INDEX, movieIndex);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieIndex = savedInstanceState == null? DEFAULT_MOVIE_INDEX : savedInstanceState.getInt(MOVIE_INDEX, DEFAULT_MOVIE_INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        mMovieDescriptionView = (TextView) fragmentView.findViewById(R.id.movieDescription);
        mMoviePosterImageView = (ImageView) fragmentView.findViewById(R.id.moviePosterImageView);

        setDisplayedDetail(mMovieIndex);

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(MOVIE_INDEX, mMovieIndex);
    }

    public void setDisplayedDetail(int movieDescriptionIndex) {
        mMovieIndex = movieDescriptionIndex;
        mMovieDescriptionView.setText(MovieContent.MOVIE_MAP.get(mMovieIndex).description);

        int drawableId = getResources().getIdentifier("poster" + mMovieIndex, "drawable", getActivity().getPackageName());
        Drawable imagePoster = getResources().getDrawable(drawableId);
        if (imagePoster != null)
            mMoviePosterImageView.setImageDrawable(imagePoster);
    }
}
