package com.plot.ioanna.sqltest;

import android.app.ListActivity;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends ListActivity {
    private Cursor books;
    private DBCDatabase db;
    MainActivity mListActivity;
    List<String> randomBooks= Arrays.asList("870970-basis:27069703", "870970-basis:51039629", "870970-basis:50653463");
    String userClass="2";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListActivity=this;
        db = new DBCDatabase(this);
        DatabaseTask task = new DatabaseTask();
        task.execute(this);

    }
    private class DatabaseTask extends AsyncTask {

        @Override
        protected void onPostExecute(Object books) {
            super.onPostExecute(books);
            ListAdapter adapter =new SimpleCursorAdapter(mListActivity,
                    android.R.layout.simple_list_item_1,
                    (Cursor) books,
                    new String[]{"book_id"},
                    new int[] {android.R.id.text1});
            mListActivity.setListAdapter(adapter);

            mListActivity.getListView();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Cursor books  = db.getBooks(userClass, randomBooks);
            return books;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        books.close();
        db.close();
    }
}
