# homan-util-service

Author : hosle

Created at Nov 29th 2018

-----

## Device

* DeviceHelper ：判断手机厂商
* DeviceUtil ： 获取屏幕宽、屏幕高
* GPSUtil： 监测GPS，打开GPS

## Permission

* 检查权限

```
fun checkPermissions(activity: Activity, requstCode:Int, permissions:Array<String>): Boolean
```

* 检查电量优化白名单

```
fun checkBatteryOptimization(activity: Activity, reqCode:Int)
```

## System Service

### Input 

* 软键盘开启／隐藏


### 常用跳转

* 打开系统图片册选择照片

```
fun navigateForPicFromPhotoAlumb(activity: Activity,reqCode:Int)
```

* 打开系统相机

```
fun navigateToCamera(activity: Activity, reqCode: Int,targetFile: File)
```

* 拨打电话盘

```
fun navigateToPhoneCall(activity: Activity, phoneNum:String)
```

## Third part

* 沉浸式状态栏： 获取高度，改变颜色