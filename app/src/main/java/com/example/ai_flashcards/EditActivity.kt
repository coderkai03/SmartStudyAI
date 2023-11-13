package com.example.ai_flashcards

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText

class EditActivity : AppCompatActivity() {
    private lateinit var sharedprefs: SharedPreferences
    private lateinit var editTerm: EditText
    private lateinit var editDefinition: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        sharedprefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        editTerm = findViewById<EditText>(R.id.edit_term)
        editDefinition = findViewById<EditText>(R.id.edit_definition)

        //load card edits into EditTexts
        loadEdits()

    }

    private fun setSave(edits: String) {
        val save_button = findViewById<Button>(R.id.edit_save)

        save_button.setOnClickListener{
            val saveTerm = editTerm.text.toString()
            val saveDefinition = editDefinition.text.toString()


            for ((key, value) in sharedprefs.all.entries) {
                if (value == edits && key != "edit") {
                    Log.d("EditActivity", "save edits to "+key)
                    sharedprefs.edit().putString(
                        key,
                        saveTerm+"|"+saveDefinition
                    ).apply()


                    val card = sharedprefs.getString(key, null)
                    if (card != null) {
                        Log.d("EditActivity", card)
                    }

                    break
                }
            }

            val intent = Intent(this, StudyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadEdits() {
        val edit = sharedprefs.getString("edit", null)

        if (edit != null){
            val card = edit.split("|")
            editTerm.text = Editable.Factory.getInstance().newEditable(card[0])
            editDefinition.text = Editable.Factory.getInstance().newEditable(card[1])


            setSave(edit)
        }
    }
}