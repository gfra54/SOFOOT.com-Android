package com.sofoot.adapter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.loader.BitmapLoader;
import com.sofoot.loader.BitmapLoaderCallbacks;

public class CoteAdapter extends SofootAdapter<Rencontre> {

    private static final String LOG_TAG = "CoteAdapter";

    final static private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM", Locale.FRANCE);

    protected final ArrayList<Rencontre> list = new ArrayList<Rencontre>();

    public CoteAdapter(final Activity context) {
        super(context);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.cote_list_item, null);
            row.setTag(new ViewHolder(row));
        }

        final Rencontre rencontre = this.getItem(position);

        final ViewHolder viewHolder = (ViewHolder) row.getTag();

        viewHolder.club1.setText(Html.fromHtml(rencontre.getClub1().getLibelle()));
        viewHolder.club2.setText(Html.fromHtml(rencontre.getClub2().getLibelle()));
        viewHolder.date.setText(CoteAdapter.simpleDateFormat.format(rencontre.getDate()));

        viewHolder.cote1.setText(rencontre.getCote().getCote1());
        viewHolder.coteN.setText(rencontre.getCote().getCoteN());
        viewHolder.cote2.setText(rencontre.getCote().getCote2());

        BitmapLoader.getInstance(this.context).load(rencontre.getClub1().getLogo(),
                new LogoBitmapLoaderCallbacks(viewHolder.logoClub1));
        BitmapLoader.getInstance(this.context).load(rencontre.getClub2().getLogo(),
                new LogoBitmapLoaderCallbacks(viewHolder.logoClub2));

        return row;
    }

    private class ViewHolder {

        TextView club1;
        ImageView logoClub1;
        TextView club2;
        ImageView logoClub2;
        TextView date;
        TextView cote1;
        TextView coteN;
        TextView cote2;

        public ViewHolder(final View view) {
            this.club1 = (TextView) view.findViewById(R.id.rencontreClub1);
            this.logoClub1 = (ImageView) view.findViewById(R.id.rencontreLogoClub1);
            this.club2 = (TextView) view.findViewById(R.id.rencontreClub2);
            this.logoClub2 = (ImageView) view.findViewById(R.id.rencontreLogoClub2);
            this.date = (TextView) view.findViewById(R.id.rencontreDate);
            this.cote1 = (TextView) view.findViewById(R.id.cote1);
            this.coteN = (TextView) view.findViewById(R.id.coteN);
            this.cote2 = (TextView) view.findViewById(R.id.cote2);
        }
    }

    private class LogoBitmapLoaderCallbacks implements BitmapLoaderCallbacks {

        private final ImageView view;

        public LogoBitmapLoaderCallbacks(final ImageView view) {
            this.view = view;
        }

        @Override
        public void onBitmapLoaded(final URL url, final Bitmap bitmap) {
            this.view.setImageBitmap(bitmap);
            this.view.setVisibility(View.VISIBLE);
        }
    }
}
