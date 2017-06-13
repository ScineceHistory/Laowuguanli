package zjut.com.laowuguanli.activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zjut.com.laowuguanli.MyService;
import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.animation.ScaleInAnimation;
import zjut.com.laowuguanli.bean.Result;
import zjut.com.laowuguanli.bean.SplashImage;
import zjut.com.laowuguanli.util.NetworkUtil;

public class SplashActivity extends Activity {

    private ImageView mImageView;
    public MyService service;
    private ProgressBar loading;
    private TextView meTv;
    private TextView flagTv;
    private LinearLayout bottomLL;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            SplashActivity.this.finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置Activity没有标题栏，在加载布局之前调用
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        mImageView = (ImageView) findViewById(R.id.spalshImage);
        loading = (ProgressBar) findViewById(R.id.loading);
        meTv = (TextView) findViewById(R.id.meTitle);
        flagTv = (TextView) findViewById(R.id.flag_group);
        bottomLL = (LinearLayout) findViewById(R.id.bottomInfo);

        service = getService();

        if (NetworkUtil.isOnline(this)) {
            System.out.println("====================================");
            loadDataSetLis(service.getSplashImage());
            System.out.println("+++++++++++++++++++++++++++++++++++++");
        } else {
            mImageView.setImageResource(R.drawable.logo);
            bottomLL.setVisibility(View.VISIBLE);
            meTv.setTextColor(getResources().getColor(android.R.color.darker_gray));
            loadAnimation(mImageView);
            mHandler.sendEmptyMessageDelayed(0x111,3000);
        }
    }

    public MyService getService() {
        String baseUrl = "http://gank.io";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(MyService.class);
        return service;
    }

    public void loadDataSetLis(Observable<SplashImage> splashImageObservable) {
        splashImageObservable
                .observeOn(AndroidSchedulers.mainThread())  // 事件消费的线程
                .subscribeOn(Schedulers.io())               // 事件产生的线程
                .subscribe(new Subscriber<SplashImage>() {
                    @Override
                    public void onCompleted() {
                        loading.setVisibility(View.GONE);
                        bottomLL.setVisibility(View.VISIBLE);
                        mHandler.sendEmptyMessageDelayed(0x111,4000);
                    }
                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(final SplashImage splashImage) {
                        List<Result> results = splashImage.getResults();
                        Result result = results.get(0);
                        String imageUrl = result.getUrl();
                        System.out.println("---------------------------"+imageUrl);
                        if (!TextUtils.isEmpty(imageUrl)) {
                            Picasso.with(SplashActivity.this).load(imageUrl).into(mImageView);
                        } else {
                            Picasso.with(SplashActivity.this).load(R.drawable.logo).into(mImageView);
                        }
                        loadAnimation(mImageView);

                        Typeface face = Typeface.createFromAsset (getAssets() , "fonts/nokia.ttf" );
                        flagTv.setTypeface (face);
                        flagTv.setVisibility(View.VISIBLE);
                        loadAnimation(flagTv);
                    }
                });
    }

    private void loadAnimation(View view) {
        ScaleInAnimation animation = new ScaleInAnimation();
        for (Animator anim : animation.getAnimators(view)) {
            anim.setDuration(4000).start();
            anim.setInterpolator(new DecelerateInterpolator());
        }
    }
}
