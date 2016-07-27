package com.example.android.newsappproject;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
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

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<ArrayList<NewsCard>> {

    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";
    private NewsCardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);
        ArrayList<NewsCard> NewsCards = new ArrayList<NewsCard>();
        mAdapter = new NewsCardAdapter(this, NewsCards);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                NewsCard currentNewsCard = mAdapter.getItem(i);
                Uri newsCardUri = Uri.parse(currentNewsCard.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsCardUri);
                startActivity(websiteIntent);
            }
        });

        if (checkConnectivity()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);

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
    public void onLoadFinished(Loader<ArrayList<NewsCard>> loader, ArrayList<NewsCard> newsCards) {
        mAdapter.clear();
        if (newsCards != null && !newsCards.isEmpty()) {
            mAdapter.addAll(newsCards);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsCard>> loader) {
        mAdapter.clear();
    }
}
