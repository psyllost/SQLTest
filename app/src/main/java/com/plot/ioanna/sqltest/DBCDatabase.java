package com.plot.ioanna.sqltest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Ioanna on 11/8/2015.
 */
public class DBCDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "dbcdatabase.db";
    private static final int DATABASE_VERSION = 2;

    public DBCDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getBooks() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"0 _id","book_id"};
        String sqlTables = "Books";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        Log.i("cursor","  "+ c.getCount());
        c.moveToFirst();
        return c;
    }
}
