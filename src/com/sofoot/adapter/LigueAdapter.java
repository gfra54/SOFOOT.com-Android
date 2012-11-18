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
import com.sofoot.domain.model.Ligue;

public class LigueAdapter extends BaseAdapter
{
    private final List<Ligue> ligues = new ArrayList<Ligue>();

    private final Activity context;

    public LigueAdapter(final Activity context) {
        this.context = context;
    }


    public void addAll(final List<Ligue> ligues) {
        this.ligues.addAll(ligues);
    }

    public void clear() {
        this.ligues.clear();
    }

    @Override
    public int getCount() {
        return this.ligues.size();
    }

    @Override
    public Ligue getItem(final int position) {
        return this.ligues.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.ligue_list_item, parent, false);
            row.setTag(new ViewHolder(row));
        }

        final Ligue ligue = this.getItem(position);

        final ViewHolder viewHolder = (ViewHolder)row.getTag();
        viewHolder.libelle.setText(ligue.getLibelle());

        return row;
    }


    public String[] getLiguesIds()
    {
        final String[] ids = new String[this.ligues.size()];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = this.ligues.get(i).getId();
        }

        return ids;
    }

    private class ViewHolder {
        TextView libelle;

        public ViewHolder(final View view) {
            this.libelle = (TextView)view.findViewById(android.R.id.text1);
        }
    }
}
