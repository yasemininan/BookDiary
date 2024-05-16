package com.example.bookdiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookdiary.data.Book
import com.example.bookdiary.data.BookDatabase
import com.example.bookdiary.databinding.FragmentReadLaterBinding
import com.example.bookdiary.ui.adapter.BookGridAdapter

class ReadLaterFragment : Fragment() {
    private lateinit var binding: FragmentReadLaterBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: ArrayList<Book>
    private lateinit var bookGridAdapter: BookGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReadLaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        bookList = BookDatabase.getReadList()

        bookGridAdapter = BookGridAdapter(bookList) {
            val action =
                ReadLaterFragmentDirections.actionReadLaterFragmentToBookFragment(
                    it.id
                )
            Navigation.findNavController(view).navigate(action)
        }

        recyclerView.adapter = bookGridAdapter
    }
}