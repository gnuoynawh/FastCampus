package com.gnuoynawh.part2.recorder

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val recordButton: RecordButton by lazy {
        findViewById(R.id.recordButton)
    }

    private val requestPermissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private val state = State.BEFORE_RECORDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAudioPermission()
        initViews()
    }

    private fun initViews() {
        recordButton.updateIconWithState(state)
    }

    private fun requestAudioPermission() {
        requestPermissions(requestPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val audioRecordPermissionGranted = requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                grantResults.first() == PackageManager.PERMISSION_GRANTED

        if(!audioRecordPermissionGranted) {
            finish()
        }
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}