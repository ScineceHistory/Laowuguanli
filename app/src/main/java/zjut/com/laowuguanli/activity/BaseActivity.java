package zjut.com.laowuguanli.activity;

import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import zjut.com.laowuguanli.rplibrary.PermissionActivity;

/**
 * Created by ScienceHistory on 16/6/18.
 * 所有Activity基类
 */
public class BaseActivity extends PermissionActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
