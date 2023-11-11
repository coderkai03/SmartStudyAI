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

class BuildByTopicActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_by_topic)

        //sharedPreferences
        val sharedPrefs: SharedPreferences
        sharedPrefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        val term_screen: Button
        val study_screen: Button

        val inputTopic = findViewById<EditText>(R.id.input_topic)

        term_screen = findViewById(R.id.by_term)
        study_screen = findViewById(R.id.buildByTopic)


        // Add_button add clicklistener
        term_screen.setOnClickListener {
            val intent = Intent(this, BuildByTerm::class.java)
            startActivity(intent)
        }

        // Add_button add clicklistener
        study_screen.setOnClickListener {
            val intent = Intent(this, StudyActivity::class.java)

            with(sharedPrefs.edit()) {
                putString("input_topic", inputTopic.toString())
                apply()
            }

            Log.d("ByTopic", sharedPrefs.toString())

            startActivity(intent)
        }
    }
}
