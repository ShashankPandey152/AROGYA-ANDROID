package com.example.hp.arogya

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.tomer.fadingtextview.FadingTextView
import kotlinx.android.synthetic.main.activity_bmi_bmr_result.*
import kotlinx.android.synthetic.main.app_bar_bmi_bmr_result.*
import kotlinx.android.synthetic.main.content_bmi_bmr_result.*

class BmiBmrResultActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_bmr_result)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val mode = intent.getStringExtra("mode")

        if(mode == "bmi") {
            result_text_view.text = "Your BMI is:"
        } else if(mode == "bmr") {
            result_text_view.text = "You can consume: "
        }

        printResult(mode)
    }

    @SuppressLint("SetTextI18n")
    fun printResult(mode: String) {

        if(mode == "bmi") {

            val BMI = intent.getStringExtra("bmi").toString()

            bmi_bmr_result_text_view.text = BMI

            val bmi = (bmi_bmr_result_text_view.text as String).toFloat()

            var result = ""
            val height = intent.getStringExtra("height").toString().toFloat()
            val weight = intent.getStringExtra("weight").toString().toFloat()
            if(bmi <= 18.5) {
                result = "Underweight"
                val new_weight = 18.5 * height * height
                change_weight.text = String.format("You need to gain: %.1f kgs", new_weight - weight)
            } else if(bmi > 18.5 && bmi <= 24.9) {
                result = "Normal"
            } else if(bmi in 25.0..29.9) {
                result = "Overweight"
                val new_weight = 24.9 * height * height
                change_weight.text = String.format("You need to lose: %.1f kgs", weight - new_weight)
            } else {
                result = "Obese"
                val new_weight = 24.9 * height * height
                change_weight.text = String.format("You need to lost: %.1f kgs", weight - new_weight)
            }

            var texts = arrayOf<String>(BMI, result)
            val bmi_text = findViewById<FadingTextView>(R.id.bmi_bmr_result_text_view)
            bmi_text.setTexts(texts)

        } else if(mode == "bmr") {

            val BMR = intent.getStringExtra("bmr").toString()

            val texts = arrayOf<String>(BMR)
            val bmr_text = findViewById<FadingTextView>(R.id.bmi_bmr_result_text_view)
            bmr_text.setTexts(texts)

            change_weight.text = "calories"

        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
