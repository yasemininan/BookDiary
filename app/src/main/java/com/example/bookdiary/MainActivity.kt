package com.example.bookdiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bookdiary.data.BookDatabase
import com.example.bookdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val NavigationView = binding.NavigationView
        NavigationView.setupWithNavController(navController)

        setupActionBarWithNavController(navController)

        val sharedPreference =  getSharedPreferences("BookDiary", Context.MODE_PRIVATE)
        val BooksAddedBefore = sharedPreference.getBoolean("BooksAddedBefore",false)

        if (!BooksAddedBefore) {
            val sharedPreferenceEditor = sharedPreference.edit()
            sharedPreferenceEditor.putBoolean("BooksAddedBefore", true)
            sharedPreferenceEditor.commit()
        }

        BookDatabase.initDB(this, BooksAddedBefore)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}