package zjut.com.laowuguanli.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.util.Constants;
import zjut.com.laowuguanli.util.SavePicture;
import zjut.com.laowuguanli.util.URLSpanGroup;
import zjut.com.laowuguanli.util.URLSpanPic;
import zjut.com.laowuguanli.util.URLSpanTel;

public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.img_pic)
    ImageView imgPic;
    @Bind(R.id.erweima_img)
    ImageView erweimaImg;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.show_erweima)
    TextView showErweima;
    @Bind(R.id.hide_erweima)
    TextView hideErweima;
    @Bind(R.id.erweima)
    RelativeLayout erweima;
    @Bind(R.id.content_user)
    LinearLayout userContent;

    ScaleAnimation showScale;
    ScaleAnimation hideScale;

    String name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("工人信息");

        showScale = new ScaleAnimation(0,1,0,1,
                Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        showScale.setDuration(500);
        hideScale = new ScaleAnimation(1,0,1,0,
                Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        hideScale.setDuration(500);

        ButterKnife.bind(this);
        User user = (User) getIntent().getSerializableExtra("user");

        String picUrl = user.getPic();
        System.out.println(picUrl);
        Glide.with(this).load(picUrl).into(imgPic);

        tvName.setMovementMethod(LinkMovementMethod.getInstance());
        String[] split = user.getName().split("，");
        name = split[0];
        erweimaUrl = Constants.baseUrl + name + "2"; // ewm=2
        System.out.println(erweimaUrl);

        for (int i = 0; i < split.length; i++) {
            if ((!TextUtils.isEmpty(split[i])) && (i != split.length-1)) {
                String string = split[i];
                if (string.startsWith("身份证")) {
                    displayPicLinkItem(split[i], name , "身份证" , "1"); // sfz=1
                } else if (string.startsWith("操作证")) {
                    displayPicLinkItem(split[i], name , "操作证" , "3"); // czz=3
                } else if (string.startsWith("合格证")) {
                    displayPicLinkItem(split[i], name , "合格证" , "4"); // hgz=4
                } else if (string.startsWith("手机")) {
                    displayPhoneLinkItem(split[i],"手机");
                } else if (string.startsWith("队长手机")) {
                    displayPhoneLinkItem(split[i],"队长手机");
                } else {
                    displayNoLinkItem(split[i]);
                }
            } else {
                displayEndItem(split[i]);
            }
        }
        tvTime.setText(user.getDate());


        showErweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erweima.setVisibility(View.VISIBLE);
                userContent.setVisibility(View.GONE);
                erweima.setAnimation(showScale);
                userContent.setAnimation(hideScale);
                Glide.with(UserInfoActivity.this)
                        .load(Constants.baseUrl+name+"ewm").into(erweimaImg);
                showScale.start();
                hideScale.start();
            }
        });

        hideErweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erweima.setVisibility(View.GONE);
                userContent.setVisibility(View.VISIBLE);
                erweima.setAnimation(hideScale);
                userContent.setAnimation(showScale);
                showScale.start();
                hideScale.start();
            }
        });

        erweimaImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialogCustom();
                return true;
            }
        });
    }

    private String[] mCustomItems = new String[]{"保存到本地", "分享二维码"};
    private String erweimaUrl;
    private ProgressDialog mSaveDialog = null;
    private Bitmap mBitmap;
    private String mFileName;
    private String mSaveMessage;

    // 显示选择系统图库 相机对话框
    private void showDialogCustom() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog
                .Builder(UserInfoActivity.this);

        builder.setItems(mCustomItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (0 == which) {
                            // 保存二维码
                            mSaveDialog = ProgressDialog.show(UserInfoActivity.this, "保存图片", "图片正在保存中，请稍等...", true);
                            mSaveDialog.setCancelable(true);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        mFileName = name + ".jpg";
                                        // 方法1：取得的是byte数组, 从byte数组生成bitmap
                                        byte[] data = SavePicture.getImage(erweimaUrl);
                                        Log.d("Science", "run: "+data);
                                        if (data != null) {
                                            mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
                                        } else {
                                            Looper.prepare();
                                            Toast.makeText(UserInfoActivity.this, "Image error!", Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }
                                        SavePicture.saveFile(mBitmap, mFileName);
                                        mSaveMessage = "图片保存成功！";
                                    } catch (Exception e) {
                                        Looper.prepare();
                                        Toast.makeText(UserInfoActivity.this,"无法链接网络！", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                        mSaveMessage = "图片保存失败！";
                                        e.printStackTrace();
                                    }
                                    messageHandler.sendMessage(messageHandler.obtainMessage());
                                }
                            }).start();

                        } else if (1 == which) {
                            // 分享二维码
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_TEXT,"二维码地址:\n" + erweimaUrl);
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Constants.ERWEIMA_DIR,name + ".jpg")));
                            startActivity(Intent.createChooser(intent,"分享至@"));
                        }
                    }
                });
        builder.create().show();
    }

    @SuppressLint("HandlerLeak")
    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSaveDialog.dismiss();
            Toast.makeText(UserInfoActivity.this, mSaveMessage, Toast.LENGTH_SHORT).show();
        }
    };

    private void displayEndItem(String source) {
        String[] fields = source.split("：");
        Log.d("Science", "displayEndItem: --------------"+source);
        Log.d("Science", "displayEndItem: ＝＝＝＝＝＝＝＝＝ "+fields[0] + fields[1]);
        SpannableString text = new SpannableString(fields[0]);
        text.setSpan(new URLSpanGroup(Constants.baseUrl + fields[1],fields[1]),0, fields[0].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvName.append(text);
    }

    private void displayNoLinkItem(String text) {
        tvName.append(text);
        tvName.append("\n");
    }

    private void displayPhoneLinkItem(String s,String type) {
        String userNumber = s.substring(type.length()+1, s.length());
        SpannableString text = new SpannableString(s);
        text.setSpan(new URLSpanTel("tel:" + userNumber),type.length()+1, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvName.append(text);
        tvName.append("\n");
    }

    private void displayPicLinkItem(String source, String name, String userType, String picFlag) {
        SpannableString text = new SpannableString(source);
        text.setSpan(new URLSpanPic(Constants.baseUrl + name + picFlag,userType),
                userType.length()+1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvName.append(text);
        tvName.append("\n");
    }

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
}
