package com.sofoot.adapter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.But;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.loader.BitmapLoader;
import com.sofoot.loader.BitmapLoaderCallbacks;

public class LiveScoringAdapter extends BaseExpandableListAdapter {

    private static final String LOG_TAG = "ResultatAdapter";

    final static private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
    final static private SimpleDateFormat simpleHourFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);

    protected final Activity context;

    protected final ArrayList<Rencontre> list = new ArrayList<Rencontre>();

    public LiveScoringAdapter(final Activity context) {
        this.context = context;
    }

    public void addAll(final List<Rencontre> list) {
        this.list.addAll(list);
    }

    public void clear() {
        this.list.clear();
    }

    @Override
    public Object getChild(final int groupPosition, final int childPosition) {
        return null;
    }

    @Override
    public long getChildId(final int groupPosition, final int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild,
            final View convertView, final ViewGroup parent) {

        final Rencontre rencontre = this.list.get(groupPosition);

        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.live_scoring_item_goals, null);
            row.setTag(new ViewHolderGoals(row));
        }

        final ViewHolderGoals viewHolderGoals = (ViewHolderGoals) row.getTag();

        viewHolderGoals.buts1.removeAllViews();
        viewHolderGoals.buts2.removeAllViews();

        View alignBelow = null;

        int id = 1;
        for (final But goal : rencontre.getButs1()) {
            final TextView time = new TextView(this.context);
            time.setId(id++);
            time.setMaxLines(1);
            time.setText(goal.getMinute() + "'");
            time.setTextAppearance(this.context, R.style.LiveScoringGoalTime);

            final LayoutParams layoutParamsScore = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsScore.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            layoutParamsScore.rightMargin = (int) this.context.getResources().getDimension(R.dimen.grid_unit);

            if (alignBelow == null) {
                layoutParamsScore.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            }
            else {
                layoutParamsScore.addRule(RelativeLayout.BELOW, alignBelow.getId());
            }

            viewHolderGoals.buts1.addView(time, layoutParamsScore);

            final TextView player = new TextView(this.context);
            player.setMaxLines(1);
            player.setEllipsize(TruncateAt.MIDDLE);
            player.setText(goal.getJoueur().getPrenom() + " " + goal.getJoueur().getNom()
                    + (goal.isCsc() ? "(csc)" : ""));

            player.setTextAppearance(this.context, R.style.LiveScoringGoalPlayer);

            final LayoutParams layoutParamsPlayer = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsPlayer.addRule(RelativeLayout.ALIGN_TOP, time.getId());
            layoutParamsPlayer.addRule(RelativeLayout.RIGHT_OF, time.getId());

            viewHolderGoals.buts1.addView(player, layoutParamsPlayer);

            alignBelow = time;
        }

        alignBelow = null;
        for (final But goal : rencontre.getButs2()) {
            final TextView time = new TextView(this.context, null, R.style.LiveScoringGoalTime);
            time.setId(id++);
            time.setMaxLines(1);
            time.setText(goal.getMinute() + "'");
            time.setTextAppearance(this.context, R.style.LiveScoringGoalTime);

            final LayoutParams layoutParamsScore = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsScore.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            layoutParamsScore.leftMargin = (int) this.context.getResources().getDimension(R.dimen.grid_unit);

            if (alignBelow == null) {
                layoutParamsScore.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            }
            else {
                layoutParamsScore.addRule(RelativeLayout.BELOW, alignBelow.getId());
            }
            viewHolderGoals.buts2.addView(time, layoutParamsScore);

            final TextView player = new TextView(this.context, null, R.style.LiveScoringGoalPlayer);
            player.setMaxLines(1);
            player.setGravity(Gravity.RIGHT);
            player.setEllipsize(TruncateAt.MIDDLE);
            player.setText(goal.getJoueur().getPrenom() + " " + goal.getJoueur().getNom());
            player.setTextAppearance(this.context, R.style.LiveScoringGoalPlayer);

            final LayoutParams layoutParamsPlayer = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsPlayer.addRule(RelativeLayout.ALIGN_TOP, time.getId());
            layoutParamsPlayer.addRule(RelativeLayout.LEFT_OF, time.getId());
            viewHolderGoals.buts2.addView(player, layoutParamsPlayer);

            alignBelow = time;
        }

        return row;
    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        return (this.list.get(groupPosition).hasGoals() ? 1 : 0);
    }

    @Override
    public Object getGroup(final int groupPosition) {
        return this.list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.list.size();
    }

    @Override
    public long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, final View convertView,
            final ViewGroup parent) {
        View row = convertView;

        Log.d(LiveScoringAdapter.LOG_TAG, "getGroupView #" + groupPosition);

        final Rencontre rencontre = (Rencontre) this.getGroup(groupPosition);

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (rencontre.isLabel()) {
                row = layoutInflater.inflate(R.layout.live_scoring_item_label, null);
            }
            else {
                row = layoutInflater.inflate(R.layout.live_scoring_item_rencontre, null);
                row.setTag(new ViewHolderRencontre(row));
            }
        }

        if (rencontre.isLabel()) {
            ((TextView) row).setText(rencontre.getLibelle());
        }
        else {
            final ViewHolderRencontre viewHolder = (ViewHolderRencontre) row.getTag();

            viewHolder.club1.setText(rencontre.getClub1().getLibelle());
            viewHolder.club2.setText(rencontre.getClub2().getLibelle());

            /*
             * Prévu dans le futur, afficher : - la date du match DD/MM/YY en
             * petit - l'heure en gros
             */
            if (rencontre.isPlanned(false)) {
                viewHolder.etat.setText(LiveScoringAdapter.simpleDateFormat.format(rencontre.getDate()));
                viewHolder.score.setText(LiveScoringAdapter.simpleHourFormat.format(rencontre.getDate()));
            }

            /*
             * Le match est prévu aujourd'hui, afficher : - le contenu du champ
             * "etat" en petit - l'heure en grand.
             */
            else if (rencontre.isPlanned(true)) {
                viewHolder.etat.setText(rencontre.getEtat());
                viewHolder.score.setText(LiveScoringAdapter.simpleHourFormat.format(rencontre.getDate()));
            }

            /*
             * Le match est en cours, afficher : - le contenu du champ "etat" en
             * petit, si le temps de jeu est égal à -1, sinon le temps de jeu -
             * le score en grand
             */
            else if (rencontre.isPlaying()) {

                if (rencontre.getTempsDeJeu() == -1) {
                    viewHolder.etat.setText(rencontre.getEtat());
                }
                else {
                    viewHolder.etat.setText(String.valueOf(rencontre.getTempsDeJeu()) + "'");
                }

                viewHolder.score.setText(String.valueOf(rencontre.getScore1()) + " - "
                        + String.valueOf(rencontre.getScore2()));

            }

            /*
             * Le match est terminé - le contenu du champ "etat" en petit - le
             * score en grand
             */
            else if (rencontre.isPlayed()) {
                viewHolder.etat.setText(rencontre.getEtat());
                viewHolder.score.setText(String.valueOf(rencontre.getScore1()) + " - "
                        + String.valueOf(rencontre.getScore2()));
            }

            if (rencontre.hasGoals() == false) {
                viewHolder.indicator.setVisibility(View.GONE);
            }
            else {
                viewHolder.indicator.setVisibility(View.VISIBLE);
                viewHolder.indicator.setImageResource(isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down);
            }

            // Chargement des logos
            viewHolder.logo1.setVisibility(View.INVISIBLE);
            viewHolder.logo2.setVisibility(View.INVISIBLE);

            BitmapLoader.getInstance(this.context).load(rencontre.getClub1().getLogo(),
                    new LogoBitmapLoaderCallbacks(viewHolder.logo1));

            BitmapLoader.getInstance(this.context).load(rencontre.getClub2().getLogo(),
                    new LogoBitmapLoaderCallbacks(viewHolder.logo2));
        }

        return row;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(final int groupPosition, final int childPosition) {
        return false;
    }

    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    @Override
    public int getGroupType(final int groupPosition) {
        return (this.list.get(groupPosition).isLabel() ? 0 : 1);
    }

    private class ViewHolderRencontre {

        TextView club1;
        TextView club2;
        TextView score;
        TextView etat;
        ImageView logo1;
        ImageView logo2;
        ImageView indicator;

        public ViewHolderRencontre(final View view) {
            this.club1 = (TextView) view.findViewById(R.id.rencontreClub1);
            this.club2 = (TextView) view.findViewById(R.id.rencontreClub2);

            this.logo1 = (ImageView) view.findViewById(R.id.rencontreLogoClub1);
            this.logo2 = (ImageView) view.findViewById(R.id.rencontreLogoClub2);
            this.indicator = (ImageView) view.findViewById(R.id.indicator);

            this.score = (TextView) view.findViewById(R.id.rencontreScore);
            this.etat = (TextView) view.findViewById(R.id.rencontreEtat);
        }
    }

    private class ViewHolderGoals {

        RelativeLayout buts1;
        RelativeLayout buts2;

        public ViewHolderGoals(final View view) {
            this.buts1 = (RelativeLayout) view.findViewById(R.id.buts1);
            this.buts2 = (RelativeLayout) view.findViewById(R.id.buts2);
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
