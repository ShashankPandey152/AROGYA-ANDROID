package com.example.hp.arogya

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hp.arogya.R.styleable.AlertDialog
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import java.util.*
import android.widget.TextView
import android.widget.DatePicker
import com.example.hp.arogya.R.id.*


class SignUpActivity : AppCompatActivity() {

    val url = ""
    lateinit var genderSpinner: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        type = findViewById<Spinner>(gender)
//        genderSpinner = findViewById<TextView>(genderView)
        val types = arrayOf("Select Gender","Male","Female","Others")
        gender.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,types)
        gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onNothingSelected(parent: AdapterView<*>?) {
                genderView.text = "Select your gender"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                genderView.text = types.get(position)
            }

        }
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        Dob.setOnClickListener {
            val dob = DatePickerDialog(this,android.R.style.Theme_DeviceDefault_Light,DatePickerDialog.OnDateSetListener { view: DatePicker, year1:Int, month1:Int, day1:Int ->
                DobView.setText(""+day1.toString()+"/"+month1.toString()+"/"+year1.toString())
            },year,month,day)
            dob.show()
        }

//        class DatePickerDialogTheme : DialogFragment(), DatePickerDialog.OnDateSetListener {
//
//            fun onCreateDialog(savedInstanceState: Bundle): Dialog {
//                val calendar = Calendar.getInstance()
//                val year = calendar.get(Calendar.YEAR)
//                val month = calendar.get(Calendar.MONTH)
//                val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//
//                val datepickerdialog = DatePickerDialog(getActivity(),
//                        AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day)
//
//            }

        val jsonobj = JSONObject()

//        }
        signUp.setOnClickListener {
            if (password.text.toString() != confirmPassword.text.toString()) {
                Toast.makeText(this,"enter same password",Toast.LENGTH_SHORT).show()
            }else if(genderView.text.toString() == "Select Gender")
            {
                    Toast.makeText(this,"select gender",Toast.LENGTH_SHORT).show()
            }
            else {

                    jsonobj.put("username", name.text)
                    jsonobj.put("email", email.text)
                    jsonobj.put("password", password.text)
                    jsonobj.put("dob", DobView.text)
                    jsonobj.put("gender", genderView.text)

                    val que = Volley.newRequestQueue(this@SignUpActivity)
                    val request = JsonObjectRequest(Request.Method.POST, url, jsonobj,
                            Response.Listener {

                                response ->
                                Toast.makeText(this, "no error", Toast.LENGTH_SHORT).show()
                            }, Response.ErrorListener {
                        //Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    })
                    que.add(request)

            }
        }
    }
}
