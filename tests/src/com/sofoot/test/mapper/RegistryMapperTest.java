package com.sofoot.test.mapper;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.sofoot.mapper.RegistryMapper;

public class RegistryMapperTest extends AndroidTestCase {

	public void testSingleton()
	{
		Assert.assertEquals(RegistryMapper.getInstance(), RegistryMapper.getInstance());
	}

	/*
	public void testGetNewsMapper()
	{
		Assert.assertTrue(RegistryMapper.getNewsMapper() instanceof NewsMapper);
	}
	 */
}
