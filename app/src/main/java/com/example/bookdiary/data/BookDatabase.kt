package com.example.bookdiary.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getIntOrNull
import com.example.bookdiary.R

object BookDatabase {
    lateinit var db: SQLiteDatabase
    private val books = HashMap<String, Book>()

    private fun execQuery(sql: String): ArrayList<Book> {
        val bookList = ArrayList<Book>()

        val cursor = db.rawQuery(sql, null)

        val idIdx = cursor.getColumnIndex("id")
        val nameIdx = cursor.getColumnIndex("name")
        val descriptionIdx = cursor.getColumnIndex("description")
        val imageUrlIdx = cursor.getColumnIndex("imageUrl")
        val authorIdx = cursor.getColumnIndex("author")
        val yearIdx = cursor.getColumnIndex("year")
        val ratingIdx = cursor.getColumnIndex("rating")
        val inReadListIdx = cursor.getColumnIndex("inReadList")

        while(cursor.moveToNext()) {
            val id = cursor.getString(idIdx)
            val name = cursor.getString(nameIdx)
            val description = cursor.getString(descriptionIdx)
            val imageUrl = cursor.getInt(imageUrlIdx)
            val author = cursor.getString(authorIdx)
            val year = cursor.getInt(yearIdx)
            val rating = cursor.getIntOrNull(ratingIdx)
            val inReadList = cursor.getInt(inReadListIdx) == 1

            val book = Book(
                id,
                name,
                description,
                imageUrl,
                author,
                year,
                rating,
                inReadList
            )

            bookList.add(book)
        }

        cursor.close()
        return bookList
    }

    private fun getBooksFromIMDB(): ArrayList<Book> {
        val bookList = ArrayList<Book>();

        bookList.add(Book(
            "1",
            "Jane Eyre",
            "This a timeless tale of love, independence, and resilience, as the protagonist, Jane Eyre, navigates obstacles, discovers her inner strength, and finds love amidst societal expectations and personal challenges in Victorian England.",
            R.drawable.janeeyre,
            "Charlotte Brontë",
            1847,
            null,
            false
        ))

        bookList.add(Book(
            "2",
            "To Kill a Mockingbird",
            "This classic novel tackles themes of racial injustice and moral growth in the American South.",
            R.drawable.killamockingbird,
            "Harper Lee",
            1960,
            null,
            false
        ))

        bookList.add(Book(
            "3",
            "The Great Gatsby",
            "Set in the Jazz Age, this novel is a critique of the American Dream and the excesses of wealth.",
            R.drawable.greatgatsby,
            "F. Scott Fitzgerald",
            1925,
            null,
            false
        ))

        bookList.add(Book(
            "4",
            "The Catcher in the Rye",
            "A coming-of-age novel that follows the rebellious teenager Holden Caulfield as he navigates life and society.",
            R.drawable.thecatcher,
            "J.D. Salinger",
            1951,
            null,
            false
        ))

        bookList.add(Book(
            "5",
            "Pride and Prejudice",
            "A timeless romance that explores social class, manners, and marriage in early 19th-century England.",
            R.drawable.pride,
            "Jane Austen",
            1813,
            null,
            false
        ))

        bookList.add(Book(
            "6",
            "Harry Potter and the Philosophers Stone",
            "A fantasy series about the adventures of a young wizard, Harry Potter, and his friends as they battle against the dark wizard Voldemort.",
            R.drawable.harrypotter,
            "J.K. Rowling",
            1997,
            null,
            false
        ))

        bookList.add(Book(
            "7",
            "The Lord of the Rings - The Fellowship of the Ring",
            "A high fantasy epic set in the fictional world of Middle-earth, following the quest to destroy the One Ring and defeat the Dark Lord Sauron.",
            R.drawable.lordoftherings,
            "J.R.R. Tolkien",
            1954,
            null,
            false
        ))

        bookList.add(Book(
            "8",
            "The Diary of a Young Girl",
            "The diary of a Jewish girl living in hiding during the Nazi occupation of the Netherlands, offering a firsthand account of the Holocaust.",
            R.drawable.diary,
            "Anne Frank",
            1947,
            null,
            false
        ))

        bookList.add(Book(
            "9",
            "Moby-Dick",
            "A novel about obsession, revenge, and the human condition, centered around the pursuit of the legendary white whale, Moby Dick",
            R.drawable.mobydick,
            "Herman Melville",
            1851,
            null,
            false
        ))

        bookList.add(Book(
            "10",
            "The Hobbit",
            "A fantasy adventure novel set in Middle-earth, following the journey of Bilbo Baggins as he embarks on a quest to reclaim treasure guarded by the dragon Smaug.",
            R.drawable.hobbit,
            "J.R.R. Tolkien",
            1937,
            null,
            false
        ))

        bookList.add(Book(
            "11",
            "The Little Prince",
            "A philosophical tale about a young prince who travels from planet to planet, learning about love, friendship, and the human condition.",
            R.drawable.littleprince,
            "Antoine de Saint-Exupéry",
            1943,
            null,
            false
        ))

        bookList.add(Book(
            "12",
            "1984",
            "A dystopian novel that explores themes of surveillance, government control, and individual freedom.",
            R.drawable.g1984,
            "George Orwell",
            1949,
            null,
            false
        ))

        bookList.add(Book(
            "13",
            "The Alchemist",
            "A fable about a shepherd named Santiago who embarks on a journey to discover his personal legend and fulfill his dreams.",
            R.drawable.alchemist,
            "Paulo Coelho",
            1988,
            null,
            false
        ))

        bookList.add(Book(
            "14",
            "The Adventures of Huckleberry Finn",
            "A classic American novel that follows the adventures of Huck Finn and his friend Jim, a runaway slave, as they travel down the Mississippi River.",
            R.drawable.huckleberry,
            "Mark Twain",
            1884,
            null,
            false
        ))

        bookList.add(Book(
            "15",
            "Frankenstein",
            "A Gothic novel that explores themes of scientific ethics, ambition, and the nature of humanity, as scientist Victor Frankenstein creates a living being from dead body parts.",
            R.drawable.frankenstein,
            "Mary Shelley",
            1818,
            null,
            false
        ))

        return bookList
    }

    fun initDB(mainActivity: AppCompatActivity, BooksAddedBefore: Boolean) {
        try {
            // Create DB
            db = mainActivity.openOrCreateDatabase("Books", Context.MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY, name VARCHAR, description VARCHAR, imageUrl INTEGER, author VARCHAR, year INTEGER, rating INTEGER, inReadList INTEGER)")

            val booksFromImdb = getBooksFromIMDB()
            booksFromImdb.forEach {
                println("Book:" + it.name)
            }

            if (!BooksAddedBefore) {
                // Add books
                booksFromImdb.forEach {
                    println("Insert Book:" + it.name)
                    db.execSQL("INSERT INTO books (name, description, imageUrl, author, year, rating, inReadList) VALUES (" +
                            "'${it.name}'," +
                            "'${it.description}'," +
                            it.imageUrl+ "," +
                            "'${it.author}'," +
                            "${it.year}," +
                            "null," +
                            "0)")
                }
            }

            val bookList = execQuery("SELECT * FROM books")
            bookList.forEach {
                println("Inserted book" + it.id + " Name:" + it.name)
                books[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getBook(BookId: String): Book {
        val bookList = execQuery("SELECT * FROM books WHERE id = $BookId")
        return bookList[0]
    }

    fun getBookList(): ArrayList<Book> {
        return execQuery("SELECT * FROM books")
    }

    fun updateBookRating(book: Book, rating: Int?) {
        book.rating = rating
        db.execSQL("UPDATE books SET rating = $rating WHERE id = ${book.id}")
    }

    fun getBooksWithRating(): ArrayList<Book> {
        return execQuery("SELECT * FROM books WHERE rating IS NOT NULL")
    }

    fun addToReadList(book: Book) {
        book.inReadList = true
        db.execSQL("UPDATE books SET inReadList = 1 WHERE id = ${book.id}")
    }

    fun removeFromReadList(book: Book) {
        book.inReadList = true
        db.execSQL("UPDATE books SET inReadList = 0 WHERE id = ${book.id}")
    }

    fun isInReadList(BookId: String): Boolean {
        val book = getBook(BookId)
        return book.inReadList
    }

    fun getReadList(): ArrayList<Book> {
        return execQuery("SELECT * FROM books WHERE inReadList <> 0")
    }
}
