package zjut.com.laowuguanli.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.adapter.MyAdapterL;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.db.LoaderDaoImpl;
import zjut.com.laowuguanli.util.GetUserTask;

public class LaowuActivity extends AdministerActivity {

    MyAdapterL adapter;
    LoaderDaoImpl mDao;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        users = mDao.getUsers();
        if (users != null) {
            datas.addAll(users);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_laowu;
    }

    @Override
    protected void initViews() {

        titleName = "劳务管理";

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(LaowuActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        datas = new ArrayList<User>();
        adapter = new MyAdapterL(LaowuActivity.this, datas);

        adapter.setOnItemClickListener(new MyAdapterL.OnItemClickListener() {
            @Override
            public void onItemLongClickListener(View itemView, int position) {
                adapter.deleteItem(position);
            }
        });

        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍候...");
        progressDialog.setMessage("正在处理中...");
        progressDialog.setCancelable(true);

        mDao = new LoaderDaoImpl(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(LaowuActivity.this);
                integrator.setOrientationLocked(false);
                integrator.setCaptureActivity(ScanActivity.class);
                integrator.initiateScan();
            }
        });
    }

    public void saveUserInfo(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "UserInfo(劳务管理).txt");
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
                    fos = new FileOutputStream(file, true);
                    write = new BufferedWriter(new OutputStreamWriter(fos));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Log.d("sh", user.toString());
                    if (write != null) {
                        if (isOut) {
                            write.write("(出)" + user.toString() + "\n\n");
                        } else {
                            write.write("(进)" + user.toString() + "\n\n");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                showHintInfo("取消扫描");
            } else {
                String url = result.getContents().toString();
                GetUserTask task = new GetUserTask(LaowuActivity.this);
                task.execute(url);
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void handlerUser(User user) {
        if (user == null || user.getName() == null) {
            hiddenDialog();
            showHintInfo("扫码失败");
            return;
        }

        if (datas.contains(user)) {
            isOut = true;
            Date date = new Date();
            SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setDate(sFormat.format(date));
            saveUserInfo(user);
            mDao.deleteUser(user.getName());
            datas.remove(user);
        } else {
            isOut = false;
            datas.add(user);
            if (!mDao.isExists(user.getName(), user.getDate())) {
                mDao.insertUser(user);
                saveUserInfo(user);
            }
        }
        adapter.notifyDataSetChanged();
        hiddenDialog();
    }
}