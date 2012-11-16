package com.sofoot.adapter;

import java.text.SimpleDateFormat;
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
import com.sofoot.domain.model.Rencontre;

public class ResultatAdapter extends BaseAdapter
{
    private final List<Rencontre> rencontres = new ArrayList<Rencontre>();

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");

    private final Activity context;

    public ResultatAdapter(final Activity context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.rencontres.size();
    }

    @Override
    public Rencontre getItem(final int position) {
        return this.rencontres.get(position);
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
            row = layoutInflater.inflate(R.layout.resultat_list_item, null);
            row.setTag(new ViewHolder(row));
        }

        final Rencontre rencontre = this.getItem(position);

        final ViewHolder viewHolder = (ViewHolder)row.getTag();
        //viewHolder.date.setText(this.simpleDateFormat.format(rencontre.getDate()));
        viewHolder.club1.setText(rencontre.getClub1().getLibelle());
        viewHolder.score.setText("" + rencontre.getScore1() + " - " + rencontre.getScore2());
        viewHolder.club2.setText(rencontre.getClub2().getLibelle());

        return row;
    }

    public void addAll(final List<Rencontre> rencontres) {
        this.rencontres.addAll(rencontres);
    }

    public void clear() {
        this.rencontres.clear();
    }

    private class ViewHolder {

        TextView date;
        TextView club1;
        TextView club2;
        TextView score;

        public ViewHolder(final View view) {
            //this.date = (TextView)view.findViewById(R.id.recontreDate);
            this.club1 = (TextView)view.findViewById(R.id.recontreClub1);
            this.club2 = (TextView)view.findViewById(R.id.recontreClub2);
            this.score = (TextView)view.findViewById(R.id.recontreScore);
        }
    }
}
