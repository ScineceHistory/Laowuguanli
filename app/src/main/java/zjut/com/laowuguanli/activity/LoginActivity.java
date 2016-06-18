package zjut.com.laowuguanli.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.db.LoaderDaoImplLog;
import zjut.com.laowuguanli.db.LoaderDaoLog;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private LoaderDaoLog mDaoLog;

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;
    @Bind(R.id.remember_pass)
    CheckBox rememberPassWord;
    @Bind(R.id.auto_login)
    CheckBox autoLogin;

    private boolean isReLogin;
    private boolean isSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mDaoLog = new LoaderDaoImplLog(LoginActivity.this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        isReLogin = getIntent().getBooleanExtra("re_login",false);
        isSignup = getIntent().getBooleanExtra("isSignup",false);

        if (isSignup || isReLogin) {
            _emailText.setText("");
            _passwordText.setText("");
            rememberPassWord.setChecked(false);
            autoLogin.setChecked(false);
            Log.d("Science", "onCreate: "+"ssss");
        }


        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        dataRestoreByPreference();
        if (preferences.getBoolean("isFirstLogin",true)) {
            editor = preferences.edit();
            editor.putBoolean("isFirstLogin",false);
            editor.apply();
            showApacheLicenseDialog();
        }
    }

    private void showApacheLicenseDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警示信息");
        builder.setCancelable(false);
        builder.setMessage("• 新用户请先注册\n\n• 登录密码未使用加密算法，为了安全，请不要使用常用的密码");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dataRestoreByPreference() {
        Boolean isRemember = preferences.getBoolean("remember_password",false);
        Boolean isAutoLogin = preferences.getBoolean("auto_login",false);
        String account = preferences.getString("account","");
        String password = preferences.getString("password","");
        if (isRemember && !isReLogin && !isSignup) {
            // 将账号和密码都设置到文本框中
            _emailText.setText(account);
            _passwordText.setText(password);
            rememberPassWord.setChecked(true);//记住密码的状态也需要还原
        }
        if (isAutoLogin && !isReLogin && !isSignup) {
            autoLogin.setChecked(true);//记住密码的状态也需要还原
            if (isReLogin || isSignup) {
                return;
            }
            login();
        }

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

//        editor = preferences.edit();
//        editor.putBoolean("remember_password",rememberPassWord.)


        _loginButton.setEnabled(false);

        View view = getLayoutInflater().inflate(R.layout.loading_progress_dialog,null);
        TextView loadTv = (TextView) view.findViewById(R.id.load_text);
        loadTv.setText("认证中");
        Button loadBtn = (Button) view.findViewById(R.id.cancel_loading);

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        builder.setView(view);
        final AlertDialog progressDialog = builder.create();

        final Handler mHandler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                // On complete call either onLoginSuccess or onLoginFailed
                onLoginSuccess();
                // onLoginFailed();
                progressDialog.dismiss();
                finish();
            }
        };

        progressDialog.show();
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.dismiss();
                mHandler.removeMessages(0x111);
                _loginButton.setEnabled(true);
            }
        });

        mHandler.sendEmptyMessageDelayed(0x111,2000);

        // TODO: Implement your own authentication logic here.
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        String account = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        boolean isSuccess = mDaoLog.isExists(account,password);
        boolean isRemember = rememberPassWord.isChecked();
        boolean isAutoLogin = autoLogin.isChecked();
        // 如果账号是science且密码是123,就认为登录成功
        if (isSuccess) {
            editor = preferences.edit();
            if (isRemember) {
                // 检查复选框是否被选中
                editor.putBoolean("remember_password",true);
                editor.putString("account",account);
                editor.putString("password",password);
                if (isAutoLogin) {
                    editor.putBoolean("auto_login",true);
                }
            } else {
                editor.clear();
            }
            editor.apply();

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this,
                    "account or password is invalid",
                    Toast.LENGTH_SHORT).show();
            //状态清空
            _emailText.setText("");
            _passwordText.setText("");
            rememberPassWord.setChecked(false);
        }
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String account = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        boolean isSuccess = mDaoLog.isExists(account,password);

        if ( !isSuccess ) {
            _emailText.setError("请输入合法的账号");
            _passwordText.setError("请输入合法的密码");
            valid = false;
        } else {
            _emailText.setError(null);
            _passwordText.setError(null);
        }

        return valid;
    }
}
