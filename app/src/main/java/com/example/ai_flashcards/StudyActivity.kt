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
import kotlin.random.Random

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
        Log.d("StudyActivity", sharedPrefs.all.toString())

        //set up recyclerview
        setRVFlashcards()

        // Buttons to vals
        val shuffle = findViewById<Button>(R.id.shuffle)
        val edit = findViewById<Button>(R.id.edit)
        val add = findViewById<Button>(R.id.add)
        //val delete = findViewById<Button>(R.id.delete)
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
        //delete.background = delete_icon
        home.background = home_icon

        // Set button onClicks (shuffle/delete are unique)
        shuffleCards(shuffle)
        setScreenButtons(edit, EditActivity::class.java)
        setScreenButtons(add, BuildByTopicActivity::class.java)
        setScreenButtons(home, MainActivity::class.java)
    }

    private fun shuffleCards(button: Button) {

        button.setOnClickListener{
            val random = java.util.Random()

            //shuffle cards list
            for (i in 0 until flashcardList.size) {
                val j = random.nextInt(i+1)

                //swap elements
                val temp = flashcardList[i]
                flashcardList[i] = flashcardList[j]
                flashcardList[j] = temp
            }

            rv_flashcards.adapter?.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()

        //
        if (sharedPrefs.all.size != 0) {
            Log.d("StudyActivity", "found existing cards")
            loadFlashcards()
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

        for ((key, value) in sharedPrefs.all.entries) {

            if (key != "input_topic" && key != "edit") {
                val value = sharedPrefs.getString(key, "nullVal")

                // Process key and value here
                val cardInfo = value?.toString()?.split("|")
                Log.d("StudyActivity", "loading $cardInfo")
                if (cardInfo != null && cardInfo.size == 2) {
                    Log.d("StudyActivity", "loading card!")
                    flashcardList.add(Flashcard(cardInfo[0], cardInfo[1]))
                    Log.d("StudyActivity", flashcardList.last().toString())
                }
            }
        }
        rv_flashcards.adapter?.notifyDataSetChanged()
    }

    private fun setScreenButtons(button: Button, screen: Class<*>) {
        //set icon button clicks
        button.setOnClickListener {
            val intent = Intent(this, screen)

            //only cache cards when going to add/edit
            if (screen == BuildByTopicActivity::class.java || screen == EditActivity::class.java) {
                cacheCards()
            }

            startActivity(intent)
        }
    }

    private fun cacheCards() {
        Log.d("StudyActivity", "caching cards")

        //clear sharedprefs first
        val topic = sharedPrefs.getString("input_topic", "null")
        sharedPrefs.edit().clear()

        //add topic if existed
        if (topic != "null") {
            sharedPrefs.edit().putString("input_topic", topic)
        }

        //add cards to sharedprefs
        with(sharedPrefs.edit()) {
            for (i in 0 until flashcardList.size) { //save cards to sharedPrefs

                val flashcard = arrayOf(
                    flashcardList[i].term,
                    flashcardList[i].definition

                ).joinToString("|")

                putString("card${i + 1}", flashcard)
            }

            apply()
        }
    }
}