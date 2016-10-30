package zjut.com.laowuguanli.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import zjut.com.laowuguanli.bean.User;

/**
 * Created by ScienceHistory on 16/6/20.
 * 管理页面的基类Activity
 */

public abstract class AdministerActivity extends BaseActivity {

    protected RecyclerView recyclerView;
    protected ProgressDialog progressDialog;
    protected FloatingActionButton fab;
    protected List<User> datas;
    protected List<User> users;
    protected String titleName;
    protected String saveFileName;
    protected SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    protected Map<String,String> mInfoMap = new HashMap<>();
    protected Map<String,String> mWeiGuiMap = new HashMap<>();

    protected SharedPreferences mPreferences;
    protected boolean isExit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(getLayoutResId());

        initViews();
        initActionBar();
    }

    protected abstract void initViews();

    private void initActionBar() {
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(titleName);
    }

    /*获取布局活动Id*/
    protected abstract int getLayoutResId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showDialog() {
        progressDialog.show();
    }

    public void hiddenDialog() {
        progressDialog.dismiss();
    }

    public void showHintInfo(String info) {
        Snackbar.make(recyclerView,info,Snackbar.LENGTH_LONG)
                .show();
    }
}
