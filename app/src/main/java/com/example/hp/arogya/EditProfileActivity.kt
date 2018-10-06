package com.example.hp.arogya

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class EditProfileActivity : AppCompatActivity() {

    val url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val jsonobj = JSONObject()

        jsonobj.put("username", name.text)
        jsonobj.put("email", email.text)
        jsonobj.put("password", password.text)
        jsonobj.put("dob", DobView.text)
        jsonobj.put("gender", genderView.text)

        val que = Volley.newRequestQueue(this@EditProfileActivity)
        val request = JsonObjectRequest(Request.Method.POST, url, jsonobj,
                Response.Listener {

                    response ->
                    Toast.makeText(this, "no error", Toast.LENGTH_SHORT).show()
                }, Response.ErrorListener {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        })
        que.add(request)
    }
}
