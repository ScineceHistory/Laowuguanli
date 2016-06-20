package zjut.com.laowuguanli;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public final class MyApplication extends Application {
    private static MyApplication applicationContext;

    public static MyApplication getInstance() {
        return applicationContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        // 设置全局字体
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/wdcyj.ttf").setFontAttrId(R.attr.fontPath).build());

    }

    public static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

}
