package com.sofoot;

import android.app.Application;

public class Sofoot extends Application {

	public String getUserAgent()
	{
		return this.getString(R.string.app_name) + "/" + this.getString(R.string.app_version);
	}

}
