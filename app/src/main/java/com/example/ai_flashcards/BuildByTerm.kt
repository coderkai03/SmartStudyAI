package com.example.ai_flashcards

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BuildByTerm : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_by_term)

        //sharedPreferences
        val sharedPrefs: SharedPreferences
        sharedPrefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        //buttons
        val topic_screen: Button
        val study_screen: Button

        val inputTerm = findViewById<EditText>(R.id.input_term)

        //assign buttons
        topic_screen = findViewById(R.id.topic_button)
        study_screen = findViewById(R.id.buildByTerm)

        // Add_button add clicklistener
        topic_screen.setOnClickListener {
            val intent = Intent(this, BuildByTopicActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }

        // Add_button add clicklistener
        study_screen.setOnClickListener {
            val intent = Intent(this, StudyActivity::class.java)

            with(sharedPrefs.edit()) {
                putString("input_term", inputTerm.toString())
                apply()
            }

            val spterm = sharedPrefs.getString("input_term", "not found")

            val term = if (spterm == null) "not found" else spterm

            Log.d("ByTerm", term)

            // start the activity connect to the specified class
            startActivity(intent)
        }
    }
}
