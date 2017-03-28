package no.hiof.larseknu.playingwithsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import no.hiof.larseknu.playingwithsqlite.database.ShowContract.*;
import no.hiof.larseknu.playingwithsqlite.model.Episode;
import no.hiof.larseknu.playingwithsqlite.model.Show;


public class ShowDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allShowColumns = { ShowEntry.COLUMN_ID,
            ShowEntry.COLUMN_TITLE,
            ShowEntry.COLUMN_YEAR,
            ShowEntry.COLUMN_IMDB_ID};

    private String[] allEpisodeColumns = { EpisodeEntry.COLUMN_ID,
            EpisodeEntry.COLUMN_SHOW_ID,
            EpisodeEntry.COLUMN_TITLE,
            EpisodeEntry.COLUMN_EPISODE,
            EpisodeEntry.COLUMN_SEASON,
            EpisodeEntry.COLUMN_OVERVIEW};

    public ShowDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public Show createShow(String title, int year, String imdbId) {
        ContentValues values = new ContentValues();
        values.put(ShowEntry.COLUMN_TITLE, title);
        values.put(ShowEntry.COLUMN_YEAR, year);
        values.put(ShowEntry.COLUMN_IMDB_ID, imdbId);

        long insertId = database.insert(ShowEntry.TABLE_NAME, null, values);

        return getShow(insertId);
    }

    public Show getShow(long id) {
        Cursor cursor = database.query(ShowEntry.TABLE_NAME,
                allShowColumns,
                ShowEntry.COLUMN_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        Show show = cursorToShow(cursor);
        cursor.close();

        return show;
    }

    public List<Show> getAllShows() {
        List<Show> shows = new ArrayList<>();

        Cursor cursor = database.query(ShowEntry.TABLE_NAME,
                allShowColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Show show = cursorToShow(cursor);
            shows.add(show);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();

        return shows;
    }

    public void deleteShow(Show show) {
        long id = show.getId();

        List<Episode> episodes = getAllEpisodes(show.getId());

        for (Episode episode : episodes) {
            deleteEpisode(episode);
        }

        database.delete(ShowEntry.TABLE_NAME, ShowEntry.COLUMN_ID + " = " + id, null);
    }

    private Show cursorToShow(Cursor cursor) {
        Show show = new Show();
        show.setId(cursor.getInt(0));
        show.setTitle(cursor.getString(1));
        show.setYear(cursor.getInt(2));
        show.setImdbId(cursor.getString(3));

        return show;
    }

    public Episode createEpisode(long showId, String title, int episode, int season, String aired) {
        ContentValues values = new ContentValues();
        values.put(EpisodeEntry.COLUMN_SHOW_ID, showId);
        values.put(EpisodeEntry.COLUMN_TITLE, title);
        values.put(EpisodeEntry.COLUMN_EPISODE, episode);
        values.put(EpisodeEntry.COLUMN_SEASON, season);
        values.put(EpisodeEntry.COLUMN_OVERVIEW, aired);

        long insertId = database.insert(EpisodeEntry.TABLE_NAME, null, values);

        return getEpisode(insertId);
    }

    public Episode getEpisode(long id) {
        Cursor cursor = database.query(EpisodeEntry.TABLE_NAME,
                allEpisodeColumns,
                EpisodeEntry.COLUMN_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        Episode episode = cursorToEpisode(cursor);
        cursor.close();

        return episode;
    }

    public List<Episode> getAllEpisodes(int showId) {
        List<Episode> episodes = new ArrayList<>();
        Cursor cursor = database.query(EpisodeEntry.TABLE_NAME,
                allEpisodeColumns,
                EpisodeEntry.COLUMN_SHOW_ID + "=" + showId,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Episode episode = cursorToEpisode(cursor);
            episodes.add(episode);
            cursor.moveToNext();
        }

        cursor.close();
        return episodes;
    }

    public void deleteEpisode(Episode episode) {
        long id = episode.getId();
        database.delete(EpisodeEntry.TABLE_NAME, EpisodeEntry.COLUMN_ID + " = " + id, null);
    }

    private Episode cursorToEpisode(Cursor cursor) {
        Episode episode = new Episode();
        episode.setId(cursor.getInt(0));
        episode.setShowId(cursor.getInt(1));
        episode.setTitle(cursor.getString(2));
        episode.setSeason(cursor.getInt(3));
        episode.setSeason(cursor.getInt(4));
        episode.setOverview(cursor.getString(5));
        return episode;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }



}
