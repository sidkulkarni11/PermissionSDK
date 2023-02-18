package com.sid.permissionlibrary

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionHelper() {


    companion object {
        private val TAG = "Permission Helper"
    }


    private var TYPE = 0
    private val ACTIVITY = 1
    private val FRAGMENT = 2

    var activity: Activity? = null
    var fragment: Fragment? = null


    private var pListener: PermissionsListener? = null

    private val deniedpermissions: ArrayList<String>
    private val grantedPermissions: ArrayList<String>
    private val allPermissions: ArrayList<String>


    var compulsoryPermissions: ArrayList<String>? = null
    var noncompulsoryPermissions: ArrayList<String>? = null

    var permissionRequestCode: Int? = null
    private var requestMultiplePermissionsFragment : ActivityResultLauncher<Array<String>>?=null

    init {
        allPermissions = ArrayList()
        grantedPermissions = ArrayList()
        deniedpermissions = ArrayList()

    }

    interface PermissionsListener {
        fun onPermissionGranted(request_code: Int, grantedPermissions: ArrayList<String>)
        fun onPermissionRejectedManyTimes(rejectedPerms: List<String?>?, request_code: Int)
        fun grantedAndRejectedPermissions(
            grantedPermissions: ArrayList<String>,
            rejectedPerms: List<String?>?,
            request_code: Int
        )
    }

    constructor(activity: Activity) : this() {
        this.activity = activity
        TYPE = ACTIVITY
    }

    constructor(fragment: Fragment) : this() {
        this.fragment = fragment
        TYPE = FRAGMENT
        requestMultiplePermissionsFragment = fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            onRequestFragmentPermissionsResult()
        }
    }




    private fun getListener(): PermissionsListener? {
        return pListener
    }

    fun setListener(pListener: PermissionsListener?): PermissionHelper? {
        this.pListener = pListener
        return this
    }


    fun requestPermission(
        compulsory: ArrayList<String>, nonCompulsory: ArrayList<String>, request_code: Int
    ) {
        allPermissions.addAll(compulsory)
        allPermissions.addAll(nonCompulsory)
        permissionRequestCode = request_code
        compulsoryPermissions = compulsory
        noncompulsoryPermissions = nonCompulsory

        deniedpermissions.clear()

//        if (!isViewAttached()) {
//            return
//        }
        var allPermissionGranted = true

        if (activity == null && fragment != null) {
            for (permissionName in allPermissions) {
                if (fragment?.activity?.checkSelfPermission(permissionName) == PackageManager.PERMISSION_DENIED) {
                    if (!deniedpermissions.contains(permissionName)) {
                        allPermissionGranted = false
                        deniedpermissions.add(permissionName)
                        Log.d(TAG, "denied $permissionName")
                    }
                } else {
                    if (deniedpermissions.contains(permissionName)) {
                        deniedpermissions.remove(permissionName)
                    }
                }
            }


        } else {
            for (permissionName in allPermissions) {
                if (activity?.checkSelfPermission(permissionName) == PackageManager.PERMISSION_DENIED) {
                    if (!deniedpermissions.contains(permissionName)) {
                        allPermissionGranted = false
                        deniedpermissions.add(permissionName)
                        Log.d(TAG, "denied $permissionName")
                    }
                } else {
                    if (deniedpermissions.contains(permissionName)) {
                        deniedpermissions.remove(permissionName)
                    }
                }
            }
        }
        if (!allPermissionGranted) {
            when (TYPE) {
                ACTIVITY -> {

                    activity!!.requestPermissions(
                        deniedpermissions.toTypedArray(),
                        permissionRequestCode!!
                    )

                }
                FRAGMENT ->
                    requestMultiplePermissionsFragment?.launch(deniedpermissions.toTypedArray())
            }

        } else {
            getListener()!!.onPermissionGranted(permissionRequestCode!!, grantedPermissions)
        }
    }

    fun requestCompulsoryPermission(
        compulsory: ArrayList<String>, request_code: Int
    ) {
        allPermissions.addAll(compulsory)
        permissionRequestCode = request_code
        compulsoryPermissions = compulsory

        deniedpermissions.clear()

//        if (!isViewAttached()) {
//            return
//        }
        var allPermissionGranted = true

        if (activity == null && fragment != null) {
            for (permissionName in allPermissions) {
                if (fragment?.activity?.checkSelfPermission(permissionName) == PackageManager.PERMISSION_DENIED) {
                    if (!deniedpermissions.contains(permissionName)) {
                        allPermissionGranted = false
                        deniedpermissions.add(permissionName)
                        Log.d(TAG, "denied $permissionName")
                    }
                } else {
                    if (deniedpermissions.contains(permissionName)) {
                        deniedpermissions.remove(permissionName)
                    }
                }
            }

        } else {
            for (permissionName in allPermissions) {
                if (activity?.checkSelfPermission(permissionName) == PackageManager.PERMISSION_DENIED) {
                    if (!deniedpermissions.contains(permissionName)) {
                        allPermissionGranted = false
                        deniedpermissions.add(permissionName)
                        Log.d(TAG, "denied $permissionName")
                    }
                } else {
                    if (deniedpermissions.contains(permissionName)) {
                        deniedpermissions.remove(permissionName)
                    }
                }
            }
        }
        if (!allPermissionGranted) {
            when (TYPE) {
                ACTIVITY -> {

                    activity!!.requestPermissions(
                        deniedpermissions.toTypedArray(),
                        permissionRequestCode!!
                    )

                }
                FRAGMENT ->
                    requestMultiplePermissionsFragment?.launch(deniedpermissions.toTypedArray())
            }

        } else {
            getListener()!!.onPermissionGranted(permissionRequestCode!!, grantedPermissions)
        }
    }

    fun requestNonCompulsoryPermission(
        nonCompulsory: ArrayList<String>, request_code: Int
    ) {
//        allPermissions.addAll(compulsory)
        allPermissions.addAll(nonCompulsory)
        permissionRequestCode = request_code
//        compulsoryPermissions = compulsory
        noncompulsoryPermissions = nonCompulsory

        deniedpermissions.clear()

//        if (!isViewAttached()) {
//            return
//        }
        var allPermissionGranted = true

        if (activity == null && fragment != null) {
            for (permissionName in allPermissions) {
                if (fragment?.activity?.checkSelfPermission(permissionName) == PackageManager.PERMISSION_DENIED) {
                    if (!deniedpermissions.contains(permissionName)) {
                        allPermissionGranted = false
                        deniedpermissions.add(permissionName)
                        Log.d(TAG, "denied $permissionName")
                    }
                } else {
                    if (deniedpermissions.contains(permissionName)) {
                        deniedpermissions.remove(permissionName)
                    }
                }

            }

        } else {
            for (permissionName in allPermissions) {
                if (activity?.checkSelfPermission(permissionName) == PackageManager.PERMISSION_DENIED) {
                    if (!deniedpermissions.contains(permissionName)) {
                        allPermissionGranted = false
                        deniedpermissions.add(permissionName)
                        Log.d(TAG, "denied $permissionName")
                    }
                } else {
                    if (deniedpermissions.contains(permissionName)) {
                        deniedpermissions.remove(permissionName)
                    }
                }
            }
        }
        if (!allPermissionGranted) {
            when (TYPE) {
                ACTIVITY -> {

                    activity!!.requestPermissions(
                        deniedpermissions.toTypedArray(),
                        permissionRequestCode!!
                    )

                }
                FRAGMENT ->
                requestMultiplePermissionsFragment?.launch(deniedpermissions.toTypedArray())
            }

        } else {
            getListener()!!.onPermissionGranted(permissionRequestCode!!, grantedPermissions)
        }
    }





    fun onRequestPermissionsResult(
    ) {
//
        val permission_name = StringBuilder()
        grantedPermissions.clear()

        for (permission in deniedpermissions) {
            if (activity != null) {
                if (activity?.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permission)
                } else {
                    if(compulsoryPermissions?.contains(permission) == true) {
                        permission_name.append(",")
                        permission_name.append(permission)
                    }
                }
            } else if (activity == null && fragment != null){
                if (fragment?.activity?.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permission)
                } else {
                    if(compulsoryPermissions?.contains(permission) == true) {
                        permission_name.append(",")
                        permission_name.append(permission)
                    }

                }
            }
        }
        var res = permission_name.toString()
        deniedpermissions.removeAll(grantedPermissions)

        if (deniedpermissions.size > 0) {

            for (ele in deniedpermissions) {
                if (compulsoryPermissions != null) {
                    if (compulsoryPermissions!!.contains(ele)) {
                        if (activity != null)
                            activity?.let { goToSettingsAlertDialog(it, res) }
                        else if(activity == null && fragment != null){
                            fragment?.activity?.let { goToSettingsAlertDialog(it, res) }
                        }
                        break
                    }
                }
            }

        } else {
            getListener()!!.onPermissionGranted(permissionRequestCode!!, grantedPermissions)
        }
        getListener()!!.grantedAndRejectedPermissions(
            grantedPermissions,
            deniedpermissions,
            permissionRequestCode!!
        )
    }

    fun onRequestFragmentPermissionsResult() {
        val permission_name = StringBuilder()
        grantedPermissions.clear()

        for (permission in deniedpermissions) {
            if (activity != null) {
                if (activity?.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permission)
                } else {
                    if(compulsoryPermissions?.contains(permission) == true) {
                        permission_name.append(",")
                        permission_name.append(permission)
                    }
                }
            } else if (activity == null && fragment != null){
                if (fragment?.activity?.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permission)
                } else {
                    if(compulsoryPermissions?.contains(permission) == true) {
                        permission_name.append(",")
                        permission_name.append(permission)
                    }
                }
            }
        }
        var res = permission_name.toString()
        deniedpermissions.removeAll(grantedPermissions)

        if (deniedpermissions.size > 0) {

            for (ele in deniedpermissions) {
                if (compulsoryPermissions != null) {
                    if (compulsoryPermissions!!.contains(ele)) {
                        if (activity != null)
                            activity?.let { goToSettingsAlertDialog(it, res) }
                        else if(activity == null && fragment != null){
                            fragment?.activity?.let { goToSettingsAlertDialog(it, res) }
                        }
                        break
                    }
                }
            }

        } else {
            getListener()!!.onPermissionGranted(permissionRequestCode!!, grantedPermissions)
        }
        getListener()!!.grantedAndRejectedPermissions(
            grantedPermissions,
            deniedpermissions,
            permissionRequestCode!!
        )
    }


    private fun goToSettingsAlertDialog(
        view: Activity,
        permission_name: String,
    ): AlertDialog? {
        return AlertDialog.Builder(view).setTitle("Permission Required")
            .setMessage("We need $permission_name permissions")
            .setPositiveButton("Go to Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    intent.data = Uri.parse("package:" + view.packageName)
                    view.startActivity(intent)
                   /* getListener()!!.grantedAndRejectedPermissions(
                        grantedPermissions,
                        deniedpermissions,
                        permissionRequestCode!!
                    )*/
                })
            /* .setNegativeButton("No",
                 DialogInterface.OnClickListener { dialogInterface, i ->
                     getListener()!!.onPermissionRejectedManyTimes(
                         deniedpermissions,
                         request_code
                     )
                 })*/
            .setCancelable(false)
            .show()
    }


}