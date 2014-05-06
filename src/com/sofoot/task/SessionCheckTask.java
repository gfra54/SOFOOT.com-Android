
package com.sofoot.task;

import java.util.ArrayList;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.domain.model.Member;

public class SessionCheckTask extends AsyncTask<Member, Void, Boolean>
{

    private final SessionCheckListener listener;
    private final Sofoot sofoot;

    public SessionCheckTask(final Sofoot sofoot, final SessionCheckListener listener) {
        this.sofoot = sofoot;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(final Member... members) {
        try {
            final Member member = members[0];

            final ArrayList<BasicNameValuePair> paramsGet = new ArrayList<BasicNameValuePair>();
            paramsGet.add(
                    new BasicNameValuePair(
                            this.sofoot.getResources().getString(R.string.ws_api_key_name),
                            this.sofoot.getResources().getString(R.string.ws_api_key_value)
                    )
                    );

            paramsGet.add(new BasicNameValuePair("mode", "login"));

            final ArrayList<BasicNameValuePair> paramsPost = new ArrayList<BasicNameValuePair>();
            paramsPost.add(new BasicNameValuePair("session_id", member.getSessionId()));

            final String result = this.sofoot.getWSGateway().
                    postData("/ws.php?" + URLEncodedUtils.format(paramsGet, "utf-8"), paramsPost);

            final JSONObject login = new JSONObject(result).getJSONObject("login");
            if (login.getBoolean("etat") == false) {
                member.setSessionId(null);
                this.sofoot.storeMemberSession(member);
                return false;
            }

            return true;

        } catch (final Exception e) {
            Log.wtf("SessionCheckTask", e);
        }

        return false;
    }

    @Override
    protected void onPostExecute(final Boolean result) {
        this.listener.onSessionChecked(result.booleanValue());
    }
}
