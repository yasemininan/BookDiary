package com.example.bookdiary.data

data class Book(
    var id: String,
    var name: String,
    var description: String,
    var imageUrl: Int,
    var author: String,
    var year: Int,
    var rating: Int?,
    var inReadList: Boolean
)
