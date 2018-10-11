package com.example.hp.arogya

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONException
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    val url =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val loginRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            try {
                val jsonStatus = response.getString("logStatus")

                if (jsonStatus.toInt() == 0) {
                    Toast.makeText(this, "Email address or password is incorrect!",
                            Toast.LENGTH_SHORT).show()
                } else if (jsonStatus.toInt() == 1) {
                    Toast.makeText(this, "login successfull!",
                            Toast.LENGTH_SHORT).show()

                }

            } catch (e: JSONException) {
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.d("JSON", "EXC: " + e.localizedMessage)
            }
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not login user: $error")
        }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }
        Volley.newRequestQueue(this).add(loginRequest)


        goToEditProfile.setOnClickListener {
            val gotoEditProfile = Intent(this, EditProfileActivity::class.java)
            startActivity(gotoEditProfile)
        }

    }
}
