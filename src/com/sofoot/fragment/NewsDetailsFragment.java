package com.sofoot.fragment;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.LruCache;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.domain.model.News;
import com.sofoot.domain.model.NewsMeta;
import com.sofoot.loader.NewsLoader;
import com.sofoot.utils.SofootAnalytics;

public class NewsDetailsFragment extends SherlockFragment
implements LoaderManager.LoaderCallbacks<News>, SofootAnalytics
{

    final static public String LOG_TAG = "NewsDetailsFragment";

    private NewsMeta newsMeta;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.newsMeta = (NewsMeta)this.getArguments().getParcelable("newsMeta");
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.setHasOptionsMenu(true);
    }


    @Override
    public void onStart() {
        super.onStart();

        this.getLoaderManager().initLoader(0, null, this);

        this.trackPageView(EasyTracker.getTracker());
        Log.i(NewsDetailsFragment.LOG_TAG, "On Start "  + this.toString());
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(NewsDetailsFragment.LOG_TAG, "On Resume "  + this.toString());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_details_fragment, container, false);
    }

    @Override
    public Loader<News> onCreateLoader(final int id, final Bundle args) {
        return new NewsLoader(this.getActivity(), this.newsMeta.getId());
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        Log.d(NewsDetailsFragment.LOG_TAG, "onCreateOptionsMenu" + inflater.toString());
        inflater.inflate(R.menu.news_details_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        try {
            switch (item.getItemId()) {
                case R.id.increaseFontSize:
                    this.searchAndChangeTextViewFontSize((ViewGroup)this.getView(), 2);
                    return true;

                case R.id.decreaseFontSize:
                    this.searchAndChangeTextViewFontSize((ViewGroup)this.getView(), -2);
                    return true;

                case R.id.share:
                    final Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, this.newsMeta.getTitre());
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, this.newsMeta.getTitre() +
                            "\n\n" + this.newsMeta.getUrl() +
                            "\n\n" + this.getString(R.string.send_via));
                    this.startActivity(Intent.createChooser(sharingIntent, this.getString(R.string.share_via)));
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (final Exception e) {
            Log.wtf("NewsDetailsActivity", e);
        }

        return false;
    }


    private void searchAndChangeTextViewFontSize(final ViewGroup root, final int coeff) {
        final int count  = root.getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = root.getChildAt(i);

            if (child instanceof ViewGroup) {
                this.searchAndChangeTextViewFontSize((ViewGroup)child, coeff);
            } else if (child instanceof TextView) {
                final TextView textView = (TextView)child;

                Log.d("TEXT SIZE", "" + textView.getTextSize() + coeff + " " + (textView.getTextSize() + coeff));

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + coeff);
            }
        }
    }

    @Override
    public void onLoadFinished(final Loader<News> loader, final News result) {
        Log.d(NewsDetailsFragment.LOG_TAG, "onLoadFinish : " + result);

        if (((NewsLoader)loader).getLastException() != null) {
            Toast.makeText(this.getActivity(), this.getString(R.string.newsloader_error), Toast.LENGTH_LONG).show();
            return;
        }

        if (result.hasSurtitre()) {
            final TextView view = ((TextView)this.getView().findViewById(R.id.surtitre));
            view.setText(Html.fromHtml(result.getSurtitre().toUpperCase()));
            view.setVisibility(View.VISIBLE);
        }

        ((TextView)this.getView().findViewById(R.id.titre)).setText(Html.fromHtml(result.getTitre()));

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yy  à  HH:mm");
        ((TextView)this.getView().findViewById(R.id.publication)).setText(simpleDateFormat.format(result.getPublication()));

        if (result.hasChapo()) {
            final TextView view = (TextView)this.getView().findViewById(R.id.chapo);
            view.setText(Html.fromHtml(result.getChapo()));
            view.setVisibility(View.VISIBLE);
        }

        if (result.hasImage()) {

            final ImageView imageView =  (ImageView)NewsDetailsFragment.this.getView().findViewById(R.id.img);
            imageView.setVisibility(View.VISIBLE);

            final AsyncTask<URL, Void, Bitmap> asyncTask = new AsyncTask<URL,Void, Bitmap>(){

                @Override
                protected Bitmap doInBackground(final URL... urls) {
                    try {
                        final Sofoot application = (Sofoot)NewsDetailsFragment.this.getActivity().getApplication();
                        final LruCache<URL, Bitmap> cache = application.getBitmapCache();

                        Bitmap bitmap = cache.get(urls[0]);

                        if (bitmap == null) {
                            Log.d(NewsDetailsFragment.LOG_TAG, "Bitmap for url '" + urls[0].toString() + "' is not cached");
                            bitmap = BitmapFactory.decodeStream(urls[0].openStream());
                            cache.put(urls[0], bitmap);
                        } else {
                            Log.d(NewsDetailsFragment.LOG_TAG, "Bitmap for url '" + urls[0].toString() + "' is cached");
                        }

                        return bitmap;
                    } catch (final IOException ioe) {
                        Log.wtf(NewsDetailsFragment.LOG_TAG, ioe);
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(final Bitmap result) {

                    final View view = NewsDetailsFragment.this.getView();

                    if ((result != null) && (view != null) && !NewsDetailsFragment.this.isDetached() && !NewsDetailsFragment.this.isRemoving()) {
                        final float ratio = (float)view.getWidth() / (float)result.getWidth();
                        Log.d(NewsDetailsFragment.LOG_TAG, "Ratio : " + ratio);

                        final int width =  (int)(result.getWidth() * ratio);
                        final int height =  (int)(result.getHeight() * ratio);

                        imageView.getLayoutParams().width = width;
                        imageView.getLayoutParams().height = height;

                        imageView.setImageBitmap(result);
                    } else {
                        imageView.setVisibility(View.INVISIBLE);
                    }
                }

            };

            asyncTask.execute(result.getImage(this.getActivity().getWindowManager().getDefaultDisplay()));

            if (result.hasLegende()) {
                final TextView legende = (TextView)this.getView().findViewById(R.id.legende);
                legende.setText(Html.fromHtml(result.getLegende().toUpperCase()));
                legende.setVisibility(View.VISIBLE);
            }
        }

        ((TextView)this.getView().findViewById(R.id.texte)).setText(Html.fromHtml(result.getTexte().replace("\r\n", "<br/>")));

        if (result.hasAuteur()) {
            final TextView view = ((TextView)this.getView().findViewById(R.id.auteur));
            view.setText(Html.fromHtml(result.getAuteur()));
            view.setVisibility(View.VISIBLE);
        }

        this.getView().findViewById(android.R.id.empty).setVisibility(View.GONE);
        this.getView().findViewById(R.id.newsDetails).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(final Loader<News> loader) {
        // TODO Auto-generated method stub

    }



    @Override
    public void trackPageView(final Tracker easyTracker) {
        easyTracker.trackView("details_news/" + this.newsMeta.getTitre() + "/" + this.newsMeta.getId());
    }

}
