package zjut.com.laowuguanli.util;

import android.os.Environment;

public final class Constants {

    //public static String baseUrl = "http://o9zgq2ik9.bkt.clouddn.com/";
    public static String baseUrl = "http://o9zg04xto.bkt.clouddn.com/";   // feng

    public static String GUANLI_DIR = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS) + "/工人管理信息/";
    public static String ERWEIMA_DIR = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS) + "/工人二维码/";

    public static final class Information {
        public static final String XINLANG_PACKAGE_ID = "com.sina.weibo";
    }
}
