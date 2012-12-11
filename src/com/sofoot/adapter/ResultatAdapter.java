package com.sofoot.adapter;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.loader.BitmapLoader;
import com.sofoot.utils.BitmapInfo;

public class ResultatAdapter extends SofootAdapter<Rencontre>
{
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy\nHH:mm");

    public ResultatAdapter(final Activity context) {
        super(context);
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

        viewHolder.club1.setText(rencontre.getClub1().getLibelle());
        viewHolder.club2.setText(rencontre.getClub2().getLibelle());


        final SpannableStringBuilder ssb = new SpannableStringBuilder();
        if (rencontre.isPlayed()) {
            ssb.append(String.valueOf(rencontre.getScore1()));
            ssb.append(" - ");
            ssb.append(String.valueOf(rencontre.getScore2()));

            final TextAppearanceSpan span = new TextAppearanceSpan(this.context, R.style.ResultatListItemInfoScore);
            ssb.setSpan(span, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else {
            ssb.append(this.simpleDateFormat.format(rencontre.getDate()));

            final TextAppearanceSpan span1 = new TextAppearanceSpan(this.context, R.style.ResultatListItemInfoDate);
            ssb.setSpan(span1, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            final TextAppearanceSpan span2 = new TextAppearanceSpan(this.context, R.style.ResultatListItemInfoHeure);
            ssb.setSpan(span2, 8, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        viewHolder.info.setText(ssb);

        viewHolder.logo1.setTag(rencontre.getClub1().getLogo());
        viewHolder.logo1.setVisibility(View.INVISIBLE);
        final BitmapLoader bitmapLoader1 = new ClubLogoLoader(viewHolder.logo1);
        bitmapLoader1.execute(rencontre.getClub1().getLogo());


        viewHolder.logo2.setTag(rencontre.getClub2().getLogo());
        viewHolder.logo2.setVisibility(View.INVISIBLE);
        final BitmapLoader bitmapLoader2 = new ClubLogoLoader(viewHolder.logo2);
        bitmapLoader2.execute(rencontre.getClub2().getLogo());

        return row;
    }



    private class ViewHolder {

        TextView club1;
        TextView club2;
        TextView info;
        ImageView logo1;
        ImageView logo2;

        public ViewHolder(final View view) {
            this.club1 = (TextView)view.findViewById(R.id.rencontreClub1);
            this.logo1 = (ImageView)view.findViewById(R.id.rencontreLogoClub1);
            this.club2 = (TextView)view.findViewById(R.id.rencontreClub2);
            this.logo2 = (ImageView)view.findViewById(R.id.rencontreLogoClub2);
            this.info = (TextView)view.findViewById(R.id.rencontreInfo);
        }
    }



    private class ClubLogoLoader extends BitmapLoader {

        private final ImageView imageView;

        public ClubLogoLoader(final ImageView view) {
            super(view.getContext());
            this.imageView = view;
        }

        @Override
        protected void onPreExecute() {
            this.imageView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(final BitmapInfo result) {
            if ((result.url == this.imageView.getTag()) && (result.bitmap != null)) {
                this.imageView.setImageBitmap(result.bitmap);
                this.imageView.setVisibility(View.VISIBLE);
            } else {
                this.imageView.setVisibility(View.INVISIBLE);
            }
        }
    }
}
