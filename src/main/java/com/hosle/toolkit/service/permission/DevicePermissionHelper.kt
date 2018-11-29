package com.hosle.toolkit.service.permission

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.hosle.toolkit.service.device.DeviceHelper


/**
 * Created by tanjiahao on 2018/11/28
 * Original Project HoToolKit
 */
object DevicePermissionHelper {
    
    fun gotoPermissionSettings(context: Context) {
        when {
            DeviceHelper.isMIUI() -> gotoMiuiPermission(context)
            DeviceHelper.isFlyme() -> gotoMeizuPermission(context)
            DeviceHelper.isEMUI() -> gotoHuaweiPermission(context)
            DeviceHelper.isOppo() -> gotoOppoPermission(context)
            DeviceHelper.is360() -> goto360Permission(context)
            DeviceHelper.isSmartisan() -> gotoSmartisan(context)
            else -> context.startActivity(getAppDetailSettingIntent(context))
        }
    }
    
    /**
     * 跳转到miui的权限管理页面
     */
    private fun gotoMiuiPermission(context: Context) {
        val i = Intent("miui.intent.action.APP_PERM_EDITOR")
        val componentName = ComponentName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
        i.component = componentName
        i.putExtra("extra_pkgname", context.packageName)
        try {
            context.startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * 跳转到魅族的权限管理系统
     */
    private fun gotoMeizuPermission(context: Context) {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.putExtra("packageName", context.packageName)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * 华为的权限管理页面
     */
    private fun gotoHuaweiPermission(context: Context) {
        try {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val comp = ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity")//华为权限管理
            intent.component = comp
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun gotoOppoPermission(context: Context) {
        
        try {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("packageName", context.packageName)
            val comp = ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity")
            intent.component = comp;
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
    }
    
    private fun goto360Permission(context: Context) {
        try {
            val intent = Intent("android.intent.action.MAIN")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("packageName", context.packageName)
            val comp = ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity")
            intent.component = comp
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun gotoSmartisan(context: Context) {
        try {
            val intent = Intent("android.intent.action.MAIN")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("packageName", context.packageName)
            val comp = ComponentName("com.smartisanos.security", "com.smartisanos.security.PermissionsActivity")
            intent.component = comp
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private fun getAppDetailSettingIntent(context: Context): Intent {
        
        // vivo 点击设置图标>加速白名单>我的app
        //      点击软件管理>软件管理权限>软件>我的app>信任该软件
        var appIntent = context.packageManager.getLaunchIntentForPackage("com.iqoo.secure")
        if (appIntent != null) {
            return appIntent
        }
        
        // oppo 点击设置图标>应用权限管理>按应用程序管理>我的app>我信任该应用
        //      点击权限隐私>自启动管理>我的app
        appIntent = context.packageManager.getLaunchIntentForPackage("com.oppo.safe")
        if (appIntent != null) {
            return appIntent
        }
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        localIntent.data = Uri.fromParts("package", context.packageName, null)
        return localIntent
    }
    
}