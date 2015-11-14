package com.plot.ioanna.sqltest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Ioanna on 11/11/2015.
 */
public class GoogleApiRequest {

    public GoogleApiRequest(MainActivity mainActivity) {
    }

    public String getImageLink(String isbn)  {
        String apiUrlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        JSONArray jsonBook = getRequest(apiUrlString);
        JSONArray json2;
        JSONObject imageLink = null;
        String thumbnail="";
        try {
            //json2 = jsonBook.getJSONArray("items");
            //Log.i("JSONRESPONSE", String.valueOf(json2));
            if (jsonBook!=null) {
                for (int i = 0; i < jsonBook.length(); i++) {
                    JSONObject object3 = jsonBook.getJSONObject(i);
                    Log.i("object3", String.valueOf(object3));
                    imageLink = object3.getJSONObject("volumeInfo").getJSONObject("imageLinks");
                    Log.i("imagelink", String.valueOf(imageLink));
                }
                thumbnail = (String) imageLink.get("smallThumbnail");
                Log.i("thumbnail", thumbnail);
            }
            return thumbnail;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("jsonfail", "jsonfail");
            return null;
        }

    }
    public JSONArray getRequest(String urlString){
        JSONArray json2;
        try{
            HttpURLConnection connection = null;
            // Build Connection.
            try{
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000); // 5 seconds
                connection.setConnectTimeout(5000); // 5 seconds
            } catch (MalformedURLException e) {
                // Impossible: The only two URLs used in the app are taken from string resources.
                e.printStackTrace();
            } catch (ProtocolException e) {
                // Impossible: "GET" is a perfectly valid request method.
                e.printStackTrace();
            }
            int responseCode = connection.getResponseCode();
            if(responseCode != 200){
                Log.w(getClass().getName(), "GoogleBooksAPI request failed. Response Code: " + responseCode);
                connection.disconnect();
                return null;
            }

            // Read data from response.
            StringBuilder builder = new StringBuilder();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = responseReader.readLine();
            while (line != null){
                builder.append(line);
                line = responseReader.readLine();
            }
            String responseString = builder.toString();
            Log.d(getClass().getName(), "Response String: " + responseString);
            JSONObject responseJson = new JSONObject(responseString);
            json2 = responseJson.getJSONArray("items");
            // Close connection and return response code.
            connection.disconnect();
            return json2;
        } catch (SocketTimeoutException e) {
            Log.w(getClass().getName(), "Connection timed out. Returning null");
            return null;
        } catch(IOException e){
            Log.d(getClass().getName(), "IOException when connecting to Google Books API.");
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.d(getClass().getName(), "JSONException when connecting to Google Books API.");
            e.printStackTrace();
            return null;
        }
    }
}
