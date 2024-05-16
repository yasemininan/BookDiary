package com.example.bookdiary.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.example.bookdiary.R
import com.example.bookdiary.data.Book

class BookListAdapter(
    private val BookList: ArrayList<Book>,
    private var onBookClick: ((Book) -> Unit)? = null
): RecyclerView.Adapter<BookListAdapter.BookListViewHolder>() {

    private lateinit var checkButtons: Array<Button>

    class BookListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewBookImage)
        val nameTextView: TextView = itemView.findViewById(R.id.bookName)
        val writerTextView: TextView = itemView.findViewById(R.id.writer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false)
        return BookListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return BookList.size
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        val book = BookList[position]

        holder.itemView.setOnClickListener {
            onBookClick?.invoke(book)
        }

        holder.imageView.setImageResource(book.imageUrl)
        holder.nameTextView.text = book.name
        holder.writerTextView.text = book.author

        checkButtons = arrayOf(
            holder.itemView.findViewById(R.id.checkOne),
            holder.itemView.findViewById(R.id.checkTwo),
            holder.itemView.findViewById(R.id.checkThree),
        )

        updateRateImages(holder.itemView, book)
    }

    private fun updateRateImages(itemView: View, book: Book) {
        val rating = if(book.rating !== null) book.rating else 0

        // Fill check icons until rating
        for (i in 0 until rating!!) {
            checkButtons[i].background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_check)
        }

        // Clear other check icons
        for (i in rating  until 3) {
            checkButtons[i].background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_check_outline)
        }
    }
}