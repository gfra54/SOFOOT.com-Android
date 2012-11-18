package com.sofoot.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.News;

public class NewsAdapter extends BaseAdapter {

    static private final int ITEM_NEWS = 1;

    static private final int ITEM_LOADER = 0;

    private final Activity context;

    private final ArrayList<News> newsList = new ArrayList<News>();


    public NewsAdapter(final Activity context) {
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final int type = this.getItemViewType(position);

        if (type == NewsAdapter.ITEM_LOADER) {
            return this.getLoaderView(position, convertView, parent);
        } else if (type == NewsAdapter.ITEM_NEWS) {
            return this.getNewsView(position, convertView, parent);
        }

        return null;
    }


    private View getNewsView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.news_list_item, parent, false);
            row.setTag(new ViewHolder(row));
        }

        final News news = this.getItem(position);

        final ViewHolder viewHolder = (ViewHolder)row.getTag();
        viewHolder.titre.setText(news.getTitre());
        viewHolder.chapo.setText(news.getChapo());
        //viewHolder.icon.setTag(news.getUrl());
        //new ImageLoader(viewHolder.icon):

        return row;
    }

    private View getLoaderView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.news_list_item_loading, parent, false);
        }

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
        return this.newsList.size() + 1;
    }

    @Override
    public News getItem(final int position) {
        return this.newsList.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(final int position) {
        return (position >= this.newsList.size()) ? NewsAdapter.ITEM_LOADER : NewsAdapter.ITEM_NEWS;
    }

    public String[] getNewsIds()
    {
        final String[] ids = new String[this.newsList.size()];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = String.valueOf(this.newsList.get(i).getId());
        }

        return ids;
    }

    private class ViewHolder {
        TextView titre;
        ImageView icon;
        TextView chapo;

        public ViewHolder(final View view) {
            this.titre = (TextView)view.findViewById(android.R.id.title);
            this.icon = (ImageView)view.findViewById(android.R.id.icon);
            this.chapo = (TextView)view.findViewById(android.R.id.text1);
        }
    }
}
