package no.hiof.larseknu.playingwithsqlite;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import no.hiof.larseknu.playingwithsqlite.adapter.EpisodeListAdapter;
import no.hiof.larseknu.playingwithsqlite.database.ShowDataSource;
import no.hiof.larseknu.playingwithsqlite.model.Episode;

public class EpisodeActivity extends AppCompatActivity {
    public static String SHOW_ID = "show id";
    public static String SHOW_TITLE = "show title";

    private ShowDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        dataSource = new ShowDataSource(this);
        dataSource.open();

        Intent intent = getIntent();
        int showId = intent.getIntExtra(SHOW_ID, -1);

        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Season 1");

        setTitle(intent.getStringExtra(SHOW_TITLE));

        List<Episode> episodes = dataSource.getAllEpisodes(showId);

        if (showId != -1) {
            EpisodeListAdapter adapter = new EpisodeListAdapter(this, android.R.layout.simple_list_item_1);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(adapter);

            if(!episodes.isEmpty())
                adapter.addAll(episodes);
        }
        else
            header.setText("No episodes found");
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}
