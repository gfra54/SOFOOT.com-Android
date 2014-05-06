package com.sofoot.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.domain.model.Member;
import com.sofoot.task.LoginListener;
import com.sofoot.task.LoginTask;
import com.sofoot.task.LoginTaskException;

public class LoginActivity extends SofootActivity implements OnClickListener, LoginListener, OnEditorActionListener {

    private EditText login;

    private EditText password;

    static public final int LOGIN_SUCCESS = 1;
    static public final int LOGIN_FAILED = -1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.login_activity);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.findViewById(R.id.btn_creer_compte).setOnClickListener(this);
        this.findViewById(R.id.login).setOnClickListener(this);

        this.login = (EditText) this.findViewById(R.id.identifiant);
        this.password = (EditText) this.findViewById(R.id.mdp);
        this.password.setOnEditorActionListener(this);
        this.password.setTypeface(Typeface.DEFAULT);

        ((TextView) this.findViewById(R.id.password_lost)).setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.setResult(LoginActivity.LOGIN_FAILED);
            this.finish();
            return true;
        }

        return false;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btn_creer_compte:
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(this.getResources().getString(
                        R.string.url_creer_compte))));
                break;

            case R.id.login:
                this.login();
                break;
        }
    }

    @Override
    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {

        if (v == this.password && actionId == EditorInfo.IME_ACTION_DONE) {
            this.login();
            return true;
        }

        return false;
    }

    private void login() {
        try {
            final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.password.getWindowToken(), 0);

            final Sofoot application = (Sofoot) this.getApplication();

            final Member member = application.getMember();
            member.setLogin(this.login.getText().toString());
            member.setPassword(this.password.getText().toString());

            new LoginTask(this, application.getWSGateway(), this).execute(member);

        } catch (final Exception e) {
            Log.wtf("LoginActivity", e);
            Toast.makeText(this, this.getString(R.string.unexpected_exception), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoginFailed(final Exception exception) {

        this.setResult(LoginActivity.LOGIN_FAILED);

        Toast.makeText(
                this,
                ((exception instanceof LoginTaskException) ? exception.getMessage() : this
                        .getString(R.string.login_error)), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccess() {
        final Sofoot application = (Sofoot) this.getApplication();

        application.storeMemberSession(application.getMember());

        this.setResult(LoginActivity.LOGIN_SUCCESS);
        this.finish();
    }

}
