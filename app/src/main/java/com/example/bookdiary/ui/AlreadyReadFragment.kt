package com.example.bookdiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookdiary.data.Book
import com.example.bookdiary.data.BookDatabase
import com.example.bookdiary.databinding.FragmentAlreadyReadBinding
import com.example.bookdiary.ui.adapter.BookListAdapter

class AlreadyReadFragment : Fragment() {
    private lateinit var binding: FragmentAlreadyReadBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: ArrayList<Book>
    private lateinit var bookListAdapter: BookListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlreadyReadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        bookList = BookDatabase.getBooksWithRating()

        bookListAdapter = BookListAdapter(bookList) {
            val action =
                AlreadyReadFragmentDirections.actionAlreadyReadFragmentToBookFragment(
                    it.id
                )
            Navigation.findNavController(view).navigate(action)
        }

        recyclerView.adapter = bookListAdapter
    }
}