package com.plcoding.roomguideandroid

sealed interface BookEvent {
    object SaveBook: BookEvent
    data class SetTitle(val title: String): BookEvent
    data class SetDescription(val description: String): BookEvent
    data class SetPrice(val price: Int): BookEvent
    object ShowDialog: BookEvent
    object HideDialog: BookEvent
    data class ShowUpdate(val book: Book): BookEvent
    object HideUpdate: BookEvent
    data class SortBooks(val sortType: SortType): BookEvent
    data class DeleteBook(val book: Book): BookEvent
    //data class UpdateBook(val title: String, val description: String, val price: Int, val id: Int)
    data class UpdateBook(val book:Book):BookEvent
}