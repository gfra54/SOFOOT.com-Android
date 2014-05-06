package com.sofoot.fragment;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.SofootException;
import com.sofoot.activity.LoginActivity;
import com.sofoot.adapter.CommentaireAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Commentaire;
import com.sofoot.domain.model.News;
import com.sofoot.domain.model.NewsMeta;
import com.sofoot.loader.CommentaireLoader;
import com.sofoot.loader.NewsLoader;
import com.sofoot.task.LogoutListener;
import com.sofoot.task.LogoutTask;
import com.sofoot.task.LogoutTaskException;
import com.sofoot.task.PostCommentaireTask;
import com.sofoot.task.SessionCheckListener;
import com.sofoot.task.SessionCheckTask;
import com.sofoot.utils.SofootAnalytics;

/**
 * Fragment utilisé pour afficher une news et ses commentaire. Etant donné qu'il
 * n'est pas possible à d'utiliser une ListView dans une ScrollView, on affiche
 * le détails de la news dans le header de la liste. Après avoir récupéré la
 * liste des news, on vérifie que l'état de la session, afin d'afficher les
 * boutons qui conviennent
 * 
 * @author christophe.borsenberger@vosprojetsweb.pro
 */
public class NewsDetailsFragment extends SherlockFragment implements SofootAnalytics, OnClickListener,
        SessionCheckListener, LogoutListener {

    final static public String LOG_TAG = "NewsDetailsFragment";

    final static private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yy  à  HH:mm",
            Locale.FRANCE);

    private NewsMeta newsMeta;

    private View emptyView;

    private View errorView;

    private View newsLayout;

    private ListView newsDetailsView;

    private ViewGroup headerView;

    private View footerView;

    private CommentaireAdapter adapter;

    private Sofoot application;

    private final NewsLoaderCallback newsLoaderCallback = new NewsLoaderCallback();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.newsMeta = (NewsMeta) this.getArguments().getParcelable("newsMeta");
        this.application = ((Sofoot) this.getActivity().getApplication());
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i(NewsDetailsFragment.LOG_TAG, "on Start");

        this.getLoaderManager().initLoader(0, null, this.newsLoaderCallback);

        this.trackPageView(EasyTracker.getTracker());
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(NewsDetailsFragment.LOG_TAG, "on Resume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(NewsDetailsFragment.LOG_TAG, "on destroy");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(NewsDetailsFragment.LOG_TAG, "on pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(NewsDetailsFragment.LOG_TAG, "on stop");
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(NewsDetailsFragment.LOG_TAG, "onActivityCreated");
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        this.displayMemberBlock();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(NewsDetailsFragment.LOG_TAG, "on Create View");

        final View v = inflater.inflate(R.layout.news_details_fragment, container, false);

        this.emptyView = v.findViewById(android.R.id.empty);
        this.errorView = v.findViewById(R.id.error);
        this.newsLayout = v.findViewById(R.id.news_layout);

        this.newsDetailsView = (ListView) this.newsLayout.findViewById(R.id.newsDetails);

        // Header : ie: le détails de la news
        this.headerView = (ViewGroup) inflater.inflate(R.layout.news_details_header_view, null);
        this.newsDetailsView.addHeaderView(this.headerView);

        // Footer : ie : lien charger tous les comms
        this.footerView = inflater.inflate(R.layout.news_details_footer_view, null);
        this.newsDetailsView.addFooterView(this.footerView);

        // Les commentaires
        this.adapter = new CommentaireAdapter(this.getActivity());
        this.newsDetailsView.setAdapter(this.adapter);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.news_details_fragment, menu);
    }

    @Override
    public void trackPageView(final Tracker easyTracker) {
        easyTracker.trackView("details_news/" + this.newsMeta.getTitre() + "/" + this.newsMeta.getId());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        try {
            switch (item.getItemId()) {
                case R.id.increaseFontSize:
                    this.searchAndChangeTextViewFontSize(this.headerView, 3);
                    return true;

                case R.id.decreaseFontSize:
                    this.searchAndChangeTextViewFontSize(this.headerView, -3);
                    return true;

                case R.id.share:
                    this.shareNews();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (final Exception e) {
            Log.wtf("NewsDetailsActivity", e);
        }

        return false;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.error:
                this.getLoaderManager().restartLoader(0, null, this.newsLoaderCallback);
                break;

            /*
             * case R.id.nbComms: // Ca ne marche pas. Impossible de scroller
             * jusqu'à la fin du // header pour voir les commentaires //
             * this.newsDetailsView.scrollTo(0, this.headerView.getHeight() // -
             * 300); break;
             */

            case R.id.btn_creer_compte:
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(this.getActivity().getResources()
                        .getString(R.string.url_creer_compte))));
                break;

            case R.id.btn_mon_compte:
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(this.getActivity().getResources()
                        .getString(R.string.url_mon_compte))));
                break;

            case R.id.btn_login:
                this.startActivityForResult(new Intent(this.getActivity(), LoginActivity.class), 1);
                break;

            case R.id.btn_logout:
                new LogoutTask(this.getActivity(), this.application.getWSGateway(), this).execute(this.application
                        .getMember());
                break;

            case R.id.btn_commentaire_send:
                final EditText composer = (EditText) this.newsLayout.findViewById(R.id.commentaire_composer);
                if (composer.getText().length() > 0) {
                    new PostCommentaireTask(this).execute(composer.getText().toString());
                }
                break;

            case R.id.btn_all_comms:
                this.loadAllCommentaires();
                break;
        }
    }

    @Override
    public void onSessionChecked(final boolean valid) {
        this.displayMemberBlock();
    }

    @Override
    public void onLogoutFailed(final Exception exception) {
        Toast.makeText(
                this.getActivity(),
                ((exception instanceof LogoutTaskException) ? exception.getMessage() : this
                        .getString(R.string.logout_error)), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLogoutSuccess() {
        this.application.storeMemberSession(this.application.getMember());
        this.displayMemberBlock();

        Toast.makeText(this.getActivity(), this.getString(R.string.logout_success), Toast.LENGTH_LONG).show();
    }

    public NewsMeta getNewsMeta() {
        return this.newsMeta;
    }

    /**
     * Affiche le block "Votre compte sofoot" en fonction de l'etat de la
     * session
     */
    public void displayMemberBlock() {
        final boolean connected = this.application.isConnected();

        final View monCompte = this.headerView.findViewById(R.id.btn_mon_compte);
        monCompte.setOnClickListener(this);

        final View logout = this.headerView.findViewById(R.id.btn_logout);
        logout.setOnClickListener(this);

        final View creerCompte = this.headerView.findViewById(R.id.btn_creer_compte);
        creerCompte.setOnClickListener(this);

        final View login = this.headerView.findViewById(R.id.btn_login);
        login.setOnClickListener(this);

        this.headerView.findViewById(android.R.id.progress).setVisibility(View.GONE);

        final View layoutCommentaireComposer = this.newsLayout.findViewById(R.id.layout_commentaire_composer);
        layoutCommentaireComposer.findViewById(R.id.btn_commentaire_send).setOnClickListener(this);

        if (connected) {
            monCompte.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            layoutCommentaireComposer.setVisibility(View.VISIBLE);
            creerCompte.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
        }
        else {
            monCompte.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            layoutCommentaireComposer.setVisibility(View.GONE);
            creerCompte.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
        }

    }

    private void displayException(final Exception e) {
        if (e instanceof SofootException) {
            ((TextView) this.errorView.findViewById(R.id.errorTextView)).setText(e.getLocalizedMessage());
        }
        else {
            ((TextView) this.errorView.findViewById(R.id.errorTextView)).setText(this
                    .getString(R.string.unexpected_exception) + " : " + e.getLocalizedMessage());
        }

        this.errorView.findViewById(R.id.errorButton).setOnClickListener(this);

        this.emptyView.setVisibility(View.GONE);
        this.errorView.setVisibility(View.VISIBLE);
        this.newsDetailsView.setVisibility(View.GONE);
    }

    private void displayNewsDetails(final News news) {

        Log.i(NewsDetailsFragment.LOG_TAG, "displayNewsDetails");

        if (news == null) {
            return;
        }

        try {
            if (news.hasSurtitre()) {
                final TextView view = ((TextView) this.headerView.findViewById(R.id.surtitre));
                view.setText(Html.fromHtml(news.getSurtitre().toUpperCase(Locale.getDefault())));
                view.setVisibility(View.VISIBLE);
            }

            final SpannableStringBuilder builder = new SpannableStringBuilder();
            int start = 0;

            // Le titre principal
            builder.append(Html.fromHtml(news.getTitre()).toString());

            try {
                builder.setSpan(new TextAppearanceSpan(this.getActivity(), R.style.SofootTitre), start,
                        builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            } catch (final Exception e) {
            }

            builder.append("\n");

            // La date de publication
            start = builder.length();
            builder.append(NewsDetailsFragment.simpleDateFormat.format(news.getPublication()));

            try {
                builder.setSpan(new TextAppearanceSpan(this.getActivity(), R.style.NewsDetailsDatePublication), start,
                        builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            } catch (final Exception e) {
            }

            if (news.hasCommentaire()) {
                builder.append("   ");

                start = builder.length();
                builder.append(" ");
                try {
                    builder.setSpan(new ImageSpan(this.getActivity(), R.drawable.ic_commentaire,
                            DynamicDrawableSpan.ALIGN_BASELINE), start, builder.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (final Exception e) {
                }

                // Le nombre de réaction
                start = builder.length();
                builder.append(" ");
                builder.append(this.getString(R.string.comments));
                builder.append(" (");
                builder.append(String.valueOf(news.getNbCommentaires()));
                builder.append(")");

                try {
                    builder.setSpan(new TextAppearanceSpan(this.getActivity(), R.style.NewsDetailsNbComms), start,
                            builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                } catch (final Exception e) {
                }
            }

            ((TextView) this.headerView.findViewById(R.id.titre)).setText(builder);

            if (news.hasChapo()) {
                final TextView view = (TextView) this.headerView.findViewById(R.id.chapo);
                view.setText(Html.fromHtml(news.getChapo()));
                view.setVisibility(View.VISIBLE);
            }

            if (news.hasImage()) {
                final ImageView imageView = (ImageView) NewsDetailsFragment.this.headerView.findViewById(R.id.img);

                if (imageView.getDrawable() == null) {
                    imageView.setVisibility(View.INVISIBLE);
                    new PictureDownloadTask(imageView).execute(news.getImage(this.getActivity().getWindowManager()
                            .getDefaultDisplay()));
                }

                if (news.hasLegende()) {
                    final TextView legende = (TextView) this.headerView.findViewById(R.id.legende);
                    legende.setText(Html.fromHtml(news.getLegende().toUpperCase(Locale.getDefault())));
                    legende.setVisibility(View.VISIBLE);
                }
            }

            final TextView texte = ((TextView) this.headerView.findViewById(R.id.texte));
            texte.setMovementMethod(LinkMovementMethod.getInstance());
            texte.setText(Html.fromHtml(news.getTexte().replace("\r\n", "<br/>")));

            if (news.hasAuteur()) {
                final TextView view = ((TextView) this.headerView.findViewById(R.id.auteur));
                view.setText(Html.fromHtml(news.getAuteur()));
                view.setVisibility(View.VISIBLE);
            }

            if (news.hasCommentaire()) {

                // si l'adapter est déjà rempli, on ne fait rien de plus.
                if (this.adapter.getCount() < news.getNbCommentaires()) {
                    if (news.getNbCommentaires() > 20) {
                        this.footerView.setVisibility(View.VISIBLE);
                        this.footerView.findViewById(R.id.btn_all_comms).setOnClickListener(this);
                    }

                    // Chargement des commentaires : mise à jour de l'adapter
                    this.adapter.addAll(news.getCommentaires());
                    this.adapter.notifyDataSetChanged();
                }
            }

            this.emptyView.setVisibility(View.GONE);
            this.errorView.setVisibility(View.GONE);
            this.newsLayout.setVisibility(View.VISIBLE);

            // Betclic pub
            this.displayBetClicAd();
        } catch (final Exception e) {
            Log.wtf(NewsDetailsFragment.LOG_TAG, e);
        }
    }

    public void displayBetClicAd() {

        final FrameLayout container = (FrameLayout) this.headerView.findViewById(R.id.adViewContainer);
        container.removeAllViews();

        final PublisherAdView adView = new PublisherAdView(this.getActivity());
        adView.setAdSizes(new AdSize(300, 75));
        adView.setAdUnitId(this.getString(R.string.betclic_bloc_news_details_unit_id));

        container.addView(adView);

        adView.loadAd(new PublisherAdRequest.Builder().build());
    }

    public void loadAllCommentaires() {
        if (this.getLoaderManager().getLoader(1) == null) {
            this.getLoaderManager().initLoader(1, null, new CommentaireLoaderCallback());
        }
        else {
            this.getLoaderManager().restartLoader(1, null, new CommentaireLoaderCallback());
        }
    }

    public void resetComposer() {
        ((EditText) this.newsLayout.findViewById(R.id.commentaire_composer)).setText("");
    }

    private void shareNews() {
        final Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, this.newsMeta.getTitre());
        sharingIntent
                .putExtra(android.content.Intent.EXTRA_TEXT, this.newsMeta.getTitre() + "\n\n" + this.newsMeta.getUrl()
                        + "\n\n" + this.getString(R.string.send_via));
        this.startActivity(Intent.createChooser(sharingIntent, this.getString(R.string.share_via)));
    }

    private void searchAndChangeTextViewFontSize(final ViewGroup root, final int coeff) {
        final int count = root.getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = root.getChildAt(i);

            if (child instanceof ViewGroup) {
                this.searchAndChangeTextViewFontSize((ViewGroup) child, coeff);
            }
            else if (child instanceof TextView) {
                final TextView textView = (TextView) child;

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + coeff);
            }
        }
    }

    private class NewsLoaderCallback implements LoaderManager.LoaderCallbacks<News> {
        @Override
        public Loader<News> onCreateLoader(final int id, final Bundle args) {
            NewsDetailsFragment.this.emptyView.setVisibility(View.VISIBLE);
            NewsDetailsFragment.this.errorView.setVisibility(View.GONE);
            NewsDetailsFragment.this.newsLayout.setVisibility(View.GONE);

            return new NewsLoader(NewsDetailsFragment.this.getActivity(), NewsDetailsFragment.this.newsMeta.getId());
        }

        @Override
        public void onLoadFinished(final Loader<News> loader, final News result) {

            final Exception e = ((NewsLoader) loader).getLastException();

            if (e != null) {
                NewsDetailsFragment.this.displayException(e);
            }
            else {
                NewsDetailsFragment.this.displayNewsDetails(result);

                // Vérification de la session
                if (NewsDetailsFragment.this.application.getMember().isSessionMustBeChecked()) {
                    new SessionCheckTask(NewsDetailsFragment.this.application, NewsDetailsFragment.this)
                            .execute(NewsDetailsFragment.this.application.getMember());
                }
                else {
                    NewsDetailsFragment.this.displayMemberBlock();
                }
            }
        }

        @Override
        public void onLoaderReset(final Loader<News> loader) {
            // TODO Auto-generated method stub
        }
    }

    private class CommentaireLoaderCallback implements LoaderManager.LoaderCallbacks<Collection<Commentaire>> {

        @Override
        public Loader<Collection<Commentaire>> onCreateLoader(final int id, final Bundle bundle) {
            NewsDetailsFragment.this.footerView.findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
            NewsDetailsFragment.this.footerView.findViewById(R.id.btn_all_comms).setVisibility(View.GONE);

            return new CommentaireLoader(NewsDetailsFragment.this.getActivity(),
                    NewsDetailsFragment.this.newsMeta.getId());
        }

        @Override
        public void onLoadFinished(final Loader<Collection<Commentaire>> loader,
                final Collection<Commentaire> commentaires) {

            final Exception e = ((CommentaireLoader) loader).getLastException();

            if (e != null) {
                NewsDetailsFragment.this.footerView.findViewById(android.R.id.progress).setVisibility(View.GONE);
                NewsDetailsFragment.this.footerView.findViewById(R.id.btn_all_comms).setVisibility(View.VISIBLE);

                Toast.makeText(NewsDetailsFragment.this.getActivity(),
                        NewsDetailsFragment.this.getActivity().getString(R.string.loader_error), Toast.LENGTH_LONG)
                        .show();
                return;
            }

            NewsDetailsFragment.this.footerView.findViewById(android.R.id.progress).setVisibility(View.GONE);
            NewsDetailsFragment.this.footerView.findViewById(R.id.btn_all_comms).setVisibility(View.VISIBLE);

            NewsDetailsFragment.this.adapter.clear();
            NewsDetailsFragment.this.adapter.addAll(commentaires);
            NewsDetailsFragment.this.adapter.notifyDataSetChanged();

            NewsDetailsFragment.this.footerView.setVisibility(View.GONE);
        }

        @Override
        public void onLoaderReset(final Loader<Collection<Commentaire>> arg0) {
            // TODO Auto-generated method stub

        }
    }

    private class PictureDownloadTask extends AsyncTask<URL, Void, Bitmap> {

        private final ImageView imageView;

        public PictureDownloadTask(final ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(final URL... urls) {
            try {
                Log.d(NewsDetailsFragment.LOG_TAG, "Download Picture : " + urls[0]);
                return BitmapFactory.decodeStream(urls[0].openStream());
            } catch (final Exception ioe) {
                Log.wtf(NewsDetailsFragment.LOG_TAG, ioe);
            }

            return null;
        }

        @Override
        protected void onPostExecute(final Bitmap result) {

            if ((result == null) || (this.imageView == null)) {
                return;
            }

            if (NewsDetailsFragment.this.isDetached() || NewsDetailsFragment.this.isRemoving()) {
                return;
            }

            this.imageView.setImageBitmap(result);
            this.imageView.setVisibility(View.VISIBLE);

            final View view = NewsDetailsFragment.this.headerView;
            final float ratio = (float) view.getWidth() / result.getWidth();
            Log.d(NewsDetailsFragment.LOG_TAG, "Ratio : " + ratio);
            final int width = (int) (result.getWidth() * ratio);
            final int height = (int) (result.getHeight() * ratio);
            this.imageView.getLayoutParams().width = width;
            this.imageView.getLayoutParams().height = height;

        }
    }
}
