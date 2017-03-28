package no.hiof.larseknu.playingwithsqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import no.hiof.larseknu.playingwithsqlite.R;
import no.hiof.larseknu.playingwithsqlite.model.Show;

public class ShowListAdapter extends ArrayAdapter<Show> {

	public ShowListAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
	    if (view == null) {
	        LayoutInflater viewInflater = LayoutInflater.from(getContext());
	        view = viewInflater.inflate(R.layout.show_list_item, null);
	    }

	    Show show = getItem(position);

	    if (show != null) {
	        TextView title = (TextView) view.findViewById(R.id.show_title);
	        TextView year = (TextView) view.findViewById(R.id.year);

	        if (title != null) {
	        	title.setText(show.getTitle());
	        }
	        if (year != null) {
	        	year.setText(show.getYear() + "");
	        }
	    }

	    return view;
	}
}
