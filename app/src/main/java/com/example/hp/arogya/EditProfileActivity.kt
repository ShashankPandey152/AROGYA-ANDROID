package com.example.hp.arogya

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    val url = "https://arogya2018.herokuapp.com/api/account/edit"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        val types = arrayOf("Select Gender","Male","Female","Others")
        changeGender.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,types)
        changeGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onNothingSelected(parent: AdapterView<*>?) {
                changeGenderView.text = "Select your gender"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                changeGenderView.text = types.get(position)
            }

        }





        editBtn.setOnClickListener {


            val jsonobj = JSONObject()

            if(changeName.text.toString().isEmpty() && changePassword.text.toString().isEmpty()  && changeGenderView.text.toString() == "Select Gender") {
                Toast.makeText(this,"Please Select any one Field",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show()
                val uid = "5bba4897bc9bab0030ddc2e6"
                jsonobj.put("uid", uid)
                if(changeName.text.toString().isNotEmpty()) {
                    jsonobj.put("Name", changeName.text)
                }else {
                    jsonobj.put("Name","")
                }
                if(changePassword.text.toString().isNotEmpty()) {
                    jsonobj.put("password", changePassword.text)
                }else {
                    jsonobj.put("password","")
                }
                if(changeGenderView.text.toString() != "Select Gender") {
                    jsonobj.put("gender", changeGenderView.text[0])
                }else {
                    jsonobj.put("gender","")
                }


                val que = Volley.newRequestQueue(this@EditProfileActivity)
                val request = JsonObjectRequest(Request.Method.POST, url, jsonobj,
                        Response.Listener {

                            response ->
                            val jsonStatus = response.getBoolean("success")
                            if(jsonStatus) {
                                Toast.makeText(this, "Edit succesfully!", Toast.LENGTH_SHORT).show()
                            } else {
                                val jsonMessage = response.getString("message")
                                Toast.makeText(this, jsonMessage, Toast.LENGTH_SHORT).show()
                            }

                        }, Response.ErrorListener {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                })
                que.add(request)

            }

        }


    }
}
