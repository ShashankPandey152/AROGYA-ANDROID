package com.example.hp.arogya

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_set_goal.*
import org.json.JSONObject

class SetGoalActivity : AppCompatActivity() {

    val url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goal)

        setGoal.setOnClickListener {
            val jsonobj = JSONObject()

            jsonobj.put("email",totalSteps.text)


            val que = Volley.newRequestQueue(this@SetGoalActivity)
            val request = JsonObjectRequest(Request.Method.POST,url,jsonobj,
                    Response.Listener { response ->

                        val jsonStatus = response.getBoolean("success")

                        if(jsonStatus) {

                            Toast.makeText(this, "Goal is set!", Toast.LENGTH_SHORT).show()

                            val token = response.getString("token")

                        } else {

                            val message = response.getString("message")
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        }

                    }, Response.ErrorListener {
                Log.d("Login", it.message)
            })
            que.add(request)
        }
    }
}