package zjut.com.laowuguanli.rplibrary;

import android.widget.Toast;

/**
 * 作者 @sh2zqp
 * 时间 @2016年12月28日 14:27
 */

public class Utils {
    /**
     * 获得权限的简化后的名字
     * @param permission 权限的全称
     * @return 简化后的权限名
     */
    public static String getSimplePermissionName(String permission) {
        return permission.substring(19);
    }

    private static Toast mToast;
    public static void showToast(String info) {
//        if (mToast != null) {
//            mToast.setText(info);
//        } else {
//            mToast = Toast.makeText(ActivityManager.getTopActivity(),
//                    info, Toast.LENGTH_SHORT);
//        }
//        mToast.show();
        Toast.makeText(ActivityManager.getTopActivity(), info, Toast.LENGTH_SHORT).show();
    }
}
