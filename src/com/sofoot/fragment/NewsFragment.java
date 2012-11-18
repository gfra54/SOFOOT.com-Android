package com.sofoot.fragment;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sofoot.R;
import com.sofoot.domain.model.News;
import com.sofoot.loader.NewsLoader;

public class NewsFragment extends Fragment
implements LoaderManager.LoaderCallbacks<News>
{

    final static private String MY_LOG_TAG = "NewsFragment";

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment, container, false);
    }

    @Override
    public Loader<News> onCreateLoader(final int id, final Bundle args) {
        return new NewsLoader(this.getActivity(), this.getArguments().getInt("id"));
    }

    @Override
    public void onLoadFinished(final Loader<News> loader, final News result) {
        Log.d(NewsFragment.MY_LOG_TAG, "onLoadFinish : " + result);

        if (((NewsLoader)loader).getLastException() != null) {
            Toast.makeText(this.getActivity(), this.getString(R.string.newsloader_error), Toast.LENGTH_LONG).show();
            return;
        }

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        if (result.hasSoustitre()) {
            final TextView view = ((TextView)this.getView().findViewById(R.id.surtitre));
            view.setText(result.getSurtitre());
            view.setVisibility(View.VISIBLE);
        }

        ((TextView)this.getView().findViewById(R.id.titre)).setText(result.getTitre());
        ((TextView)this.getView().findViewById(R.id.publication)).setText(simpleDateFormat.format(result.getPublication()));

        if (result.hasSoustitre()) {
            final TextView view = ((TextView)this.getView().findViewById(R.id.soustitre));
            view.setText(result.getSoustitre());
            view.setVisibility(View.VISIBLE);
        }

        if (result.hasSoustitre()) {
            final TextView view = ((TextView)this.getView().findViewById(R.id.chapo));
            view.setText(result.getChapo());
            view.setVisibility(View.VISIBLE);
        }

        //((TextView)this.getView().findViewById(R.id.legende)).setText(result.getLegende());

        ((TextView)this.getView().findViewById(R.id.texte)).setText(Html.fromHtml(result.getTexte().replace("\r\n", "<br/>")));

        if (result.hasSoustitre()) {
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
