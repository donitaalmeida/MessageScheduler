package com.bignerdranch.android.messagescheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by donita on 04-07-2016.
 */
public class MessageDbAdapter
{
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "MessageSchedule";
    private static final String SQLITE_TABLE = "MessageSchedule";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS MessageSchedule( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "toName VARCHAR, toNumber, message VARCHAR, datetime VARCHAR);";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public MessageDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public MessageDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public Cursor fetchAllMessageSchedule() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {
                "_id", "toName", "toNumber", "message", "datetime"},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
