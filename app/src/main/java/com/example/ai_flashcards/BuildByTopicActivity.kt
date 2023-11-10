package com.example.ai_flashcards

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BuildByTopicActivity : AppCompatActivity() {

    // define the global variable
    private lateinit var question2: TextView
    // Add button Move to next Activity and previous Activity
    private lateinit var next_button: Button
    private lateinit var previous_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_by_topic)

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the both Button and textview
        next_button = findViewById(R.id.second_activity_next_button)
        previous_button = findViewById(R.id.second_activity_previous_button)
        question2 = findViewById(R.id.question2_id)

        // In question1 get the TextView use by findViewById()
        // In TextView set question Answer for message
        //question2.text = "Q2 - What is ADB in android? Ans - Android Debug Bridge".trimIndent()

        // Add_button add clicklistener
        next_button.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called ThirdActivity with the following code.
            val intent = Intent(this, BuildByTerm::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }

        // Add_button add clicklistener
        previous_button.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called oneActivity with the following code
            val intent = Intent(this, MainActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }
    }
}
