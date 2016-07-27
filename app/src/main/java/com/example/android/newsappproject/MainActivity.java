package com.example.android.newsappproject;

import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private ListView listView = (ListView) findViewById(R.id.list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkConnectivity()) {


            populateListView(listView);

        } else {
            Log.e("Connectivity", "The app cannot detect an Internet Connection");
            Toast connectivityToast = Toast.makeText(this, "The app cannot detect an Internet Connection.", Toast.LENGTH_SHORT);
            connectivityToast.show();
        }
    }


    public void populateListView(ListView newsCardListView) {

        final ArrayList<NewsCard> NewsCards = new ArrayList<>();

        //get newscards from JSON

        final NewsCardAdapter adapter = new NewsCardAdapter(this, NewsCards);

        newsCardListView.setAdapter(adapter);

        newsCardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                NewsCard currentNewsCard = adapter.getItem(i);
                Uri newsCardUri = Uri.parse(currentNewsCard.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsCardUri);
                startActivity(websiteIntent);
            }
        });
    }

    public Boolean checkConnectivity() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }







}
