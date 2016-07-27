package com.example.android.newsappproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by da7th on 7/27/2016.
 */
public class NewsCardAdapter extends ArrayAdapter<NewsCard> {

    public NewsCardAdapter(MainActivity context, ArrayList<NewsCard> resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ListItemView = convertView;
        if (ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        NewsCard currentNewsCard = getItem(position);

        TextView typeTextView = (TextView) ListItemView.findViewById(R.id.type_text_view);
        typeTextView.setText(currentNewsCard.getType());

        TextView titleTextView = (TextView) ListItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentNewsCard.getTitle());

        TextView dateTextView = (TextView) ListItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentNewsCard.getDate());

        return ListItemView;

    }
}
