package com.example.hp.arogya

import android.app.DatePickerDialog
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

    val url = ""
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

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        changeDob.setOnClickListener {
            val dob = DatePickerDialog(this,android.R.style.Theme_DeviceDefault_Light, DatePickerDialog.OnDateSetListener { view: DatePicker, year1:Int, month1:Int, day1:Int ->
                changeDobView.setText(""+day1.toString()+"/"+month1.toString()+"/"+year1.toString())
            },year,month,day)
            dob.show()
        }


        editBtn.setOnClickListener {


            val jsonobj = JSONObject()

            if(changeName.text.toString().isEmpty() && changeDobView.text.toString() == "Not selected" && changeGenderView.text.toString() == "Select Gender") {
                Toast.makeText(this,"Please Select any one Field",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show()
                if(changeName.text.toString().isNotEmpty()) {
                    jsonobj.put("username", changeName.text)
                }
                if(changeDobView.text.toString() != "Not selected") {
                    jsonobj.put("dob", changeDobView.text)
                }
                if(changeGenderView.text.toString() != "Select Gender") {
                    jsonobj.put("gender", changeGenderView.text[0])
                }


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


    }
}
