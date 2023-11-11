package com.example.ai_flashcards

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class StudyActivity : AppCompatActivity() {

    //RecyclerView + flashcard list
    lateinit var rv_flashcards: RecyclerView
    val flashcardList = mutableListOf<Flashcard>()

    //shared prefs
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        //shared prefs
        sharedPrefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        //set up recyclerview
        setRVFlashcards()

        // Buttons to vals
        val shuffle = findViewById<Button>(R.id.shuffle)
        val edit = findViewById<Button>(R.id.edit)
        val add = findViewById<Button>(R.id.add)
        val delete = findViewById<Button>(R.id.delete)
        val home = findViewById<Button>(R.id.home)

        // Find background icons
        val shuffle_icon = resources.getDrawable(R.drawable.shuffle_icon)
        val edit_icon = resources.getDrawable(R.drawable.edit_icon)
        val add_icon = resources.getDrawable(R.drawable.add_icon)
        val delete_icon = resources.getDrawable(R.drawable.delete_icon)
        val home_icon = resources.getDrawable(R.drawable.home_icon)


        // Set icons
        shuffle.background = shuffle_icon
        edit.background = edit_icon
        add.background = add_icon
        delete.background = delete_icon
        home.background = home_icon

        // Set button onClicks (shuffle/delete are unique)
        setScreenButtons(edit, EditActivity::class.java)
        setScreenButtons(add, BuildByTopicActivity::class.java)
        setScreenButtons(home, MainActivity::class.java)
    }

    override fun onResume() {
        super.onResume()

        //load existing cards
        if (sharedPrefs.all != null) {
            Log.d("StudyActivity", "found existing cards")
            loadFlashcards()
        }
        else {

            //load dummy card data (REMOVE LATER)
            Log.d("StudyActivity", "starting with test cards")
            testFillFlashcards()
        }
    }

    private fun setRVFlashcards() {
        // Initialize UI elements after setContentView
        rv_flashcards = findViewById(R.id.flashcardRecyclerView)

        // Initialize the RecyclerView and FlashcardList
        rv_flashcards.adapter = FlashcardAdapter(flashcardList)
        rv_flashcards.layoutManager = LinearLayoutManager(this@StudyActivity)
    }


    private fun loadFlashcards() {
        flashcardList.clear()

        //retrieve flashcard data
        if (sharedPrefs.all != null){
            for ((key, value) in sharedPrefs.all) {
                val cardInfo = value.toString().split("|")
                flashcardList.add(Flashcard(cardInfo[0], cardInfo[1]))
                Log.d("StudyActivity", flashcardList.last().toString())
            }
        }
    }

    private fun testFillFlashcards() {
        Log.d("StudyActivity", "Filling with dummy data")

        clearCardsAndPrefs()

        //test dummy data for flashcards
        for (i in 0 until 10) {
            flashcardList.add(Flashcard("Term $i", "Definition $i"))
        }

        rv_flashcards.adapter?.notifyDataSetChanged()

        cacheCards()
    }

    private fun setScreenButtons(button: Button, screen: Class<*>) {


        //set icon button clicks
        button.setOnClickListener {
            val intent = Intent(this, screen)

            //only cache cards when going to add/edit
            if (screen == BuildByTopicActivity::class.java || screen == EditActivity::class.java){
                cacheCards()
            }
            else if (screen == MainActivity::class.java) {
                clearCardsAndPrefs()
            }

            startActivity(intent)
        }
    }

    private fun cacheCards() {
        with(sharedPrefs.edit()) {
            for (i in 0 until flashcardList.size) { //save cards to sharedPrefs
                val flashcard = arrayOf(
                    flashcardList[i].term,
                    flashcardList[i].definition
                ).joinToString("|")
                putString("card$i", flashcard)
            }
            apply()
        }
    }

    private fun clearCardsAndPrefs() {
        // Clear all flashcard data from shared preferences
        with(sharedPrefs.edit()) {
            for ((key, _) in sharedPrefs.all) {
                remove(key)
            }
            apply()
        }

        // Log to check if flashcardList is cleared
        Log.d("StudyActivity", "FlashcardList size before clearing: ${flashcardList.size}")

        // Clear flashcardList and update the adapter
        flashcardList.clear()
        rv_flashcards.adapter?.notifyDataSetChanged()
    }
}
