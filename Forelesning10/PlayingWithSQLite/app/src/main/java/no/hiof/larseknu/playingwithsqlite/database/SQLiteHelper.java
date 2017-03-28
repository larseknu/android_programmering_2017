package no.hiof.larseknu.playingwithsqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import no.hiof.larseknu.playingwithsqlite.database.ShowContract.*;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "show.db";
    private static final int DATABASE_VERSION = 2;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.v(SQLiteHelper.class.getName(), "Database " + DATABASE_NAME + " version " + DATABASE_VERSION + " created.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ShowEntry.CREATE_TABLE_SHOW);
        db.execSQL(EpisodeEntry.CREATE_TABLE_EPISODE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(SQLiteHelper.class.getName(), "Upgrading database from " + oldVersion + " to " + newVersion + ". All data are lost.");

        db.execSQL("DROP TABLE IF EXISTS " + ShowEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EpisodeEntry.TABLE_NAME);

        onCreate(db);
    }
}
