package com.sofoot.adapter;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.Commentaire;

public class CommentaireAdapter extends SofootAdapter<Commentaire> {

    final static private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy à HH:mm", Locale.FRANCE);

    public CommentaireAdapter(final Activity context) {
        super(context);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.commentaire_list_item, null);
            row.setTag(new ViewHolder(row));
        }

        final Commentaire commentaire = this.getItem(position);

        final ViewHolder viewHolder = (ViewHolder) row.getTag();

        if (commentaire.isSpecial()) {
            row.setBackgroundResource(R.drawable.bg_commentaire_special);
            // row.setBackgroundColor(this.context.getResources().getColor(R.color.commentaireBackgroundSpecial));
        }
        else if ((position % 2) == 0) {
            row.setBackgroundResource(R.drawable.bg_commentaire_even);
            // row.setBackgroundColor(this.context.getResources().getColor(R.color.commentaireBackgroundEven));
        }
        else {
            row.setBackgroundResource(R.drawable.bg_commentaire_odd);
            // row.setBackgroundColor(this.context.getResources().getColor(R.color.commentaireBackgroundOdd));
        }

        final SpannableStringBuilder ssb = new SpannableStringBuilder();

        ssb.append("Message de ");
        final int indexStart = ssb.length();
        ssb.append(commentaire.getAuteur());
        final int indexStop = ssb.length();
        ssb.append(" le " + CommentaireAdapter.simpleDateFormat.format(commentaire.getDatePublication()));

        final StyleSpan span = new StyleSpan(Typeface.BOLD);
        try {
            ssb.setSpan(span, indexStart, indexStop, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        } catch (final Exception e) {
        }

        viewHolder.info.setText(ssb);
        viewHolder.texte.setText(Html.fromHtml(commentaire.getTexte()));

        return row;
    }

    private class ViewHolder {

        TextView info;
        TextView texte;

        public ViewHolder(final View view) {
            this.info = (TextView) view.findViewById(R.id.commentaire_info);
            this.texte = (TextView) view.findViewById(R.id.commentaire_texte);
            this.texte.setMovementMethod(LinkMovementMethod.getInstance());

        }
    }

}
