package com.plot.ioanna.sqltest;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structure.Book;

public class MainActivity extends Activity {
    private Cursor books;
    private DBCDatabase db;
    private BookFromXml xmlResponse;
    private GoogleApiRequest imageLink;
    TextView textView;
    MainActivity mListActivity;
    Book book;
    //this is a list of books that the user has read
    List<String> randomBooks= Arrays.asList("870970-basis:27069703", "870970-basis:51039629", "870970-basis:50653463");
    HashMap<String, String> bookInfo = new HashMap<String, String>();
    //this is the class that the user belongs to
    String userClass="3";
    Bitmap thumbnail;
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
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap((Bitmap) response);
        }

        @Override
        protected Bitmap doInBackground(Object[] params) {
            Bitmap bitmap = null;
            ArrayList books = new ArrayList();
            books = db.getBooks(userClass, randomBooks);
            List<Map<String, String>> response = new ArrayList<Map<String, String>>();
            for (int j = 0; j < books.size(); j++) {
                response.add(xmlResponse.consumeWebService((String) books.get(j)));
                book = xmlResponse.createBookFromXMLResponse(response.get(j));
                Log.i("BOOK", (String) books.get(j));
            }
            for (Map<String, String> map : response) {
                Log.i("XMLRESPONSE", map.get("isbn"));
                if (map.get("isbn") != null) {
                    thumbnail = GoogleApiRequest.getInstance().getImage(map.get("isbn"));
                }
            }
            if (thumbnail != null) {
                return thumbnail;
            }
            else {
                return null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        books.close();
        db.close();
    }
}
