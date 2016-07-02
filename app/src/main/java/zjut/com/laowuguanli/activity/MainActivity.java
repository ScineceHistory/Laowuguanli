package zjut.com.laowuguanli.activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import zjut.com.laowuguanli.MyApplication;
import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.animation.AlphaInAnimation;
import zjut.com.laowuguanli.animation.SlideInLeftAnimation;
import zjut.com.laowuguanli.dialog.InputPasswordDialog;
import zjut.com.laowuguanli.fragment.ImageFragment;
import zjut.com.laowuguanli.util.Check;
import zjut.com.laowuguanli.util.Constants;
import zjut.com.laowuguanli.util.PicSave;
import zjut.com.laowuguanli.util.SelectPicUtil;

public class MainActivity extends BaseActivity {

    public static final String MY_WEB = "http://weibo.com/";
    public static final String GROUP_WEB = "http://www.cscec83.com/";

    private static final int RESULT_IMAGE = 100;    // 返回码：系统图库
    private static final int RESULT_CAMERA = 200;   // 返回码：相机
    private static final String IMAGE_TYPE = "image/*"; // IMAGE TYPE
    public static String TEMP_IMAGE_PATH = Environment  // Temp照片路径
            .getExternalStorageDirectory().getPath() + "/temp.png";
    public static final String DIALOG_IMAGE = "image";

    private FloatingActionButton fabLaowu;
    private FloatingActionButton fabWeigui;
    private FloatingActionButton fabQianzheng;
    private Button fabDt;
    TextView mTextViewTime;
    TextView mTextViewDate;
    TextView mTextViewTitleFlag;

    LinearLayout parentLayout;
    LinearLayout mLinearLayoutL;
    LinearLayout mLinearLayoutQ;
    LinearLayout mLinearLayoutW;
    LinearLayout mLinearLayoutDateTime;
    private long exitTime = 0;//两次退出时间间隔

    private DrawerLayout drawerLayout;
    private ImageView imageView;
    private ImageView headerImageView;

    private NavigationView navigationView;
    private TextView accountTv;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String myBitmap;
    private boolean isNewUser;

    boolean isShow = true;
    public static final int DURATION = 700;
    private android.view.animation.Interpolator mInterpolator;

    private String[] mCustomItems = new String[]{"本地图册", "相机拍照","查看原图"};   // 本地图册、相机选择

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            refreshTime();
            timeHandler.sendEmptyMessageDelayed(0,1000);
        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        initViews();
        timeHandler.sendEmptyMessage(0x111);

        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        final String format = sFormat.format(date);
        mTextViewDate.setText(format);

        //navigationView选中Item处理,为了代码整齐些，就放在一个函数里了
        handNavigationView();
        refreshAccountInfo();

    }

    private void showDT() {
        Animator showAnimatorA = new AlphaInAnimation(0,1).getHideAnimators(mLinearLayoutDateTime);
        Animator showAnimatorS = new SlideInLeftAnimation(this).getShowAnimator(mLinearLayoutDateTime);
        showAnimatorA.setDuration(DURATION);
        showAnimatorS.setDuration(DURATION);
        showAnimatorA.setInterpolator(mInterpolator);
        showAnimatorS.setInterpolator(mInterpolator);
        showAnimatorA.start();
        showAnimatorS.start();
    }

    private void hideDT() {
        Animator hideAnimatorA = new AlphaInAnimation(1,0).getHideAnimators(mLinearLayoutDateTime);
        Animator hideAnimatorS = new SlideInLeftAnimation(this).getHideAnimator(mLinearLayoutDateTime);


        hideAnimatorA.setDuration(DURATION);
        hideAnimatorS.setDuration(DURATION);
        hideAnimatorA.setInterpolator(mInterpolator);
        hideAnimatorS.setInterpolator(mInterpolator);
        hideAnimatorA.start();
        hideAnimatorS.start();
    }


    private void initViews() {

        parentLayout = (LinearLayout) findViewById(R.id.mainLayout);
        mLinearLayoutL = (LinearLayout) findViewById(R.id.line2);
        mLinearLayoutQ = (LinearLayout) findViewById(R.id.line1);
        mLinearLayoutW = (LinearLayout) findViewById(R.id.line3);
        mLinearLayoutDateTime = (LinearLayout) findViewById(R.id.current_Date_Time);

        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        imageView= (ImageView) findViewById(R.id.image);
        navigationView= (NavigationView) findViewById(R.id.navigation);

        mTextViewTime = (TextView) findViewById(R.id.times);
        mTextViewDate = (TextView) findViewById(R.id.dates);
        mTextViewTitleFlag = (TextView) findViewById(R.id.title_flag);

        mInterpolator = new AnticipateOvershootInterpolator();

        fabLaowu= (FloatingActionButton) findViewById(R.id.fab2);
        fabLaowu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputPasswordDialog(LaowuActivity.class);
                //startActivity(new Intent(MainActivity.this,LaowuActivity.class));
            }
        });

        fabWeigui= (FloatingActionButton) findViewById(R.id.fab3);
        fabWeigui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputPasswordDialog(WeiguiActivity.class);
            }
        });

        fabQianzheng= (FloatingActionButton) findViewById(R.id.fab1);
        fabQianzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,QianzhengActivity.class));
            }
        });

        fabDt = (Button) findViewById(R.id.fabDT);
        fabDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    hideDT();
                    fabDt.setText(getString(R.string.show_time));
                    isShow = false;
                } else {
                    showDT();
                    isShow = true;
                    fabDt.setText(getString(R.string.hide_time));
                }
            }
        });

    }


    /**
     * 弹出输入密码的对话框
     */
    private void showInputPasswordDialog(final Class cls) {
        final String password = getPassword();
        final InputPasswordDialog inputPasswordDialog = new InputPasswordDialog(MainActivity.this);

        inputPasswordDialog.setCallBack(new InputPasswordDialog.MyCallBack() {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(inputPasswordDialog.mInputPassWordEt.getText().toString())) {
                    showHintInfo("密码不能为空");
                } else {
                    assert password != null;
                    if (password.equals(inputPasswordDialog.mInputPassWordEt.getText().toString())) {
                        //进入防盗主界面
                        inputPasswordDialog.dismiss();
                        startActivity(new Intent(MainActivity.this,cls));
                    } else {
                        //对话框消失，弹出密码有误提示
                        inputPasswordDialog.dismiss();
                        showHintInfo("密码有误，请重新输入");
                    }
                }
            }

            @Override
            public void cancel() {
                inputPasswordDialog.dismiss();
            }
        });

        //让对话框显示
        inputPasswordDialog.show();
        inputPasswordDialog.setCancelable(true);
        showHintInfo("密码为登陆时的密码");
    }

    /**
     * 获取密码
     */
    private String getPassword() {
        String password = preferences.getString("password",null);
        if (TextUtils.isEmpty(password)) {
            return "";
        }
        return password;
    }

    @SuppressLint("DefaultLocale")
    private void refreshTime() {
        Calendar calendar = Calendar.getInstance();

        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));

        if (Integer.parseInt(hour) < 10) {
            hour = "0" + hour;
        }
        if (Integer.parseInt(minute) < 10) {
            minute = "0" + minute;
        }
        if (Integer.parseInt(second) < 10) {
            second = "0" + second;
        }

        mTextViewTime.setText(String.format("%s : %s : %s", hour, minute, second));
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void handNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //用于辨别此前是否已有选中条目
            MenuItem preMenuItem;
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //首先将选中条目变为选中状态 即checked为true,后关闭Drawer，以前选中的Item需要变为未选中状态
                if(preMenuItem!=null) {
                    preMenuItem.setChecked(false);
                }

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                preMenuItem = menuItem;

                //不同item对应不同图片
                switch (menuItem.getItemId()){
                    case R.id.nav_web:
                        launcherActivity(GroupWebActivity.class);
                        break;
                    case R.id.nav_profile:
                        goToXinLang(MainActivity.this,MY_WEB);
                        break;
                    case R.id.nav_relogin:
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        intent.putExtra("re_login",true);
                        startActivity(intent);
                        break;
                    case R.id.nav_share:
                        shareQuestion();
                        break;
                    case R.id.nav_exit:
                        finish();
                        break;
                    case R.id.nav_about:
                        drawerLayout.closeDrawers();
                        showApacheLicenseDialog();
                        break;
                    case R.id.nav_settings:
                        launcherActivity(PrefsActivity.class);
                        break;
                }
                return true;
            }
        });
    }

    private void launcherActivity(Class clz) {
        Intent intent = new Intent(MainActivity.this,clz);
        startActivity(intent);
    }

    private void goToXinLang(Context context, String url) {
        if (!MyApplication.getSharedPreferences()
                .getBoolean("using_xinlang_client?", false)) {
            openUsingWebView(context, url);
        } else if (Check.isXinLangClientInstalled()) {
            openUsingXinLangClient(context);
        } else {
            openUsingWebView(context, url);
        }
    }

    private void openUsingWebView(Context context, String url) {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (Check.isIntentSafe(browserIntent)) {
//            context.startActivity(browserIntent);
//        } else {
//            Toast.makeText(context, "无浏览器", Toast.LENGTH_SHORT).show();
//        }
        launcherActivity(PersonWebActivity.class);
    }

    private void openUsingXinLangClient(Context context) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(
                Constants.Information.XINLANG_PACKAGE_ID);
        if (intent != null)
            startActivity(intent);
    }



    private void showApacheLicenseDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("软件信息");
        builder.setCancelable(false);
        builder.setMessage("作者 @ScienceHistory\n\n时间 @2016年6月15号\n\n版本 @v1.0" +
                "\n\n类型 @实用工具\n\n备注 @功能待完善");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void refreshAccountInfo() {
        //直接在代码中加载头部布局
        View view= navigationView.inflateHeaderView(R.layout.drawer_header);
        accountTv = (TextView) view.findViewById(R.id.account_info);

        headerImageView = (ImageView) view.findViewById(R.id.headerImage);

        String accountName = preferences.getString("account","ScienceHistory");
        accountTv.setText(accountName);

        if (preferences.getString("headerImgName",null) != null) {
            headerImageView.setImageURI(Uri.parse(preferences.getString("headerImgName",null)));
        } else {
            headerImageView.setImageResource(R.mipmap.header);
        }

        headerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCustom();
            }
        });
    }

    // 显示选择系统图库 相机对话框
    private void showDialogCustom() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog
                .Builder(MainActivity.this);
        builder.setItems(mCustomItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (0 == which) {
                            // 本地图册
                            SelectPicUtil.getByAlbum(MainActivity.this);
                        } else if (1 == which) {
                            // 系统相机
                            SelectPicUtil.getByCamera(MainActivity.this);
                        } else if (2 == which) {
                            // 查看大图
                            checkFullPic();
                        }
                    }
                });
        builder.create().show();
    }

    private void checkFullPic() {
        FragmentManager fm = getSupportFragmentManager();
        myBitmap = preferences.getString("headerImgName",null);
        if (myBitmap != null) {
            ImageFragment fragment = ImageFragment.newInstance(myBitmap);
            fragment.show(fm,DIALOG_IMAGE);
        } else {
            showFirstHeaderImg();
        }
    }

    private void showFirstHeaderImg() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.header_view,null);
        //builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();
    }

    /**
     * 调用图库回调方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = SelectPicUtil.onActivityResult(this, requestCode, resultCode, data);
        try {
            myBitmap = PicSave.saveMyBitmap("science", 100, this, bitmap);
            editor = preferences.edit();
            editor.putString("headerImgName",myBitmap);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            headerImageView.setImageBitmap(bitmap);
        }
    }


    private void shareQuestion() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        //noinspection deprecation
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT,"分享一款小小工具软件给你啊！！！（可加入软件的下载地址）");
        startActivity(Intent.createChooser(share,"分享至"));
    }

    /**
     * Android中的“再按一次返回键退出程序“的代码
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000 ){
                showHintInfo("再按一次退出程序","切换账号");
                exitTime = System.currentTimeMillis();
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            } else {
                System.exit(0);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showHintInfo(String leftStr, String rightStr) {
        Snackbar.make(parentLayout,leftStr,Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setAction(rightStr, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        intent.putExtra("re_login",true);
                        startActivity(intent);
                    }
                })
                .show();
    }

    private void showHintInfo(String leftStr) {
        Snackbar.make(parentLayout,leftStr,Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .show();
    }

}
