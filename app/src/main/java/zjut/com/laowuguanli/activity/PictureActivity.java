package zjut.com.laowuguanli.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjut.com.laowuguanli.R;

/**
 * 作者 @ScienceHistory
 * 时间 @2016年07月08日 18:21
 */
public class PictureActivity extends AppCompatActivity {
    @Bind(R.id.user_pic)
    ImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_pic);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String picUrl = intent.getStringExtra("picUrl");
        String titleStr = intent.getStringExtra("titleStr");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(titleStr);

        Glide.with(this).load(picUrl).into(userPic);

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
