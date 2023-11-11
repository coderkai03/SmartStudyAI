package com.example.ai_flashcards

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // define the global variable
    private lateinit var question1: TextView
    // Add button Move to Activity
    private lateinit var build_screen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref: SharedPreferences
        sharedPref = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        //set screen button
        build_screen = findViewById<Button>(R.id.start_learning)


        // Add_button add clicklistener
        build_screen.setOnClickListener {
            val intent = Intent(this, BuildByTopicActivity::class.java)

            // Clear all flashcard data from shared preferences
            sharedPref.edit().clear().apply()
            Log.d("Home", "cleared prefs: "+sharedPref.all.toString())

            startActivity(intent)
        }
    }
}
