package com.sofoot.loader;

import android.content.Context;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Commentaire;

public class CommentaireLoader extends SofootLoader<Collection<Commentaire>> {

    final static private String LOG_TAG = "CommentairesLoader";

    public CommentaireLoader(final Context context, final int newsId) {
        super(context);
        this.criteria.setParam("id", String.valueOf(newsId));
    }

    @Override
    public Collection<Commentaire> doLoad() throws SofootException {
        Log.d(CommentaireLoader.LOG_TAG, "loadInbackground");
        return ((Sofoot) this.getContext().getApplicationContext()).getCommentaireMapper().findAll(this.criteria);
    }
}
