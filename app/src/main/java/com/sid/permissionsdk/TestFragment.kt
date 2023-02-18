package com.sid.permissionsdk

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sid.permissionlibrary.PermissionHelper


class TestFragment : Fragment(), PermissionHelper.PermissionsListener {
    lateinit var mPermissionHelper: PermissionHelper
    val PERMISSION_REQUEST_CODE = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var nonCompulsoryList = ArrayList<String>()
        nonCompulsoryList.add(Manifest.permission.READ_CONTACTS)
        nonCompulsoryList.add(Manifest.permission.READ_CALENDAR)

        var compulsoryList = ArrayList<String>()
        compulsoryList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        compulsoryList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        compulsoryList.add(Manifest.permission.CAMERA)

        mPermissionHelper = PermissionHelper(this).setListener(this)!!
        mPermissionHelper.requestPermission(compulsoryList,nonCompulsoryList,PERMISSION_REQUEST_CODE)


        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onPermissionGranted(request_code: Int, grantedPermissions: ArrayList<String>) {
        when (request_code) {
            PERMISSION_REQUEST_CODE -> {
                Log.d("******", PERMISSION_REQUEST_CODE.toString())

                for (grantedPer in grantedPermissions) {
                    Log.d("*******", grantedPer)
                }
            }
        }
    }

    override fun onPermissionRejectedManyTimes(rejectedPerms: List<String?>?, request_code: Int) {
        if (rejectedPerms != null) {
            for (rP in rejectedPerms) {
                if (rP != null) {
                    Log.d("******", rP)
                }
            }
        }
    }

    override fun grantedAndRejectedPermissions(
        grantedPermissions: ArrayList<String>,
        rejectedPerms: List<String?>?,
        request_code: Int
    ) {
        if (grantedPermissions != null) {
            for (grantedPer in grantedPermissions) {
                if (grantedPer != null) {
                    Log.d("*******", grantedPer)
                }
            }
        }
        if (rejectedPerms != null) {
            for (rejectedPer in rejectedPerms) {
                if (rejectedPer != null) {
                    Log.d("*******", rejectedPer)
                }
            }
        }
    }






}