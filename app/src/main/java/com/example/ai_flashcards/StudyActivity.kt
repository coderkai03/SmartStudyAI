package com.example.ai_flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StudyActivity : AppCompatActivity() {

    //RecyclerView + flashcard list
    lateinit var rv_flashcards: RecyclerView
    val flashcardList = mutableListOf<Flashcard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        // Initialize UI elements after setContentView
        rv_flashcards = findViewById(R.id.flashcardRecyclerView)

        // Initialize the RecyclerView and FlashcardList
        rv_flashcards.adapter = FlashcardAdapter(flashcardList)
        rv_flashcards.layoutManager = LinearLayoutManager(this@StudyActivity)

        // Call the function to fill flashcards for testing
        testFillFlashcards()

        // Buttons to vals
        val shuffle = findViewById<Button>(R.id.shuffle)
        val edit = findViewById<Button>(R.id.edit)
        val add = findViewById<Button>(R.id.add)
        val delete = findViewById<Button>(R.id.delete)

        // Find background icons
        val shuffle_icon = resources.getDrawable(R.drawable.shuffle_icon)
        val edit_icon = resources.getDrawable(R.drawable.edit_icon)
        val add_icon = resources.getDrawable(R.drawable.add_icon)
        val delete_icon = resources.getDrawable(R.drawable.delete_icon)

        // Set icons
        shuffle.background = shuffle_icon
        edit.background = edit_icon
        add.background = add_icon
        delete.background = delete_icon

        // Set button onClicks (shuffle/delete are unique)
        setScreenButtons(edit, EditActivity::class.java)
        setScreenButtons(add, BuildByTopicActivity::class.java)
    }

    private fun testFillFlashcards() {
        for (i in 0 until 10) {
            flashcardList.add(Flashcard("Term $i", "Definition $i"))
        }

        rv_flashcards.adapter?.notifyDataSetChanged()
    }

    private fun setScreenButtons(button: Button, screen: Class<*>) {
        button.setOnClickListener {
            val intent = Intent(this, screen)
            startActivity(intent)
        }
    }
}
