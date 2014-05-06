package com.sofoot;

import java.net.URL;

import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.google.analytics.tracking.android.EasyTracker;
import com.sofoot.domain.model.Member;
import com.sofoot.gateway.WSGateway;
import com.sofoot.mapper.ClassementMapper;
import com.sofoot.mapper.CommentaireMapper;
import com.sofoot.mapper.CoteMapper;
import com.sofoot.mapper.LigueMapper;
import com.sofoot.mapper.LiveScoringMapper;
import com.sofoot.mapper.NewsMapper;
import com.sofoot.service.AdManager;

public class Sofoot extends Application {

    private String defaultWSUserAgent;

    private HttpHost defaultWSHttpHost;

    private NewsMapper newsMapper;

    private LigueMapper ligueMapper;

    private ClassementMapper classementMapper;

    private LiveScoringMapper liveScoringMapper;

    private CommentaireMapper commentaireMapper;

    private CoteMapper coteMapper;

    private WSGateway wsGateway;

    private LruCache<URL, Bitmap> bitmapCache;

    private Member member;

    private JSONObject options;

    private AdManager adManager;

    @Override
    public void onCreate() {
        super.onCreate();
        EasyTracker.getInstance().setContext(this);
    }

    public String getDefaultWSUserAgent() {
        if (this.defaultWSUserAgent == null) {
            this.defaultWSUserAgent = this.getString(R.string.app_name) + "/" + this.getString(R.string.app_version)
                    + " (Android; " + Build.DEVICE + "; " + Build.VERSION.RELEASE + "; " + Build.VERSION.SDK_INT + ")";
        }

        return this.defaultWSUserAgent;
    }

    public HttpHost getDefaultWSHttpHost() {
        if (this.defaultWSHttpHost == null) {
            this.defaultWSHttpHost = new HttpHost(this.getString(R.string.ws_hostname), this.getResources().getInteger(
                    R.integer.ws_port), this.getString(R.string.ws_schemme));
        }
        return this.defaultWSHttpHost;
    }

    public NewsMapper getNewsMapper() {
        if (this.newsMapper == null) {
            this.newsMapper = new NewsMapper(this.getWSGateway(), this.getString(R.string.ws_api_key_name),
                    this.getString(R.string.ws_api_key_value));
        }

        return this.newsMapper;
    }

    public LigueMapper getLigueMapper() {
        if (this.ligueMapper == null) {
            this.ligueMapper = new LigueMapper(this.getWSGateway(), this.getString(R.string.ws_api_key_name),
                    this.getString(R.string.ws_api_key_value));
        }

        return this.ligueMapper;
    }

    public ClassementMapper getClassementMapper() {
        if (this.classementMapper == null) {
            this.classementMapper = new ClassementMapper(this.getWSGateway(), this.getString(R.string.ws_api_key_name),
                    this.getString(R.string.ws_api_key_value));
        }

        return this.classementMapper;
    }

    public LiveScoringMapper getLiveScoringMapper() {
        if (this.liveScoringMapper == null) {
            this.liveScoringMapper = new LiveScoringMapper(this.getWSGateway(),
                    this.getString(R.string.ws_api_key_name), this.getString(R.string.ws_api_key_value));
        }

        return this.liveScoringMapper;
    }

    public CommentaireMapper getCommentaireMapper() {
        if (this.commentaireMapper == null) {
            this.commentaireMapper = new CommentaireMapper(this.getWSGateway(),
                    this.getString(R.string.ws_api_key_name), this.getString(R.string.ws_api_key_value));
        }

        return this.commentaireMapper;
    }

    public CoteMapper getCoteMapper() {
        if (this.coteMapper == null) {
            this.coteMapper = new CoteMapper(this.getWSGateway(), this.getString(R.string.ws_api_key_name),
                    this.getString(R.string.ws_api_key_value));
        }

        return this.coteMapper;
    }

    public WSGateway getWSGateway() {
        if (this.wsGateway == null) {
            this.wsGateway = new WSGateway((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE),
                    this.getDefaultWSUserAgent(), this.getDefaultWSHttpHost());

            this.wsGateway.setAppVersion(this.getString(R.string.app_version));
            this.wsGateway.setOsName("android");
            this.wsGateway.setOsVersion(Build.VERSION.RELEASE);

            this.wsGateway.setTimeTracker(EasyTracker.getTracker());
        }

        return this.wsGateway;
    }

    public LruCache<URL, Bitmap> getBitmapCache() {
        if (this.bitmapCache == null) {
            final int memClass = ((ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();

            final int cacheSize = (memClass / 8) * 1024 * 1024;

            Log.d("SO FOOT", "Lru Cache Size : " + cacheSize);

            this.bitmapCache = new LruCache<URL, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(final URL key, final Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        return this.bitmapCache;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (this.bitmapCache != null) {
            this.bitmapCache.evictAll();
        }
    }

    public boolean isConnected() {
        return this.getMember().isConnected();
    }

    public Member getMember() {
        if (this.member == null) {
            this.member = new Member();

            try {
                final SharedPreferences sp = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);

                this.member.setLogin(sp.getString("login", null));
                this.member.setSessionId(sp.getString("sessionId", null));
                this.member.setLastSessionCheck(sp.getLong("lastSessionCheck", 0));
            } catch (final Exception e) {
                this.member.setLogin(null);
                this.member.setSessionId(null);
                this.member.setLastSessionCheck(0);
            }
        }

        return this.member;
    }

    /**
     * Stock dans les préférences de l'application les données de la session du
     * membre, afin qu'il puisse être tjs connecté après la fermeture de
     * l'application
     * 
     * @param member
     */
    public void storeMemberSession(final Member member) {
        final SharedPreferences sp = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        final Editor editor = sp.edit();

        editor.putString("login", member.getLogin());
        editor.putString("sessionId", member.getSessionId());
        editor.putLong("lastSessionCheck", member.getLastSessionCheck());

        editor.commit();
    }

    public JSONObject getOptions() {
        return this.options;
    }

    public void setOptions(final JSONObject jsonObject) {
        this.options = jsonObject;
    }

    public JSONObject getOrangeOptions() throws JSONException {
        return this.options.getJSONObject("options").getJSONObject("orange");
    }

    public AdManager getAdManager() {
        if (this.adManager == null) {
            this.adManager = new AdManager(this);
        }

        return this.adManager;
    }

}
