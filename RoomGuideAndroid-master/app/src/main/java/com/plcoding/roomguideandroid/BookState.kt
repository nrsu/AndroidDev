package com.plcoding.roomguideandroid

data class BookState(
    val books: List<Book> = emptyList(),
    val title: String = "",
    val description: String = "",
    val price: Int = 0,
    val isAddingBook: Boolean = false,
    val sortType: SortType = SortType.PRICE_ASC,
    val isUpdating: Boolean = false,
    val currentBook: Book = Book("s", "s", 0)
)
