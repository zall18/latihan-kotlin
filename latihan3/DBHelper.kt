package com.example.booklibrarysqllite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        private val DB_NAME = "bookLibrary"
        private val DB_VERSION = 1
        private val ID = "id"
        private val NAME_BOOK = "name_book"
        private val PUBLISHER_NAME = "publisher"
        private val AUTHOR_NAME = "author"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_TABLE = "CREATE TABLE book (id INTEGER PRIMARY KEY, name_book TEXT, publisher TEXT, author TEXT)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS book"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun getAllBook(): MutableList<bookModel>{
        val booklist:MutableList<bookModel> = mutableListOf<bookModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM book"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    val idIndex = cursor.getColumnIndex("id")

                    val nameBookIndex = cursor.getColumnIndex("name_book")

                    val publisherIndex = cursor.getColumnIndex("publisher")

                    val authorIndex = cursor.getColumnIndex("author")
                    booklist.add(bookModel((Integer.parseInt(cursor.getString(idIndex))), cursor.getString(nameBookIndex), cursor.getString(publisherIndex), cursor.getString(authorIndex)))
                }while (cursor.moveToNext())

            }
        }
        cursor.close()
        return booklist
    }

    fun createBookData(books: bookModel): Boolean{

        val db = writableDatabase
        val values = ContentValues()
        values.put("name_book", books.name)
        values.put("publisher", books.publisher)
        values.put("author", books.author)
        val _response = db.insert("book", null, values)
        db.close()
        return (Integer.parseInt("$_response") != -1)

    }

    fun getBookById(id: Int): MutableList<bookModel>{

        val booklist:MutableList<bookModel> = mutableListOf<bookModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM book WHERE id = $id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor.moveToFirst()
        val idIndex = cursor.getColumnIndex("id")

        val nameBookIndex = cursor.getColumnIndex("name_book")

        val publisherIndex = cursor.getColumnIndex("publisher")

        val authorIndex = cursor.getColumnIndex("author")
        booklist.add(bookModel((Integer.parseInt(cursor.getString(idIndex))), cursor.getString(nameBookIndex), cursor.getString(publisherIndex), cursor.getString(authorIndex)))

        return booklist
    }

    fun deleteBooks(id: Int): Boolean{

        val db = writableDatabase
        val _success = db.delete("book", "id =? ", arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != 1

    }

    fun updateBooks(books: bookModel) : Boolean {

        val db = writableDatabase
        val values = ContentValues()
        values.put("name_book", books.name)
        values.put("publisher", books.publisher)
        values.put("author", books.author)
        val _success = db.update("book", values, "id =? ", arrayOf(books.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1

    }
}