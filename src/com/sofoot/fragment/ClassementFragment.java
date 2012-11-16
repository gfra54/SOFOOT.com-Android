package com.sofoot.fragment;

import java.util.Iterator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Classement;
import com.sofoot.loader.ClassementLoader;

public class ClassementFragment extends Fragment
implements LoaderManager.LoaderCallbacks<Collection<Classement>>
{

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.classement, container, false);
    };

    @Override
    public Loader<Collection<Classement>> onCreateLoader(final int id, final Bundle args) {
        return new ClassementLoader(this.getActivity());
    }

    @Override
    public void onLoadFinished(final Loader<Collection<Classement>> loader, final Collection<Classement> result) {
        final TableLayout tableLayout = (TableLayout)this.getView().findViewById(android.R.id.content);

        final Iterator<Classement> i = result.iterator();

        while(i.hasNext()) {
            final Classement classement = i.next();

            final View row = this.getActivity().getLayoutInflater().inflate(R.layout.classement_row, null);

            ((TextView) row.findViewById(R.id.classement_rang)).setText(String.valueOf(classement.getDiff()));
            ((TextView) row.findViewById(R.id.classement_club)).setText(classement.getClub().getLibelle());
            ((TextView) row.findViewById(R.id.classement_points)).setText(String.valueOf(classement.getNbPoints()));
            //((TextView) row.findViewById(R.id.classement_journee)).setText(classement.get());

            tableLayout.addView(row);
        }

    }

    @Override
    public void onLoaderReset(final Loader<Collection<Classement>> loader) {
        // TODO Auto-generated method stub

    }
}
