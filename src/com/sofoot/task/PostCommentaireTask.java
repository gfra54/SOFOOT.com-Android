package com.sofoot.task;

import java.util.ArrayList;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.fragment.NewsDetailsFragment;

public class PostCommentaireTask extends AsyncTask<String, Void, Void>
{
    final NewsDetailsFragment newsDetailsFragment;

    private final ProgressDialog progressDialog;

    private Exception lastException;

    public PostCommentaireTask(final NewsDetailsFragment newsDetailsFragment) {
        this.newsDetailsFragment = newsDetailsFragment;

        this.progressDialog = new ProgressDialog(newsDetailsFragment.getActivity());
        this.progressDialog.setMessage(newsDetailsFragment.getString(R.string.send_comment));
        this.progressDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        this.progressDialog.show();
    }

    @Override
    protected Void doInBackground(final String... messages) {
        try {
            final String message = messages[0];
            final Sofoot application = (Sofoot)this.newsDetailsFragment.getActivity().getApplication();

            final ArrayList<BasicNameValuePair> paramsGet = new ArrayList<BasicNameValuePair>();
            paramsGet.add(
                    new BasicNameValuePair(
                            this.newsDetailsFragment.getString(R.string.ws_api_key_name),
                            this.newsDetailsFragment.getString(R.string.ws_api_key_value)
                            )
                    );

            paramsGet.add(new BasicNameValuePair("mode", "message"));

            final ArrayList<BasicNameValuePair> paramsPost = new ArrayList<BasicNameValuePair>();
            paramsPost.add(new BasicNameValuePair("message", message));
            paramsPost.add(new BasicNameValuePair("session_id", application.getMember().getSessionId()));
            paramsPost.add(new BasicNameValuePair("id_article", String.valueOf(this.newsDetailsFragment.getNewsMeta().getId())));

            final String result = application.getWSGateway().
                    postData("/ws.php?" + URLEncodedUtils.format(paramsGet, "utf-8"), paramsPost);

            final JSONObject login = new JSONObject(result).getJSONObject("message");
            if (login.getBoolean("etat") == false) {
                throw new LoginTaskException(login.getString("message"));
            }
        } catch (final Exception e) {
            Log.wtf("PostCommentaireTask", e);
            this.lastException = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(final Void result) {
        this.progressDialog.dismiss();

        if (this.lastException != null) {
            Toast.makeText(
                    this.newsDetailsFragment.getActivity(),
                    ((this.lastException instanceof LoginTaskException) ?
                            this.lastException.getMessage() :
                                this.newsDetailsFragment.getString(R.string.loader_error)),
                                Toast.LENGTH_LONG
                    ).show();
            return;
        }

        this.newsDetailsFragment.resetComposer();
        this.newsDetailsFragment.loadAllCommentaires();
    }
}
