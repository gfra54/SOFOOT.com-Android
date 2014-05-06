package com.sofoot.task;

import java.util.ArrayList;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sofoot.R;
import com.sofoot.domain.model.Member;
import com.sofoot.gateway.WSGateway;

public class LoginTask extends AsyncTask<Member, Void, Void> {

    private final Context context;

    private final WSGateway wsGateway;

    private final LoginListener loginListener;

    private final ProgressDialog progressDialog;

    private Exception lastException;

    public LoginTask(final Context context, final WSGateway wsGateway, final LoginListener loginListener) {
        this.context = context;
        this.wsGateway = wsGateway;
        this.loginListener = loginListener;

        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setMessage(context.getString(R.string.login_in));
        this.progressDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        this.progressDialog.show();
    }

    @Override
    protected Void doInBackground(final Member... members) {
        try {
            final Member member = members[0];

            final ArrayList<BasicNameValuePair> paramsGet = new ArrayList<BasicNameValuePair>();
            paramsGet.add(new BasicNameValuePair(this.context.getString(R.string.ws_api_key_name), this.context
                    .getString(R.string.ws_api_key_value)));

            paramsGet.add(new BasicNameValuePair("mode", "login"));

            final ArrayList<BasicNameValuePair> paramsPost = new ArrayList<BasicNameValuePair>();
            paramsPost.add(new BasicNameValuePair("membre[login]", member.getLogin()));
            paramsPost.add(new BasicNameValuePair("membre[pass]", member.getPassword()));

            final String result = this.wsGateway.postData("/ws.php?" + URLEncodedUtils.format(paramsGet, "ISO-8859-1"),
                    paramsPost);

            final JSONObject login = new JSONObject(result).getJSONObject("login");
            if (login.getBoolean("etat") == false) {
                throw new LoginTaskException(login.getString("message"));
            }

            member.setSessionId(login.getString("session_id"));
            member.setLastSessionCheck(System.currentTimeMillis());

        } catch (final Exception e) {
            Log.wtf("LoginTask", e);
            this.lastException = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(final Void result) {
        this.progressDialog.dismiss();

        if (this.lastException != null) {
            this.loginListener.onLoginFailed(this.lastException);
        }
        else {
            this.loginListener.onLoginSuccess();
        }
    }
}
