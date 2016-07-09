package zjut.com.laowuguanli.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者 @ScienceHistory
 * 时间 @2016年07月09日 12:12
 */
public class SavePicture {

    private final static String TAG = "Science";
    private final static String DOWNLOAD_PATH
            = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/工人二维码/";

    /**
     * Get image from network
     */
    public static byte[] getImage(String urlImg) throws Exception {
        Log.d("Science", "getImage: "+urlImg);
        URL url = new URL(urlImg);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // 设置请求方法为GET
        conn.setReadTimeout(5000); // 设置请求过时时间为5秒
        conn.setConnectTimeout(5000);
        if(conn.getResponseCode() == 200){
            InputStream inStream = conn.getInputStream();
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get data from stream
     */
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * Get image from network
     */
    public static InputStream getImageStream(String urlImg) throws Exception{
        URL url = new URL(urlImg);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * 保存文件
     */
    public static void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(DOWNLOAD_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(DOWNLOAD_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
}
