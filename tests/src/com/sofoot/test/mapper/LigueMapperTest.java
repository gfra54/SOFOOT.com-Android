package com.sofoot.test.mapper;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;

public class LigueMapperTest extends AndroidTestCase
{

    private Collection<Ligue> collection;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.collection = ((Sofoot)this.getContext().getApplicationContext()).getLigueMapper().findAll();
    }

    public void testNotEmptyLigueCollection()
    {
        Log.d("COLLECTION_SIZE", "" + this.collection.size());
        Assert.assertTrue(this.collection.size() > 0);
    }

    public void testIfL1Exists()
    {
        for(final Ligue ligue : this.collection) {
            if (ligue.getId().equals("L1")) {
                Assert.assertTrue(true);
                return;
            }
        }

        Assert.assertTrue(false);
    }

}
