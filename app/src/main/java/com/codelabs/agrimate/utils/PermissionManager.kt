package com.codelabs.agrimate.utils

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.codelabs.agrimate.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor() : ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(permission: String, isGranted: Boolean) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}

abstract class PermissionTextProvider (private val context: Context) {
    abstract fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider @Inject constructor(private val context: Context) :
    PermissionTextProvider(context) {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            context.getString(R.string.camera_permission_permanently_declined_desc)
        } else {
            context.getString(R.string.camer_permission_declined_desc)
        }
    }
}

class CoarseLocationPermissionTextProvider @Inject constructor(private val context: Context) :
    PermissionTextProvider(context) {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            context.getString(R.string.location_permission_permanently_declined_desc)
        } else {
            context.getString(R.string.location_permission_declined_desc)
        }
    }
}