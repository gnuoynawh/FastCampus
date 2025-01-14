package com.gnuoynawh.part3.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                val notiPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                if (notiPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1000)
                    return
                }
            }
        }

        init()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                return
            }
        }

        init()
    }

    private fun init() {

        initOnOffButton()
        initChangeAlarmButton()

        val model = fetchDataFromSharedPreferences()
        renderView(model)

    }

    private fun initOnOffButton() {
        val onOffButton = findViewById<Button>(R.id.onOffButton)
        onOffButton.setOnClickListener {

            val model = it.tag as? AlarmDisplayModel ?: return@setOnClickListener
            val newModel = saveAlarmModel(model.hour, model.minute, model.onOff.not())
            renderView(newModel)

            if (newModel.onOff) {
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, newModel.hour)
                    set(Calendar.MINUTE, newModel.minute)

                    if(before(Calendar.getInstance())) {
                        add(Calendar.DATE, 1)
                    }
                }

                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,    // 정확한 시간
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,  // 하루 뒤
                    pendingIntent
                )

            } else {
                cancelAlarm()
            }
        }
    }
    private fun initChangeAlarmButton() {
        val changeAlarmButton = findViewById<Button>(R.id.changeAlarmTimeButton)
        changeAlarmButton.setOnClickListener {

            // 현재 시간 가져옴. timepicker
            val calendar = Calendar.getInstance()
            TimePickerDialog(this, { picker, hour, minute ->

                val model = saveAlarmModel(hour, minute, false)
                renderView(model)
                cancelAlarm()

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
        }
    }

    private fun saveAlarmModel(
        hour:Int,
        minute: Int,
        onOff: Boolean
    ): AlarmDisplayModel {
        val model = AlarmDisplayModel(
            hour = hour,
            minute = minute,
            onOff = onOff
        )

        val sharedPreference = getSharedPreferences(SHARD_PREFERENCES_NAME, Context.MODE_PRIVATE)
        with(sharedPreference.edit()) {
            putString(ALARM_KEY, model.makeDataForDB())
            putBoolean(ONOFF_KEY, model.onOff)
            commit()
        }

        return model
    }

    private fun cancelAlarm() {
        val pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, Intent(this, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
        pendingIntent?.cancel()
    }

    private fun fetchDataFromSharedPreferences(): AlarmDisplayModel {
        val sharedPreference = getSharedPreferences(SHARD_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val timeDBValue = sharedPreference.getString(ALARM_KEY, "9:30") ?: "9:30"
        val onOffDBValue = sharedPreference.getBoolean(ONOFF_KEY, false)
        val alarmData = timeDBValue.split(":")

        val alarmModel = AlarmDisplayModel(
            hour =  alarmData[0].toInt(),
            minute = alarmData[1].toInt(),
            onOff = onOffDBValue
        )

        val pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, Intent(this, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
        if ((pendingIntent == null) and alarmModel.onOff) {
            alarmModel.onOff = false    // 알람은 꺼져있고, 데이터는 켜져있는 경우

        } else if ((pendingIntent != null) and alarmModel.onOff.not()) {
            pendingIntent.cancel()  // 알람은 켜져있는데, 데이터는 꺼져있는 경우
        }

        return alarmModel
    }

    private fun renderView(model: AlarmDisplayModel) {
        findViewById<TextView>(R.id.ampmTextView).apply {
            text = model.ampmText
        }
        findViewById<TextView>(R.id.timeTextView).apply {
            text = model.timeText
        }
        findViewById<Button>(R.id.onOffButton).apply {
            text = model.onOffText
            tag = model
        }
    }

    companion object {
        private const val SHARD_PREFERENCES_NAME = "time"
        private const val ALARM_KEY = "alarm"
        private const val ONOFF_KEY = "onOff"
        private const val ALARM_REQUEST_CODE = 1000
    }
}