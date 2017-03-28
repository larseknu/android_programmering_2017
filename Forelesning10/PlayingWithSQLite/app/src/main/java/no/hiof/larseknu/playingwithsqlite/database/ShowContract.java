package no.hiof.larseknu.playingwithsqlite.database;

import android.provider.BaseColumns;

public final class ShowContract {

    public static class ShowEntry implements BaseColumns {
        public static final String TABLE_NAME = "show";

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "name";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_IMDB_ID = "imdb_id";

        public static final String CREATE_TABLE_SHOW = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_YEAR + " INTEGER, " +
                COLUMN_IMDB_ID + " TEXT" + ");";
    }

    public static class EpisodeEntry implements BaseColumns {
        public static final String TABLE_NAME = "episode";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_SHOW_ID = "show_id";
        public static final String COLUMN_TITLE = "name";
        public static final String COLUMN_EPISODE = "episode";
        public static final String COLUMN_SEASON = "season";
        public static final String COLUMN_OVERVIEW = "overview";

        public static final String CREATE_TABLE_EPISODE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_SHOW_ID + " INTEGER NOT NULL, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_EPISODE + " INTEGER NOT NULL, " +
                        COLUMN_SEASON + " INTEGER NOT NULL, " +
                        COLUMN_OVERVIEW + " TEXT, " +
                        "FOREIGN KEY("+ EpisodeEntry.COLUMN_SHOW_ID + ") REFERENCES " +
                        ShowEntry.TABLE_NAME +
                        "(" + ShowEntry._ID +") " + ");";
    }

}
