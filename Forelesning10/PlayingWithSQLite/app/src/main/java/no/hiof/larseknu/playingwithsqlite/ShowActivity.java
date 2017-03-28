package no.hiof.larseknu.playingwithsqlite;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

import no.hiof.larseknu.playingwithsqlite.adapter.ShowListAdapter;
import no.hiof.larseknu.playingwithsqlite.database.ShowDataSource;
import no.hiof.larseknu.playingwithsqlite.model.Show;

public class ShowActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ShowDataSource showDataSource;
    private ShowListAdapter showListAdapter;
    private int showNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        showDataSource = new ShowDataSource(this);
        showDataSource.open();

        ListView listView = (ListView) findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Show show = showListAdapter.getItem(pos);
                //Toast.makeText(this, show.getId() + " " + show.getTitle(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(view.getContext(), EpisodeActivity.class);
                intent.putExtra(EpisodeActivity.SHOW_ID, show.getId());
                intent.putExtra(EpisodeActivity.SHOW_TITLE, show.getTitle());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteShowDialog(position);
                return true;
            }
        });

        List<Show> shows = showDataSource.getAllShows();

        showListAdapter = new ShowListAdapter(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(showListAdapter);
        showListAdapter.addAll(shows);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] showTitles = getResources().getStringArray(R.array.shows);
                Show show = showDataSource.createShow(showTitles[showNumber++%showTitles.length], new Random().nextInt(17)+2000, null);
                showDataSource.createEpisode(show.getId(), "Pilot", 1, 1, "Brilliant physicist roommates Leonard and Sheldon meet their new neighbor Penny, who begins showing them that as much as they know about science, they know little about actual living.");
                showDataSource.createEpisode(show.getId(), "The Big Bran Hypothesis", 2, 1, "Brilliant physicist roommates Leonard and Sheldon meet their new neighbor Penny, who begins showing them that as much as they know about science, they know little about actual living.");

                Log.v(ShowActivity.class.getName(), show.getTitle() + " " + show.getYear() + " " + show.getId());

                showListAdapter.add(show);
            }
        });
    }

    private void deleteShowDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete entry?")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ShowListAdapter adapter = showListAdapter;
                        Show show = adapter.getItem(position);
                        showDataSource.deleteShow(show);
                        adapter.remove(show);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onResume() {
        showDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        showDataSource.close();
        super.onPause();
    }

}
