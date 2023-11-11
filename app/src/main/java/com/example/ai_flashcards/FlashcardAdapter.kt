package com.example.ai_flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Flashcard(
    var term: String,
    var definition: String
)

class FlashcardAdapter(val flashcardList: MutableList<Flashcard>) : RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>(){

    class FlashcardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flashcard: Button

        init {
            flashcard = view.findViewById(R.id.flashcard)
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

        holder.flashcard.setOnClickListener {
            holder.flashcard.text = if (holder.flashcard.text == card.term) card.definition else card.term
        }
    }
}