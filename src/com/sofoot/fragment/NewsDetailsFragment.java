package com.sofoot.fragment;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sofoot.R;
import com.sofoot.domain.model.News;
import com.sofoot.loader.ImageLoader;
import com.sofoot.loader.NewsLoader;

public class NewsDetailsFragment extends Fragment
implements LoaderManager.LoaderCallbacks<News>
{

    final static public String LOG_TAG = "NewsFragment";

    private News currentNews;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.setHasOptionsMenu(true);

        this.getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_details_fragment, container, false);
    }

    @Override
    public Loader<News> onCreateLoader(final int id, final Bundle args) {
        return new NewsLoader(this.getActivity(), this.getArguments().getInt("id"));
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.news_details_activity, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        try {
            switch (item.getItemId()) {
                case R.id.increaseFontSize:
                    this.searchAndChangeTextViewFontSize((ViewGroup)this.getView(), 2);
                    break;

                case R.id.decreaseFontSize:
                    this.searchAndChangeTextViewFontSize((ViewGroup)this.getView(), -2);
                    break;

                case R.id.share:
                    final Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, this.currentNews.getTitre());
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, this.currentNews.getTitre() + "\n\n" + this.currentNews.getUrl() + "\n\nEnvoyé par l'appli SO FOOT");
                    this.startActivity(Intent.createChooser(sharingIntent, this.getString(R.string.share_via)));
                default:

                    break;
            }
        } catch (final Exception e) {
            Log.wtf("NewsDetailsActivity", e);
        }

        return true;
    }


    private void searchAndChangeTextViewFontSize(final ViewGroup root, final int coeff) {
        final int count  = root.getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = root.getChildAt(i);

            if (child instanceof ViewGroup) {
                this.searchAndChangeTextViewFontSize((ViewGroup)child, coeff);
            } else if (child instanceof TextView) {
                final TextView textView = (TextView)child;
                textView.setTextSize(textView.getTextSize() + coeff);
            }
        }
    }

    @Override
    public void onLoadFinished(final Loader<News> loader, final News result) {
        Log.d(NewsDetailsFragment.LOG_TAG, "onLoadFinish : " + result);

        this.currentNews = result;

        if (((NewsLoader)loader).getLastException() != null) {
            Toast.makeText(this.getActivity(), this.getString(R.string.newsloader_error), Toast.LENGTH_LONG).show();
            return;
        }

        if (result.hasSurtitre()) {
            final TextView view = ((TextView)this.getView().findViewById(R.id.surtitre));
            view.setText(result.getSurtitre());
            view.setVisibility(View.VISIBLE);
        }


        ((TextView)this.getView().findViewById(R.id.titre)).setText(result.getTitre());

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yy  à  HH:mm");
        ((TextView)this.getView().findViewById(R.id.publication)).setText(simpleDateFormat.format(result.getPublication()));

        /*
        if (result.hasSoustitre()) {
            final TextView view = (TextView)this.getView().findViewById(R.id.soustitre);
            view.setText(result.getSoustitre());
            view.setVisibility(View.VISIBLE);
        }
         */

        if (result.hasChapo()) {
            final TextView view = (TextView)this.getView().findViewById(R.id.chapo);
            view.setText(result.getChapo());
            view.setVisibility(View.VISIBLE);
        }

        if (result.hasImage(News.ImageSize.NORMAL)) {
            final ImageView imageView = (ImageView)this.getView().findViewById(R.id.img);
            final ImageLoader imageLoader = new ImageLoader(imageView);

            imageView.setTag(result.getImage(News.ImageSize.NORMAL));
            imageLoader.execute(result.getImage(News.ImageSize.NORMAL));
            imageView.setVisibility(View.VISIBLE);

            if (result.hasLegende()) {
                final TextView legende = (TextView)this.getView().findViewById(R.id.legende);
                legende.setText(result.getLegende().toUpperCase());
                legende.setVisibility(View.VISIBLE);
            }
        }

        ((TextView)this.getView().findViewById(R.id.texte)).setText(Html.fromHtml(result.getTexte().replace("\r\n", "<br/>")));

        if (result.hasAuteur()) {
            final TextView view = ((TextView)this.getView().findViewById(R.id.auteur));
            view.setText(result.getAuteur());
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(final Loader<News> loader) {
        // TODO Auto-generated method stub

    }


}
