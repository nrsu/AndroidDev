package com.plcoding.roomguideandroid

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Upsert
    suspend fun upsertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("UPDATE book SET title=:Title, description=:Description, price=:Price WHERE id=:Id")
    fun updateBooks(Title:String, Description:String, Price:String, Id:String)

    @Query("SELECT * FROM book ORDER BY price ASC")
    fun getBooksAsc(): Flow<List<Book>>

    @Query("SELECT * FROM book ORDER BY price DESC")
    fun getBooksDesc(): Flow<List<Book>>

}