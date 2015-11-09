package com.plot.ioanna.sqltest;

import android.app.Activity;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {
    private Cursor books;
    private DBCDatabase db;
    private BookFromXml xmlResponse;
    TextView textView;
    MainActivity mListActivity;
    List<String> randomBooks= Arrays.asList("870970-basis:27069703", "870970-basis:51039629", "870970-basis:50653463");
    String userClass="2";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListActivity=this;
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        db = new DBCDatabase(this);
        xmlResponse = new BookFromXml(this);
        DatabaseTask task = new DatabaseTask();
        task.execute();

    }
    private class DatabaseTask extends AsyncTask {

        @Override
        protected void onPostExecute(Object response) {
            super.onPostExecute(response);
        }

        @Override
        protected String[] doInBackground(Object[] params) {
            ArrayList books = db.getBooks(userClass, randomBooks);
            String[] response = new String[0];
            for (int j = 0; j < books.size(); j++) {
                response = xmlResponse.consumeWebService((String) books.get(j));
                Log.i("BOOK", (String) books.get(j));
            }
            Log.i("XMLRESPONSE", response[0]);
            return response;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        books.close();
        db.close();
    }
}
