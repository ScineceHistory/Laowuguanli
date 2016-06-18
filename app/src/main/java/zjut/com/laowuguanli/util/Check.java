package zjut.com.laowuguanli.util;

import android.content.Intent;
import android.content.pm.PackageManager;

import zjut.com.laowuguanli.MyApplication;


public final class Check {
    private Check() {

    }

    public static boolean isXinLangClientInstalled() {
        try {
            return preparePackageManager().getPackageInfo(Constants.Information.XINLANG_PACKAGE_ID, PackageManager.GET_ACTIVITIES) != null;
        } catch (PackageManager.NameNotFoundException ignored) {
            return false;
        }
    }

    public static boolean isIntentSafe(Intent intent) {
        return preparePackageManager().queryIntentActivities(intent, 0).size() > 0;
    }

    private static PackageManager preparePackageManager() {
        return MyApplication.getInstance().getPackageManager();
    }
}
