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
import com.example.bookdiary.databinding.FragmentLibraryBinding
import com.example.bookdiary.ui.adapter.BookGridAdapter

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private lateinit var bookGridRecyclerView: RecyclerView
    private lateinit var bookList: ArrayList<Book>
    private lateinit var bookGridAdapter: BookGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookGridRecyclerView = binding.recyclerViewLibrary
        bookGridRecyclerView.setHasFixedSize(true)
        bookGridRecyclerView.layoutManager = GridLayoutManager(context, 3)

        bookList = BookDatabase.getBookList()

        bookGridAdapter = BookGridAdapter(bookList) {
            val action =
                LibraryFragmentDirections.actionLibraryFragmentToBookFragment(
                    it.id
                )
            Navigation.findNavController(view).navigate(action)
        }

        bookGridRecyclerView.adapter = bookGridAdapter
    }
}