package zjut.com.laowuguanli.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.adapter.WeiGuiAdapter;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.bean.UserWorkInfo;
import zjut.com.laowuguanli.db.LoaderDaoImplWorkW;
import zjut.com.laowuguanli.db.LoaderDaoImplw;
import zjut.com.laowuguanli.util.Constants;
import zjut.com.laowuguanli.util.GetUserTaskW;

public class WeiguiActivity extends AdministerActivity {

    WeiGuiAdapter adapter;
    LoaderDaoImplw mDao;
    MaterialSpinner spinner;
    String weiguiInfo = "迟到";

    LoaderDaoImplWorkW mDaoImplWorkW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initMenu();

        users = mDao.getUsers();
        if (users != null) {
            datas.addAll(users);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_weigui;
    }

    protected String extraOutputInfo() {
        return  "违规类型：" + weiguiInfo;
    }

    @Override
    protected void initViews() {

        titleName = "违规管理";
        saveFileName = "UserInfo(违规管理).txt";

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(WeiguiActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        datas = new ArrayList<>();
        adapter = new WeiGuiAdapter(WeiguiActivity.this, datas);

        adapter.setOnItemClickListener(new WeiGuiAdapter.OnItemClickListener() {
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

        mDao = new LoaderDaoImplw(this);
        mDaoImplWorkW = new LoaderDaoImplWorkW(this);

        fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spinner.setVisibility(View.GONE);
                IntentIntegrator integrator = new IntentIntegrator(WeiguiActivity.this);
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
                showHintInfo("取消扫描");
            } else {
                String url = result.getContents();
                GetUserTaskW task = new GetUserTaskW(WeiguiActivity.this);
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
            user.setIsOut(1);
            saveUserInfo(user);
            mDao.deleteUser(user.getName());
            datas.remove(user);
        } else {
            user.setIsOut(0);
            datas.add(user);
            if (!mDao.isExists(user.getName(),user.getDate())) {
                mDao.insertUser(user);
                saveUserInfo(user);
            }
        }

        adapter.notifyDataSetChanged();
        hiddenDialog();
    }

    public void saveUserInfo(final User user) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                File dirFile = new File(Constants.GUANLI_DIR);
                if(!dirFile.exists()){
                    dirFile.mkdir();
                }
                File file = new File(dirFile,saveFileName);
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
                    if (write != null) {
                        if (user.getIsOut() != 0) {
                            Log.d("Science", "run: "+user.getIsOut());
                            String name = splitName(user.toString());
                            UserWorkInfo workInfo = mDaoImplWorkW.getUser(name);
                            //isExit = mPreferences.getBoolean("isExit",false);
                            if (mDaoImplWorkW.isExists(name)) {
                                String outInfo = "出 " + user.getDate();
                                Log.d("Science", "run: ============" + outInfo);
                                workInfo.setOutInfo(outInfo);
                                mDaoImplWorkW.deleteUser(name);
                                mDaoImplWorkW.insertUser(workInfo);
                                write.write(workInfo.toString());
                                mDaoImplWorkW.deleteUser(name);
                            }
                        } else {

                            Log.d("Science", "run: "+user.getIsOut());
                            String name = splitName(user.toString());
                            String weigui = extraOutputInfo() + "\r\n";
                            Log.d("Science", "run: ************"+weigui);
                            String inInfo = "进 " + user.getDate();
                            Log.d("Science", "run: "+inInfo);

                            UserWorkInfo workInfo = new UserWorkInfo();
                            workInfo.setName(name);
                            workInfo.setInInfo(inInfo);
                            workInfo.setWeiguiInfo(weigui);
                            workInfo.setOutInfo("");
                            mDaoImplWorkW.insertUser(workInfo);
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

    private void initMenu() {
        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("迟到", "早退", "上班喝酒", "不听指挥", "不戴安全帽", "不按规程操作");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                weiguiInfo = "";
                switch (position) {
                    case 0:
                        weiguiInfo = item;
                        break;
                    case 1:
                        weiguiInfo = item;
                        break;
                    case 2:
                        weiguiInfo = item;
                        break;
                    case 3:
                        weiguiInfo = item;
                        break;
                    case 4:
                        weiguiInfo = item;
                        break;
                    case 5:
                        weiguiInfo = item;
                        break;
                }
            }
        });
    }
}
