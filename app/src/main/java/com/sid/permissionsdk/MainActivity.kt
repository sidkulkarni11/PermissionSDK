package com.sid.permissionsdk

import android.Manifest.permission.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.sid.permissionlibrary.PermissionHelper

class MainActivity : AppCompatActivity(), PermissionHelper.PermissionsListener {
    lateinit var mPermissionHelper: PermissionHelper
    val PERMISSION_REQUEST_CODE = 101
    lateinit var permissionButton :Button
    lateinit var fragButton :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionButton = findViewById(R.id.permissionBtn)
        fragButton= findViewById(R.id.Fragmentbtn)

        mPermissionHelper = PermissionHelper(this).setListener(this)!!
        var compulsoryList = ArrayList<String>()
        compulsoryList.add(READ_EXTERNAL_STORAGE)
        compulsoryList.add(WRITE_EXTERNAL_STORAGE)
        compulsoryList.add(CAMERA)




        var nonCompulsoryList = ArrayList<String>()
        nonCompulsoryList.add(READ_CONTACTS)
        nonCompulsoryList.add(READ_CALENDAR)

//        mPermissionHelper.requestCompulsoryPermission(nonCompulsoryList,PERMISSION_REQUEST_CODE)



        /*permissionButton.setOnClickListener {
            mPermissionHelper.requestCompulsoryPermission(
                compulsoryList,PERMISSION_REQUEST_CODE
            )
        }*/

        fragButton.setOnClickListener {
            var intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != RESULT_CANCELED)
            mPermissionHelper.onRequestPermissionsResult()
    }


}