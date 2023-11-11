package com.example.ai_flashcards

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BuildByTerm : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_by_term)

        val topic_screen: Button
        val study_screen: Button

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the Button and textview.
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
            // start the activity connect to the specified class
            startActivity(intent)
        }
    }
}
