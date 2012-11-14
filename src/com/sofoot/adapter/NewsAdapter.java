package com.sofoot.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.News;

public class NewsAdapter extends BaseAdapter {

    private final Activity context;

    private final ArrayList<News> newsList;

    public NewsAdapter(final Activity context) {
        this.context = context;
        this.newsList = new ArrayList<News>();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder;

        if (row == null) {

            final LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.news_list_item, null);

            viewHolder = new ViewHolder();
            row.setTag(viewHolder);

            viewHolder.titre = (TextView)row.findViewById(R.id.news_title);
            row.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final News news = this.getItem(position);
        viewHolder.titre.setText(news.getTitre());

        return row;
    }

    public void addAll(final List<News> list) {
        this.newsList.addAll(list);
    }

    public void clear() {
        this.newsList.clear();
    }

    @Override
    public int getCount() {
        return this.newsList.size();
    }

    @Override
    public News getItem(final int position) {
        return this.newsList.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }


    private class ViewHolder {
        TextView titre;
    }

}
