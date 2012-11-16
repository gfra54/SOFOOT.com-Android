package com.sofoot.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        return new NewsLoader(this.getActivity(), this.getActivity().getIntent().getExtras().getInt("id"));
    }

    @Override
    public void onLoadFinished(final Loader<News> loader, final News result) {
        Log.d(NewsFragment.MY_LOG_TAG, "onLoadFinish : " + result);

        /*
        if (this.newsLoader.getLastException() != null) {
            Toast.makeText(this.getActivity(), this.getString(R.string.newsloader_error), Toast.LENGTH_LONG).show();
        }*/

        ((TextView)this.getView().findViewById(R.id.surtitre)).setText(result.getTitre());
    }

    @Override
    public void onLoaderReset(final Loader<News> loader) {
        // TODO Auto-generated method stub

    }


}
