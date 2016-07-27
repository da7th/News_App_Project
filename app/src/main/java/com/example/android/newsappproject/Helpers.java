package com.example.android.newsappproject;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by da7th on 7/27/2016.
 */
public class Helpers {

    private static final String LOG_TAG = Helpers.class.getSimpleName();

    private Helpers() {
    }


    public static ArrayList<NewsCard> fetchNewsCardData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        ArrayList<NewsCard> newsCards = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return newsCards;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("Error:", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Error:", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<NewsCard> extractFeatureFromJson(String newsCardJSON) {
        if (TextUtils.isEmpty(newsCardJSON)) {
            return null;
        }

        ArrayList<NewsCard> newsCards = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(newsCardJSON);

            JSONObject responseJsonObject = baseJsonResponse.getJSONObject("response");

            JSONArray newsCardArray = responseJsonObject.getJSONArray("results");

            for (int i = 0; i < newsCardArray.length(); i++) {

                JSONObject currentNewsCard = newsCardArray.getJSONObject(i);

                String type = currentNewsCard.getString("type");

                String title = currentNewsCard.getString("webTitle");

                String date = currentNewsCard.getString("webPublicationDate");

                String url = currentNewsCard.getString("webUrl");

                NewsCard newsCard = new NewsCard(type, title, date, url);

                newsCards.add(newsCard);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return newsCards;
    }

}
