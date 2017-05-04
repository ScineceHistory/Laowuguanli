package zjut.com.laowuguanli.rplibrary;

/**
 * 作者 @sh2zqp
 * 时间 @2016年12月28日 11:45
 */

public interface OnPermissionListener {

    /**
     * 所有权限被授予时回调
     */
    void onAllGranted();

    /**
     * 权限被授予时回调
     */
    void onGranted(String permission);

    /**
     * 权限被拒绝时回调
     */
    void denied(String[] permissions);

}
