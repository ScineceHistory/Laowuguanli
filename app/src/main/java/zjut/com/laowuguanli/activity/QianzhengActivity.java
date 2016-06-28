package zjut.com.laowuguanli.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Date;

import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.adapter.QianZhengAdapter;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.db.LoaderDaoImplq;
import zjut.com.laowuguanli.util.GetUserTaskQ;


public class QianzhengActivity extends AdministerActivity {

    QianZhengAdapter adapter;
    LoaderDaoImplq mDao;

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
        return R.layout.activity_qianzheng;
    }

    @Override
    protected String extraOutputInfo() {
        return "";
    }

    @Override
    protected void initViews() {

        titleName = "签证管理";
        saveFileName = "UserInfo(签证管理).txt";

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(QianzhengActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        datas = new ArrayList<>();
        adapter = new QianZhengAdapter(QianzhengActivity.this, datas);
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍候...");
        progressDialog.setMessage("正在处理中...");
        progressDialog.setCancelable(true);

        mDao = new LoaderDaoImplq(this);

        fab= (FloatingActionButton) findViewById(R.id.fabq);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(QianzhengActivity.this);
                integrator.setOrientationLocked(false);
                integrator.setCaptureActivity(ScanActivity.class);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                showHintInfo("取消扫描");
            } else {
                Log.d("MainActivity", "Scanned");
                String url = result.getContents();
                GetUserTaskQ task = new GetUserTaskQ(QianzhengActivity.this);
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
            user.setDate(sFormat.format(date));
            saveUserInfo(user);
            mDao.deleteUser(user.getName());
            datas.remove(user);
        } else {
            isOut = false;
            datas.add(user);
            if (!mDao.isExists(user.getName(),user.getDate())) {
                mDao.insertUser(user);
                saveUserInfo(user);
            }
        }
        adapter.notifyDataSetChanged();
        hiddenDialog();
    }
}
