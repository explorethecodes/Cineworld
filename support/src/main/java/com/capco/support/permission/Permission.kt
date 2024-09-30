package com.capco.support.permission

import androidx.fragment.app.FragmentActivity
import com.qifan.powerpermission.PowerPermission
import com.qifan.powerpermission.data.hasAllGranted
import com.qifan.powerpermission.data.hasPermanentDenied
import com.qifan.powerpermission.data.hasRational

fun FragmentActivity.hasPermissions(permissions : Array<String>, permissionCallbacks: PermissionCallbacks){
    PowerPermission.init().requestPermissions(context = this, permissions = *permissions) { permissionResult ->
        when {
            permissionResult.hasAllGranted() -> {
                permissionCallbacks.hasAllGranted()
            }

            permissionResult.hasRational() -> {
                permissionCallbacks.hasRational()
            }

            permissionResult.hasPermanentDenied() -> {
                permissionCallbacks.hasPermissionDenied()
            }
        }
    }
}

interface PermissionCallbacks{
    fun hasAllGranted()
    fun hasRational()
    fun hasPermissionDenied()
}