package com.sofoot.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.Ligue;

public class LigueAdapter extends SofootAdapter<Ligue>
{
    public LigueAdapter(final Activity context) {
        super(context);
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

    private class ViewHolder {
        TextView libelle;

        public ViewHolder(final View view) {
            this.libelle = (TextView)view.findViewById(android.R.id.text1);
        }
    }
}
