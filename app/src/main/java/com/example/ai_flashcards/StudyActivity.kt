package com.example.ai_flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class StudyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        //buttons to vals
        val shuffle = findViewById<Button>(R.id.shuffle)
        val edit = findViewById<Button>(R.id.edit)
        val add = findViewById<Button>(R.id.add)
        val delete = findViewById<Button>(R.id.delete)

        //find background icons
        val shuffle_icon = resources.getDrawable(R.drawable.shuffle_icon)
        val edit_icon = resources.getDrawable(R.drawable.edit_icon)
        val add_icon = resources.getDrawable(R.drawable.add_icon)
        val delete_icon = resources.getDrawable(R.drawable.delete_icon)

        //set icons
        shuffle.background = shuffle_icon
        edit.background = edit_icon
        add.background = add_icon
        delete.background = delete_icon

        //set button onClicks (shuffle/delete are unique)
        setScreenButtons(edit, EditActivity::class.java)
        setScreenButtons(add, BuildByTopicActivity::class.java)

    }

    private fun setScreenButtons(button: Button, screen: Class<*>) {
        button.setOnClickListener {
            val intent = Intent(this, screen)
            startActivity(intent)
        }
    }
}