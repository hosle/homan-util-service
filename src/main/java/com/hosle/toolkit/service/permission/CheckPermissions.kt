package com.hosle.toolkit.service.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


/**
 * Created by tanjiahao on 2018/10/11
 * Original Project HoToolKit
 */


/**
 * 检查权限
 */
fun checkPermissions(activity: Activity, requstCode:Int, permissions:Array<String>): Boolean {


    val toApplyList = ArrayList<String>()

    for (perm in permissions) {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(activity, perm)) {
            toApplyList.add(perm)
            // 进入到这里代表没有权限.
        }
    }
    if (!toApplyList.isEmpty()) {
        ActivityCompat.requestPermissions(activity,toApplyList.toTypedArray(), requstCode)
        return false
    }

    return true
}

/**
 * 检查电量优化白名单
 */
fun checkBatteryOptimization(activity: Activity, reqCode:Int){

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val intent = Intent()
        val packageName = activity.packageName
        val pm = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)){
            checkPermissions(activity, reqCode, arrayOf(
                    Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            ))

            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:"+packageName)
//            intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
            activity.startActivity(intent)
        }
    }
}