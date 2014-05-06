package com.sofoot.adapter;

import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.Classement;
import com.sofoot.loader.BitmapLoader;
import com.sofoot.loader.BitmapLoaderCallbacks;

public class ClassementAdapter extends SofootAdapter<Classement>
{
    public ClassementAdapter(final Activity context) {
        super(context);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.classement_list_item, null);
            row.setTag(new ViewHolder(row));
        }

        final Classement classement = this.getItem(position);

        final ViewHolder viewHolder = (ViewHolder)row.getTag();

        viewHolder.rang.setText(String.valueOf(classement.getRang()));
        viewHolder.club.setText(Html.fromHtml(classement.getClub().getLibelle()));
        viewHolder.club.setCompoundDrawables(null, null, null, null);
        viewHolder.pts.setText(String.valueOf(classement.getNbPoints()));
        viewHolder.journee.setText(String.valueOf(classement.getNbMatchs()));
        viewHolder.gagne.setText(String.valueOf(classement.getNbMatchsGagnes()));
        viewHolder.nul.setText(String.valueOf(classement.getNbMatchsNuls()));
        viewHolder.perdu.setText(String.valueOf(classement.getNbMatchsPerdus()));

        if (classement.getDiff() >= 0) {
            viewHolder.diff.setText("+" + String.valueOf(classement.getDiff()));
        } else {
            viewHolder.diff.setText(String.valueOf(classement.getDiff()));
        }

        BitmapLoader.getInstance(this.context).load(
                classement.getClub().getLogo(),
                new LogoBitmapLoaderCallbacks(viewHolder.club));

        return row;
    }

    private class ViewHolder {

        TextView rang;
        TextView club;
        TextView journee;
        TextView pts;
        TextView gagne;
        TextView nul;
        TextView perdu;
        TextView diff;

        public ViewHolder(final View view) {
            this.rang = (TextView)view.findViewById(R.id.classement_rang);
            this.club = (TextView)view.findViewById(R.id.classement_club);
            this.journee = (TextView)view.findViewById(R.id.classement_journee);
            this.pts = (TextView)view.findViewById(R.id.classement_points);
            this.gagne = (TextView)view.findViewById(R.id.classement_gagne);
            this.nul = (TextView)view.findViewById(R.id.classement_nul);
            this.perdu = (TextView)view.findViewById(R.id.classement_perdu);
            this.diff = (TextView)view.findViewById(R.id.classement_diff);
        }
    }


    private class LogoBitmapLoaderCallbacks implements BitmapLoaderCallbacks {

        private final TextView textView;

        public LogoBitmapLoaderCallbacks(final TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onBitmapLoaded(final URL url, final Bitmap bitmap) {
            final BitmapDrawable bitmapDrawable = new BitmapDrawable(this.textView.getContext().getResources(), bitmap);
            final int size = (int)this.textView.getResources().getDimension(R.dimen.logoSize);
            bitmapDrawable.setBounds(0, 0, size , size);

            this.textView.setCompoundDrawables(
                    bitmapDrawable,
                    null,
                    null,
                    null);
        }
    }
}
