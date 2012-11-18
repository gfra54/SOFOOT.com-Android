package com.sofoot.test.mapper;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.Rencontre;

public class ResultatMapperTest extends AndroidTestCase
{

    private Criteria criteria;

    private Collection<Rencontre> collection;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.criteria = Criteria.defaultCriteria();
        this.criteria.setParam("ligue", "L1");
        this.criteria.setParam("j", "12");
        this.collection = ((Sofoot)this.getContext().getApplicationContext()).getResultatMapper().findAll(this.criteria);
    }

    public void testIfCollectionNotEmpty()
    {
        Log.d("COLLECTION_SIZE", "" + this.collection.size());
        Assert.assertTrue(this.collection.size() > 0);
    }

}
