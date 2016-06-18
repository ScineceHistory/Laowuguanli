package zjut.com.laowuguanli.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zjut.com.laowuguanli.R;

/**
 * Created by ScienceHistory on 16/6/5.
 */
public class WrongFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wrong,container,false);
        return view;
    }
}
