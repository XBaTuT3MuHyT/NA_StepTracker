package com.example.na_steptracker.service.StepsForegroundService

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.na_steptracker.App
import com.example.na_steptracker.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class StepsForegroundService : Service(), SensorEventListener {
    companion object {
        private const val NOTIFICATION_ID = 1
    }
    private lateinit var stepsCollector: StepsCollector
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private lateinit var sensorManager: SensorManager
    private var stepsSensor: Sensor? = null

    private fun createNotification(): Notification {
        val channelId = "steps_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Steps tracking",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Подсчёт шагов")
            .setContentText("Идёт подсчёт шагов")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        Log.d("StepsService", "Service CREATED")

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)


        val app = application as App
        val stepsRepository = app.stepsRepository
        val weatherRepository = app.weatherRepository
        val prefs = app.stepCounterStorage

        stepsCollector = StepsCollector(
            stepsRepository = stepsRepository,
            weatherRepository = weatherRepository,
            stepsPrefs = prefs,
            scope = serviceScope
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(
            NOTIFICATION_ID,
            createNotification()
        )

        stepsSensor?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        Log.d("StepsService", "Service ON_START_COMMAND")
        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val totalSteps = event?.values?.firstOrNull()?.toInt() ?: return
        stepsCollector.onNewSensorValue(totalSteps)
        Log.d("StepsService", "Sensor Changed")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("StepsService", "Service DESTROYED")
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Provide the return value")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}
