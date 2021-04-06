package com.head.toolkit;

public class DeviceUtils {
    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 返回版本
     * @return
     */
    public static String getSDKVersionName() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 返回版本编码
     *
     * @return
     */
    public static int getSDKVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }

}
