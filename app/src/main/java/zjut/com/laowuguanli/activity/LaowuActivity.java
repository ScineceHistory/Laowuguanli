package zjut.com.laowuguanli.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Date;

import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.adapter.LaoWuAdapter;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.db.LoaderDaoImpll;
import zjut.com.laowuguanli.util.GetUserTask;

public class LaowuActivity extends AdministerActivity {

    LaoWuAdapter adapter;
    LoaderDaoImpll mDao;

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
        saveFileName = "UserInfo(劳务管理).txt";

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(LaowuActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        datas = new ArrayList<User>();
        adapter = new LaoWuAdapter(LaowuActivity.this, datas);

        adapter.setOnItemClickListener(new LaoWuAdapter.OnItemClickListener() {
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

        mDao = new LoaderDaoImpll(this);
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

    @Override
    protected String extraOutputInfo() {
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                showHintInfo("取消扫描");
            } else {
                String url = result.getContents();
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