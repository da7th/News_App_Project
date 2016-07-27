package com.example.android.newsappproject;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsCard>> {

    private static final String GUARDIAN_REQUEST_URL =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
    private NewsCardAdapter mAdapter;
    private ListView listView = (ListView) findViewById(R.id.list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkConnectivity()) {


            ArrayList<NewsCard> NewsCards = new ArrayList<>();

            mAdapter = new NewsCardAdapter(this, NewsCards);

            listView.setAdapter(mAdapter);

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(1, null, this);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    NewsCard currentNewsCard = mAdapter.getItem(i);
                    Uri newsCardUri = Uri.parse(currentNewsCard.getUrl());
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsCardUri);
                    startActivity(websiteIntent);
                }
            });

        } else {
            Log.e("Connectivity", "The app cannot detect an Internet Connection");
            Toast connectivityToast = Toast.makeText(this, "The app cannot detect an Internet Connection.", Toast.LENGTH_SHORT);
            connectivityToast.show();
        }
    }


    public Boolean checkConnectivity() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    @Override
    public Loader<ArrayList<NewsCard>> onCreateLoader(int i, Bundle bundle) {
        return new NewsCardLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsCard>> loader, ArrayList<NewsCard> o) {


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsCard>> loader) {
        mAdapter.clear();
    }


}
