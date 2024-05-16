package com.example.bookdiary.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.example.bookdiary.R
import com.example.bookdiary.data.Book

class BookGridAdapter(
    private val BookList: ArrayList<Book>,
    var onBookClick: ((Book) -> Unit)? = null
    ): RecyclerView.Adapter<BookGridAdapter.BookGridViewHolder>() {

    class BookGridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageViewBookImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookGridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_grid_item, parent, false)
        return BookGridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return BookList.size
    }

    override fun onBindViewHolder(holder: BookGridViewHolder, position: Int) {
        val book = BookList[position]

        holder.imageView.setImageResource(book.imageUrl)
        holder.imageView.setOnClickListener {
            onBookClick?.invoke(book)
        }
    }
}