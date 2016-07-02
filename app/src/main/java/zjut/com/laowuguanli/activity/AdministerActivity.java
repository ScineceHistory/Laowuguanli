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
    public boolean isOut = false;
    protected String titleName;
    protected String saveFileName;
    protected SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    protected Map<String,String> mInfoMap = new HashMap<>();
    protected Map<String,String> mWeiGuiMap = new HashMap<>();

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
                            String name = splitName(user.toString());
                            Log.d("Science", "run: "+name);
                            Log.d("Science", "run: "+mInfoMap.containsKey(name));
                            if (mInfoMap.containsKey(name)) {
                                String allInfo = mInfoMap.get(name) +
                                        "\r\n" + "出 " + user.getDate() + mWeiGuiMap.get(name) +"\r\n\r\n";
                                Log.d("Science", "run: "+allInfo);
                                write.write(allInfo);
                            }
                        } else {
                            String name = splitName(user.toString());
                            String weigui = extraOutputInfo();
                            String info = splitName(user.toString()) + "\r\n" + "进 " + user.getDate();
                            Log.d("Science",name);
                            Log.d("Science",info);
                            mInfoMap.put(name,info);
                            mWeiGuiMap.put(name,weigui);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
