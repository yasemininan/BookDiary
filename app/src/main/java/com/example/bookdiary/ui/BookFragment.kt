package com.example.bookdiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.bookdiary.R
import com.example.bookdiary.data.Book
import com.example.bookdiary.data.BookDatabase
import com.example.bookdiary.databinding.FragmentBookBinding

class BookFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentBookBinding
    private lateinit var checkButtons: Array<Button>

    // Data
    private lateinit var book: Book

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookBinding.inflate(inflater, container, false)

        checkButtons = arrayOf(
            binding.checkOne,
            binding.checkTwo,
            binding.checkThree,
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val bookId = BookFragmentArgs.fromBundle(it).bookId
            book = BookDatabase.getBook(bookId)!!

            // Add Click Listeners
            binding.checkOne.setOnClickListener { onCheckClick( 1) }
            binding.checkTwo.setOnClickListener { onCheckClick( 2) }
            binding.checkThree.setOnClickListener { onCheckClick( 3) }
            binding.buttonReadLater.setOnClickListener{ onReadLaterClick() }

            updateUI()
        }
    }

    private fun updateUI() {
        binding.bookImage.setImageResource(book.imageUrl)
        binding.bookName.text = book.name
        binding.writer.text = book.author
        binding.publishYear.text = book.year.toString()
        binding.bookDescription.text = book.description

        if (book.rating !== null) {
            updateRateImages()
        }

        updateReadLaterImage()
    }

    private fun updateRateImages() {
        val rating = if(book.rating !== null) book.rating else 0

        // Fill checkboxes until rating
        for (i in 0 until rating!!) {
            checkButtons[i].background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_check)
        }

        // Clear other checkboxes
        for (i in rating  until 3) {
            checkButtons[i].background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_outline)
        }
    }

    private fun updateReadLaterImage() {
        val inReadList = BookDatabase.isInReadList(book.id)

        val backgroundDrawable: Int = if (inReadList) {
            R.drawable.ic_bookmark_filled
        } else {
            R.drawable.ic_bookmark_outline
        }

        binding.buttonReadLater.background = ContextCompat.getDrawable(requireContext(), backgroundDrawable)
    }

    private fun onCheckClick(rating: Int) {
        // Update rating
        BookDatabase.updateBookRating(book, rating)
        updateRateImages()

        // Remove from read list
        BookDatabase.removeFromReadList(book)
        updateReadLaterImage()
    }

    private fun onReadLaterClick() {
        // Update read list
        val inReadList = BookDatabase.isInReadList(book.id)

        if (inReadList) {
            BookDatabase.removeFromReadList(book)
        } else {
            BookDatabase.addToReadList(book)
        }

        updateReadLaterImage()

        // Remove rating
        BookDatabase.updateBookRating(book, null)
        updateRateImages()
    }
}