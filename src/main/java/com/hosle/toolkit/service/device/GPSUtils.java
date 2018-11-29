package com.hosle.toolkit.service.device;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Author: qinxudong
 * <p>
 * XuDongQin@sf-express.com
 * <p>
 * Created Date: 2017/11/27
 * <p>
 * Final modified Date: 2017/11/27
 * <p>
 * Describe:
 */

public class GPSUtils {

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return 0 表示gps未开启 1 表示开启 2 表示定位权限被拒绝
     */
    public static final int gpsOPenState(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        int state = gps ? 1 : 0;
        if (gps) {//开了定位服务
            //检测用户是否将当前应用的定位权限拒绝
            int checkResult = checkOp(context, 2, AppOpsManager.OPSTR_FINE_LOCATION);//其中2代表AppOpsManager.OP_GPS，如果要判断悬浮框权限，第二个参数需换成24即AppOpsManager。OP_SYSTEM_ALERT_WINDOW及，第三个参数需要换成AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW
            int checkResult2 = checkOp(context, 1, AppOpsManager.OPSTR_FINE_LOCATION);
            if (AppOpsManagerCompat.MODE_IGNORED == checkResult || AppOpsManagerCompat.MODE_IGNORED == checkResult2) {
                return 2;
            }
        }
        return state;

    }

    /**
     * 判断定位是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isCanLocation(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
//        Intent GPSIntent = new Intent();
//        GPSIntent.setClassName("com.android.settings",
//                "com.android.settings.widget.SettingsAppWidgetProvider");
//        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
//        GPSIntent.setData(Uri.parse("custom:3"));
//        try {
//            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }

        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(settingsIntent);

    }

    /**
     * 检查权限列表
     *
     * @param context
     * @param op       这个值被hide了，去AppOpsManager类源码找，如位置权限  AppOpsManager.OP_GPS==2
     * @param opString 如判断定位权限 AppOpsManager.OPSTR_FINE_LOCATION
     * @return @see 如果返回值 AppOpsManagerCompat.MODE_IGNORED 表示被禁用了
     */
    public static int checkOp(Context context, int op, String opString) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
//            Object object = context.getSystemService("appops");
            Class c = object.getClass();
            try {
                Class[] cArg = new Class[3];
                cArg[0] = int.class;
                cArg[1] = int.class;
                cArg[2] = String.class;
                Method lMethod = c.getDeclaredMethod("checkOp", cArg);
                return (Integer) lMethod.invoke(object, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                if (Build.VERSION.SDK_INT >= 23) {
                    return AppOpsManagerCompat.noteOp(context, opString, context.getApplicationInfo().uid,
                            context.getPackageName());
                }
            }
        }
        return -1;
    }

    /**
     * 定位权限是否开启
     *
     * @param context
     * @return
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    /**
     * App 是否有定位权限
     *
     * @return
     */
    public static boolean isAppHasLocationPermission(Context context) {
        int fineLocation;
        int coarseLocation;
        fineLocation = PermissionChecker.checkPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Process.myPid(),
                Process.myUid(),
                context.getPackageName());
        coarseLocation = PermissionChecker.checkPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Process.myPid(),
                Process.myUid(),
                context.getPackageName());
        return fineLocation == PackageManager.PERMISSION_GRANTED
                || coarseLocation == PackageManager.PERMISSION_GRANTED;
    }

}
