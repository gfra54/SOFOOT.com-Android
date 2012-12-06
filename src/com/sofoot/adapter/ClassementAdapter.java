package com.sofoot.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.Classement;
import com.sofoot.loader.BitmapLoader;
import com.sofoot.utils.BitmapInfo;

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

        final ClubLogoLoader clubLogoLoader = new ClubLogoLoader(viewHolder.club);
        clubLogoLoader.execute(classement.getClub().getLogo());

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



    private class ClubLogoLoader extends BitmapLoader {

        private final TextView textView;

        public ClubLogoLoader(final TextView view) {
            super(view.getContext());
            this.textView = view;
        }

        @Override
        protected void onPostExecute(final BitmapInfo result) {
            final BitmapDrawable bitmapDrawable = new BitmapDrawable(this.textView.getContext().getResources(), result.bitmap);
            bitmapDrawable.setBounds(0, 0, 25, 25);

            this.textView.setCompoundDrawables(
                    bitmapDrawable,
                    null,
                    null,
                    null);
        }
    }
}
