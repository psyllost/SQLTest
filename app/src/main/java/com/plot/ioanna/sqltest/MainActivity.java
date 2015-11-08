package com.plot.ioanna.sqltest;

import android.app.ListActivity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
    private Cursor books;
    private DBCDatabase db;
    MainActivity mListActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListActivity=this;
        db = new DBCDatabase(this);
        books = db.getBooks(); // you would not typically call this on the main thread
        ListAdapter adapter =new SimpleCursorAdapter(this,
        android.R.layout.simple_list_item_1,
                books,
                new String[] { "book_id"},
                new int[] {android.R.id.text1});
        this.setListAdapter(adapter);

        mListActivity.getListView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        books.close();
        db.close();
    }
}
