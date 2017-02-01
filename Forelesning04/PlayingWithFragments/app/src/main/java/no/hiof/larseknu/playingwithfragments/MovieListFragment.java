package no.hiof.larseknu.playingwithfragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import no.hiof.larseknu.playingwithfragments.dummy.MovieContent;

public class MovieListFragment extends Fragment implements ListView.OnItemClickListener {
    private OnFragmentInteractionListener mListener;

    private ListView mListView;
    private ListAdapter mAdapter;

    public MovieListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.movie_list_item, R.id.label, MovieContent.MOVIES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelector(R.drawable.movie_list_selector);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            mListView.setItemChecked(position, true);
            view.setSelected(true);
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(MovieContent.MOVIES.get(position).id);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int id);
    }
}
