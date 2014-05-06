
package com.sofoot.task;

import java.util.ArrayList;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sofoot.R;
import com.sofoot.domain.model.Member;
import com.sofoot.gateway.WSGateway;

public class LogoutTask extends AsyncTask<Member, Void, Void>
{
    private final Context context;

    private final LogoutListener logoutListener;

    private final ProgressDialog progressDialog;

    private Exception lastException;

    private final WSGateway wsGateway;

    public LogoutTask(final Context context, final WSGateway wsGateway, final LogoutListener logoutListener) {
        this.context = context;
        this.wsGateway = wsGateway;
        this.logoutListener = logoutListener;

        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setMessage(context.getString(R.string.logout_in));
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
            paramsGet.add(
                    new BasicNameValuePair(
                            this.context.getString(R.string.ws_api_key_name),
                            this.context.getString(R.string.ws_api_key_value))
                    );

            paramsGet.add(new BasicNameValuePair("mode", "logout"));

            final ArrayList<BasicNameValuePair> paramsPost = new ArrayList<BasicNameValuePair>();
            paramsPost.add(new BasicNameValuePair("session_id", member.getSessionId()));

            final String result =
                    this.wsGateway.postData("/ws.php?" + URLEncodedUtils.format(paramsGet, "utf-8"), paramsPost);

            final JSONObject login = new JSONObject(result).getJSONObject("logout");
            if (login.getBoolean("etat") == false) {
                throw new LogoutTaskException(login.getString("message"));
            }

            member.setSessionId(null);
            member.setLastSessionCheck(0);

        } catch (final Exception e) {
            this.lastException = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(final Void result) {
        this.progressDialog.dismiss();

        if (this.lastException != null) {
            this.logoutListener.onLogoutFailed(this.lastException);
        } else {
            this.logoutListener.onLogoutSuccess();
        }
    }
}
