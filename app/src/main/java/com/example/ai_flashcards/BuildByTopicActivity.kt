package com.example.ai_flashcards

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BuildByTopicActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_by_topic)

        val term_screen: Button
        val study_screen: Button

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
            startActivity(intent)
        }
    }
}
