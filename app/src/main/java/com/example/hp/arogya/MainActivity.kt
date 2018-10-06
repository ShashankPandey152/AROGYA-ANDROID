package com.example.hp.arogya

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hp.arogya.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToSign.setOnClickListener {
            val gotoSignup = Intent(this, SignUpActivity::class.java)
            startActivity(gotoSignup)
        }
        val jsonobj = JSONObject()

        jsonobj.put("username",loginName)
        jsonobj.put("password",loginPassword)

        val que = Volley.newRequestQueue(this@MainActivity)
        val request = JsonObjectRequest(Request.Method.POST,url,jsonobj,
                Response.Listener {
                    response ->
                    Toast.makeText(this,"successfully login",Toast.LENGTH_SHORT).show()
                },Response.ErrorListener {
            //Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
        })
        que.add(request)

    }
}
