package com.halzhang.android.examples.permissionexample;

import android.content.pm.PackageManager;

/**
 * Created by HalZhang on 2016/1/12.
 */
public class Utils {

    /**
     * 多权限授权结果校验
     *
     * @param grantResults
     * @return
     */
    public static boolean verifyPermission(int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
