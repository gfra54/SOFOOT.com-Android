package com.sofoot.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;

import com.sofoot.R;
import com.sofoot.SplashscreenActivity;

public class SplashscreenActivityTest extends ActivityInstrumentationTestCase2<SplashscreenActivity> {

	private SplashscreenActivity mActivity;

	public SplashscreenActivityTest() {
		super(SplashscreenActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.mActivity = this.getActivity();
	}

	public void testLogo()
	{
		final View logo = this.mActivity.findViewById(R.id.splashscreenLogo);
		Assert.assertTrue(logo instanceof ImageView);
	}
}
