package com.hosle.toolkit.service.system_service

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File

/**
 * Created by tanjiahao on 2018/11/28
 * Original Project HoToolKit
 */

/**
 * 激活系统图库，选择一张图片
 */
fun navigateForPicFromPhotoAlumb(activity: Activity,reqCode:Int) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    activity.startActivityForResult(intent, reqCode)
}

/**
 * 打开系统相机
 */
fun navigateToCamera(activity: Activity, reqCode: Int,targetFile: File) {
    val uri = Uri.fromFile(targetFile)
    Log.e("camera", uri.toString())
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
    activity.startActivityForResult(intent, reqCode)
}

/**
 * 拨打电话
 */
fun navigateToPhoneCall(activity: Activity, phoneNum:String){
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(phoneNum))
    activity.startActivity(intent)
}