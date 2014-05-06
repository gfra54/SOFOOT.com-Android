package com.sofoot.loader;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.sofoot.R;
import com.sofoot.Sofoot;

public class OptionsLoader extends AsyncTaskLoader<JSONObject> {

    final static public String LOG_TAG = "OptionsLoader";

    final private ArrayList<BasicNameValuePair> wsParams = new ArrayList<BasicNameValuePair>();

    public OptionsLoader(final Context context) {
        super(context);

        this.wsParams.add(new BasicNameValuePair(this.getContext().getString(R.string.ws_api_key_name), this
                .getContext().getString(R.string.ws_api_key_value)));
    }

    @Override
    protected void onStartLoading() {
        this.forceLoad();
    }

    @Override
    public JSONObject loadInBackground() {
        Log.d(OptionsLoader.LOG_TAG, "loadInBackground");

        try {
            return new JSONObject(((Sofoot) this.getContext().getApplicationContext()).getWSGateway().fetchData(
                    "/ws/options.json", this.wsParams));
        } catch (final Exception e) {
            return new JSONObject();
        }
    }

}
