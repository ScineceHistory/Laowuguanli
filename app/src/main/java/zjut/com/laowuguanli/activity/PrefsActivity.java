package zjut.com.laowuguanli.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.WindowManager;

import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.fragment.PrefsFragment;

public class PrefsActivity extends BaseActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pre);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        initView();
    }

    protected void initView() {
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("设置");
        PrefsFragment fragment = new PrefsFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_frame, fragment)
                .commit();


        fragment.setOnChangeSharedPreferencesListener(
                new PrefsFragment.OnChangeSharedPreferencesListener() {
                    @Override
                    public void preferenceChange(Preference preference) {
                        if (preferences.getBoolean("auto_login",false)) {
                            preference.setDefaultValue(true);
                        } else {
                            preference.setDefaultValue(false);
                        }
                    }
                });
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
