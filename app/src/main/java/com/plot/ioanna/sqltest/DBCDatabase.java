package com.plot.ioanna.sqltest;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.common.base.Joiner;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ioanna on 11/8/2015.
 */
public class DBCDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "dbcdatabase.db";
    private static final int DATABASE_VERSION = 2;

    public DBCDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getBooks(String userClass, List<String> hasRead) {
        String hasReadString = "";
        for (String s : hasRead)
        {
            hasReadString += '"'+s+'"' + ",";
        }
        hasReadString = hasReadString.substring(0,hasReadString.length()-1);
        Cursor c = getReadableDatabase().
                rawQuery("select 0 _id, book_id from Books where user_class LIKE '%" +userClass+ "%'" +
                        " AND book_id NOT IN ("+hasReadString+")",null);
        Log.i("cursor","  "+ c.getCount());
        c.moveToFirst();
        return c;
    }

}
