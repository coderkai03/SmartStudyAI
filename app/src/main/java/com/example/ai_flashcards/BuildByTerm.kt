package com.example.ai_flashcards

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BuildByTerm : AppCompatActivity() {

    // define the global variable
    private lateinit var question3: TextView
    // Add button Move previous activity
    private lateinit var previous_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_by_term)

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the Button and textview.
        previous_button = findViewById(R.id.third_activity_previous_button)
        question3 = findViewById(R.id.question3_id)

        // In question1 get the TextView use by findViewById()
        // In TextView set question Answer for message
        //question3.text = "Q3 - How to store heavy structured data in android? Ans - SQlite database ".trimIndent()

        // Add_button add clicklistener
        previous_button.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called SecondActivity with the following code:
            val intent = Intent(this, BuildByTopicActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }
    }
}
