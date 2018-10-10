package com.example.hp.arogya

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hp.arogya.R.id.*
import com.example.hp.arogya.listener.StepListener
import com.example.hp.arogya.utils.StepDetector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pedometer.*
import org.json.JSONObject

/**
 * Created by Govind on 05/25/2018.
 */
class PedometerActivity : AppCompatActivity(), SensorEventListener, StepListener {

    val url = ""
    private var simpleStepDetector: StepDetector? = null
    private var sensorManager: SensorManager? = null
    private val TEXT_NUM_STEPS = "Number of Steps: "
    private var numSteps: Int = 0

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }

    override fun step(timeNs: Long) {
        numSteps++
        tvSteps.text = TEXT_NUM_STEPS.plus(numSteps)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedometer)

        // Get an instance of the SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)

        btnStart.setOnClickListener(View.OnClickListener {
            numSteps = 0
            sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)
        })

        btnStop.setOnClickListener(View.OnClickListener {
            sensorManager!!.unregisterListener(this)
            val jsonobj = JSONObject()
            var totalCalorie = java.lang.Long.parseLong(tvSteps.text.toString())
            Toast.makeText(this, "right", Toast.LENGTH_SHORT).show()
            jsonobj.put("email",totalCalorie.toString())


            val que = Volley.newRequestQueue(this@PedometerActivity)
            val request = JsonObjectRequest(Request.Method.POST,url,jsonobj,
                    Response.Listener { response ->

                        val jsonStatus = response.getBoolean("success")

                        if(jsonStatus) {

                            Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show()

                            val token = response.getString("token")

                        } else {

                            val message = response.getString("message")
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        }

                    }, Response.ErrorListener {
                Log.d("Login", it.message)
            })
            que.add(request)


        })


        btnGoal.setOnClickListener {
            val gotoSetGoal = Intent(this, SetGoalActivity::class.java)
            startActivity(gotoSetGoal)
        }
        }
    }




