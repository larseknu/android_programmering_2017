package no.hiof.larseknu.playingwithsqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import no.hiof.larseknu.playingwithsqlite.R;
import no.hiof.larseknu.playingwithsqlite.model.Episode;

public class EpisodeListAdapter extends ArrayAdapter<Episode> {
	
	public EpisodeListAdapter(Context context, int textViewResourceId) {
	    super(context, textViewResourceId);
	}

	public EpisodeListAdapter(Context context, int resource, List<Episode> episodes) {
	    super(context, resource, episodes);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {

	    if (view == null) {
	        LayoutInflater viewInflater = LayoutInflater.from(getContext());
	        view = viewInflater.inflate(R.layout.episode_list_item, null);
	    }

	    Episode episodes = getItem(position);

	    if (episodes != null) {

	        TextView title = (TextView) view.findViewById(R.id.episode_title);
	        TextView overview = (TextView) view.findViewById(R.id.overview);

	        if (title != null) {
	        	title.setText(episodes.getTitle());
	        }
	        if (overview != null) {
	        	overview.setText(episodes.getOverview());
	        }
	    }

	    return view;
	}
}
