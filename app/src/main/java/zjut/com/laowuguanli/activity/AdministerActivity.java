package zjut.com.laowuguanli.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
    public boolean isOut = false;
    protected String titleName;
    protected String saveFileName;
    protected SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

    public void saveUserInfo(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),saveFileName);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                FileOutputStream fos = null;
                BufferedWriter write = null;
                try {
                    fos = new FileOutputStream(file,true);
                    write = new BufferedWriter(new OutputStreamWriter(fos));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Log.d("sh",user.toString());
                    if (write != null) {
                        if (isOut) {
                            write.write("(出)"+ splitName(user.toString()) + "\r\n" + user.getDate() + extraOutputInfo() +"\r\n\r\n");
                        } else {
                            write.write("(进)"+ splitName(user.toString()) + "\r\n" + user.getDate() + extraOutputInfo() +"\r\n\r\n");
                        }
                        write.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                        if (write != null) {
                            write.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private String splitName(String userInfo) {
        String[] fields = userInfo.split("，");
        return fields[0];
    }

    protected abstract String extraOutputInfo();


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
