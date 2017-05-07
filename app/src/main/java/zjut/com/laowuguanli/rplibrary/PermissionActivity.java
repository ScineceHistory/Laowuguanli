package zjut.com.laowuguanli.rplibrary;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 @sh2zqp
 * 时间 @2016年12月28日 11:43
 */

public class PermissionActivity extends AppCompatActivity {

    public static final int REQUEST_PERMISSION_CODE = 1;
    private static OnPermissionListener mOnPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
    }

    public static void requestPermission(String[] permissions, OnPermissionListener onPermissionListener) {

        mOnPermissionListener = onPermissionListener;

        Activity activity = ActivityManager.getTopActivity();
        if (activity == null) {
            return;
        }

        List<String> requestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                // 该权限未被授权, 添加到权限集合, 去一起去请求权限
                requestPermissionList.add(permission);
            } else {
                mOnPermissionListener.onGranted(Utils.getSimplePermissionName(permission));
            }
        }

        if (requestPermissionList.isEmpty()) {
            // 全部权限已被授予
            mOnPermissionListener.onAllGranted();
        } else {
            // 请求未被授予的权限
            ActivityCompat.requestPermissions(
                    activity,
                    requestPermissionList.toArray(new String[requestPermissionList.size()]),
                    REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    List<String> deniedPermissionList = new ArrayList<>();
                    for (int i = 0; i < permissions.length; i++) {
                        String permission = permissions[i];
                        int grantResult = grantResults[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissionList.add(Utils.getSimplePermissionName(permission));
                        }
                    }
                    if (deniedPermissionList.isEmpty()) {
                        // 没有被拒绝的权限
                        mOnPermissionListener.onAllGranted();
                    } else {
                        // 有被拒绝的权限, 等待下次被授予
                        mOnPermissionListener.denied(
                                deniedPermissionList.toArray(new String[deniedPermissionList.size()]));
                    }
                }
                break;
            default:
                break;
        }

    }
}
