package zjut.com.laowuguanli.activity;

import android.os.Bundle;
import android.text.TextUtils;
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

public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.img_pic)
    ImageView imgPic;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_time)
    TextView tvTime;

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
        Glide.with(this).load(user.getPic()).into(imgPic);


        String[] split = user.getName().split("，");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if ((!TextUtils.isEmpty(split[i])) && (i != split.length-1)) {
                sb.append(split[i]+"\n");
            } else {
                sb.append(split[i]);
            }
            Log.d("SH",split[i]);
        }
        tvName.setText(sb.toString());
        tvTime.setText(user.getDate());
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
