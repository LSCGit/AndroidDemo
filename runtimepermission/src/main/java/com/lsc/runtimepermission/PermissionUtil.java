package com.lsc.runtimepermission;

import android.content.pm.PackageManager;

/**
 * Created by lsc on 2020-04-17 18:22.
 * E-Mail:2965219926@qq.com
 */
public abstract class PermissionUtil {

    /*
    * 通过验证给定数组中的每个条目的值{@linkPackageManager#PERMISSION_GRANTED}，可以检查是否授予了所有给定的权限。
    * */
    public static boolean verifyPermissions(int[] grantResults) {
        // 传过来的数组长度必须大于1
        if (grantResults.length < 1) {
            return false;
        }
        // 验证每一项权限，不允许则返回false
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
