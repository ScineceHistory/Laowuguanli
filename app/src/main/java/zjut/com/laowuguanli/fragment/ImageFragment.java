package zjut.com.laowuguanli.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.util.PictureUtils;

public class ImageFragment extends DialogFragment {
    public static final String EXTRA_IMAGE_PATH = "com.zjut.criminalintent.fragment.EXTRA_IMAGE_PATH";
    private ImageView mImageView;
    boolean isNewUser = true;

    public static ImageFragment newInstance(String imagePath) {
        ImageFragment imageFragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_IMAGE_PATH,imagePath);
        imageFragment.setArguments(args);
        imageFragment.setStyle(DialogFragment.STYLE_NO_TITLE,0);
        return imageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mImageView = (ImageView) inflater.inflate(R.layout.fullscreen_image,container,false);
        String path = (String) getArguments().getSerializable(EXTRA_IMAGE_PATH);
        BitmapDrawable bitmapDrawable = PictureUtils.getScaledDrawable(getActivity(),path);
        mImageView.setImageDrawable(bitmapDrawable);
        return mImageView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (isNewUser) {
//            isNewUser = false;
//            return;
//        }
        PictureUtils.cleanImageView(mImageView);
    }
}
