package com.sofoot.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.sofoot.R;

abstract public class SofootActivity extends SherlockFragmentActivity {
    protected View headerNextButton;
    protected View headerPrevButton;

    protected View headerUpdatedTime;

    private SimpleDateFormat dateFormat;
    private final Date date = new Date();

    protected void onCreate(final Bundle bundle, final int layoutResID) {
        super.onCreate(bundle);

        this.setContentView(layoutResID);

        this.getSupportActionBar().setHomeButtonEnabled(true);

        this.dateFormat = new SimpleDateFormat(this.getString(R.string.updated_datetime_format), Locale.FRANCE);

        this.headerUpdatedTime = this.findViewById(R.id.headerUpdatedTimeView);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            final Intent intent = new Intent(SofootActivity.this.getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            SofootActivity.this.startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance().activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance().activityStop(this);
    }

    public void showHeaderUpdatedTime() {
        if (this.headerUpdatedTime != null) {
            this.headerUpdatedTime.setVisibility(View.VISIBLE);
        }
    }

    public void setHeaderUpdatedTime(final long time) {
        if (this.headerUpdatedTime != null) {
            this.showHeaderUpdatedTime();
            this.date.setTime(time);
            ((TextView) this.headerUpdatedTime).setText(this.dateFormat.format(this.date));
        }
    }
}
