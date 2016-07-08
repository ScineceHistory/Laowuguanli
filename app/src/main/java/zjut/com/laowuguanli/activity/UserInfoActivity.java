package zjut.com.laowuguanli.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.util.URLSpanGroup;
import zjut.com.laowuguanli.util.URLSpanPic;
import zjut.com.laowuguanli.util.URLSpanTel;

public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.img_pic)
    ImageView imgPic;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_time)
    TextView tvTime;

    String baseUrl = "http://o9zg04xto.bkt.clouddn.com/";
    String groupUrl = "http://8bur.cscec.com/col/col1627/index.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("工人信息");

        ButterKnife.bind(this);
        User user = (User) getIntent().getSerializableExtra("user");
        String picUrl = user.getPic();
        if (!picUrl.startsWith("http:")) {
            picUrl = "http:" + picUrl;

        }

        Glide.with(this).load(picUrl).into(imgPic);

        tvName.setMovementMethod(LinkMovementMethod.getInstance());
        String[] split = user.getName().split("，");
        String name = null;
        for (int i = 0; i < split.length; i++) {
            if ((!TextUtils.isEmpty(split[i])) && (i != split.length-1)) {
                String string = split[i];
                if (i==0) {
                    name = string;
                }
                if (string.startsWith("身份证")) {
                    displayPicLinkItem(split[i], name , "身份证" , "sfz");
                } else if (string.startsWith("操作证")) {
                    displayPicLinkItem(split[i], name , "操作证" , "czz");
                } else if (string.startsWith("合格证")) {
                    displayPicLinkItem(split[i], name , "合格证" , "hgz");
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
    }

    private void displayEndItem(String source) {
//        SpannableString text = new SpannableString(source);
//        text.setSpan(new URLSpanGroup(groupUrl),0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tvName.append(text);
        String[] fields = source.split("：");
        Log.d("Science", "displayEndItem: --------------"+source);
        Log.d("Science", "displayEndItem: ＝＝＝＝＝＝＝＝＝ "+fields[0] + fields[1]);
        SpannableString text = new SpannableString(fields[0]);
        text.setSpan(new URLSpanGroup(baseUrl + fields[1],fields[1]),0, fields[0].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        text.setSpan(new URLSpanPic(baseUrl + name + picFlag,userType),userType.length()+1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
