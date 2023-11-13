package com.example.ai_flashcards

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class Flashcard(
    var term: String,
    var definition: String
)

class FlashcardAdapter(val flashcardList: MutableList<Flashcard>) : RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>(){

    class FlashcardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flashcard: Button
        val edit: Button
        val trash: Button

        init {
            flashcard = view.findViewById(R.id.flashcard)
            edit = view.findViewById(R.id.editcard)
            trash = view.findViewById(R.id.trashcard)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardAdapter.FlashcardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flashcard_item, parent, false)
        return FlashcardViewHolder(view)
    }

    override fun getItemCount() = flashcardList.size

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        //flashcard data (ONLY ONE SIDE VISIBLE)
        val card = flashcardList[position]

        //current button text
        holder.flashcard.text = card.term

        //onclick: flip flashcard
        holder.flashcard.setOnClickListener {
            holder.flashcard.text = if (holder.flashcard.text == card.term) card.definition else card.term
        }

        //onclick: edit card
        holder.edit.setOnClickListener {
            val sharedPrefs: SharedPreferences
            val context = holder.itemView.context

            //store card edits in sharedprefs
            sharedPrefs = context.getSharedPreferences("com.example.ai_flashcards", Context.MODE_PRIVATE)
            with (sharedPrefs.edit()) {
                putString("edit", card.term + "|" + card.definition)
                apply()
            }

            //switch to Edit screen
            val intent = Intent(context, EditActivity::class.java)
            context.startActivity(intent)
        }


        //onclick: delete card
        holder.trash.setOnClickListener {
            flashcardList.removeAt(position)
            this.notifyDataSetChanged()
        }
    }
}