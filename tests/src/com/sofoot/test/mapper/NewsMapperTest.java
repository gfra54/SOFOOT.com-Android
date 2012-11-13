package com.sofoot.test.mapper;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.News;

public class NewsMapperTest extends AndroidTestCase
{

    private Criteria criteria;

    private Collection<News> collection;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.criteria = Criteria.defaultCriteria();
        this.criteria.setLimit(8);
        this.collection = ((Sofoot)this.getContext().getApplicationContext()).getNewsMapper().findNews(this.criteria);
    }

    public void testNbNewsInCollection()
    {
        Log.d("COLLECTION_SIZE", "" + this.collection.size());
        Assert.assertEquals(this.criteria.getLimit(), this.collection.size());
    }

}
