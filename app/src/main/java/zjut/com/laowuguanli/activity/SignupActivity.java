package zjut.com.laowuguanli.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.bean.Account;
import zjut.com.laowuguanli.db.LoaderDaoImplLog;
import zjut.com.laowuguanli.db.LoaderDaoLog;

public class SignupActivity extends BaseActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name) EditText _nameText;
    //@Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    private LoaderDaoLog mDaoLog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        mDaoLog = new LoaderDaoImplLog(SignupActivity.this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                signTologin();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("创建账号中...");
        progressDialog.show();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                        finish();
                    }
                }, 2000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Account account = new Account();
        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();
        account.setAccount(name);
        account.setPassword(password);
        mDaoLog.insertUser(account);
        signTologin();
    }

    private void signTologin() {
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        intent.putExtra("isSignup",true);
        startActivity(intent);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "注册失败(用户或已存在)", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        //String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (mDaoLog.isExists(name,password)) {
            _nameText.setError("该用户已存在");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (name.isEmpty() || name.length() < 1) {
            _nameText.setError("至少1个字符");
            valid = false;
        } else {
            _nameText.setError(null);
        }

//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("请输入合法的邮箱");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }

        if (password.isEmpty() || password.length() < 1) {
            _passwordText.setError("至少1个字符");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        signTologin();
    }
}
