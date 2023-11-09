package com.example.ai_flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class StudyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        val shuffle = findViewById<Button>(R.id.shuffle)
        val shuffle_icon = resources.getDrawable(R.drawable.shuffle_icon)
        shuffle.background = shuffle_icon

        val edit = findViewById<Button>(R.id.edit)
        val edit_icon = resources.getDrawable(R.drawable.edit_icon)
        edit.background = edit_icon

        val add = findViewById<Button>(R.id.add)
        val add_icon = resources.getDrawable(R.drawable.add_icon)
        add.background = add_icon

        val delete = findViewById<Button>(R.id.delete)
        val delete_icon = resources.getDrawable(R.drawable.delete_icon)
        delete.background = delete_icon


        testToastButton(shuffle, "shuffle")
        testToastButton(edit, "edit")
        testToastButton(add, "add")
        testToastButton(delete, "delete")

    }

    private fun testToastButton(button: Button, text: String) {
        button.setOnClickListener {
            val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}