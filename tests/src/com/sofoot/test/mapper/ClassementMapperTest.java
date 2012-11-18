package com.sofoot.test.mapper;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Classement;

public class ClassementMapperTest extends AndroidTestCase
{

    private Collection<Classement> collection;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.collection = ((Sofoot)this.getContext().getApplicationContext()).getClassementMapper().findAll();
    }

    public void testNotEmptyClassementCollection()
    {
        Log.d("CLASSEMENT_COLLECTION_SIZE", "" + this.collection.size());
        Assert.assertTrue(this.collection.size() > 0);
    }

}
