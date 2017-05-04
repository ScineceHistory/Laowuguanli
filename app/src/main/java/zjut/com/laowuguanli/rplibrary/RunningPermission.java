package zjut.com.laowuguanli.rplibrary;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;

/**
 * 作者 @sh2zqp
 * 时间 @2016年12月28日 14:06
 *
 * 一共9组危险权限组需要进行运行时申请权限
 */

public class RunningPermission {

    /**
     * CALENDAR 日历权限组
     */
    public static class CALENDAR {
        public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
        public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    }

    /**
     * CAMERA 相机权限组
     */
    public static class CAMERA {
        public static final String CAMERA = Manifest.permission.CAMERA;
    }

    /**
     * CONTACTS 联系人权限组
     */
    public static class CONTACTS {
        public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
        public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
        public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    }

    /**
     * LOCATION 位置权限组
     */
    public static class LOCATION {
        public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    }

    /**
     * MICROPHONE 微机权限组
     */
    public static class MICROPHONE {
        public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    }

    /**
     * PHONE 手机权限组
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static class PHONE {
        public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
        public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
        public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
        public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
        public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
        public static final String USE_SIP = Manifest.permission.USE_SIP;
        public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    }

    /**
     * SENSORS 传感器权限组
     */
    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    public static class SENSORS {
        public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    }

    /**
     * SMS 短信权限组
     */
    public static class SMS {
        public static final String SEND_SMS = Manifest.permission.SEND_SMS;
        public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
        public static final String READ_SMS = Manifest.permission.READ_SMS;
        public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
        public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    }

    /**
     * STORAGE 存储SD卡权限组
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static class STORAGE {
        public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
        public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }

}
