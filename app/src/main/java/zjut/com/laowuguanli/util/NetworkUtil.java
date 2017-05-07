package zjut.com.laowuguanli.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.HttpURLConnection;
import java.net.URL;

import static zjut.com.laowuguanli.util.SavePicture.readStream;


public class NetworkUtil {

    /**
     * 判断当前是否有网络连接
     * @param context
     * @return
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context

                .getSystemService(Activity.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {

            return true;

        }

        return false;
    }

    public static String requestByGet(String path) throws Exception {
        // 新建一个URL对象
        URL url = new URL(path);
        // 打开一个HttpURLConnection连接
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        // 设置连接超时时间
        urlConn.setConnectTimeout(5 * 1000);
        // 开始连接
        urlConn.connect();
        // 判断请求是否成功
        String result = null;
        if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // 获取返回的数据
            byte[] data = readStream(urlConn.getInputStream());
            result = new String(data, "UTF-8");
        } else {
            result = "Get方式请求失败";
        }
        // 关闭连接
        urlConn.disconnect();
        return result;
    }
}
