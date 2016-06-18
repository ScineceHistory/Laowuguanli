package zjut.com.laowuguanli.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zjut.com.laowuguanli.R;


public class PrefsFragment extends PreferenceFragment {

    Preference preference;
    OnChangeSharedPreferencesListener mListener;

    public interface OnChangeSharedPreferencesListener {
        void preferenceChange(Preference preference);
    }

    public void setOnChangeSharedPreferencesListener(
            OnChangeSharedPreferencesListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        preference = findPreference("auto_login");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListener.preferenceChange(preference);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
