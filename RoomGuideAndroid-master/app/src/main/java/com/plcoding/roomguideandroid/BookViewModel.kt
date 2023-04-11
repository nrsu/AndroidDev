package com.plcoding.roomguideandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class BookViewModel(
    private val dao: BookDao
): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.PRICE_ASC)
    private val _books = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.PRICE_ASC -> dao.getBooksAsc()
                SortType.PRICE_DESC -> dao.getBooksDesc()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(BookState())
    val state = combine(_state, _sortType, _books) { state, sortType, books ->
        state.copy(
            books = books,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BookState())

    fun onEvent(event: BookEvent) {
        when(event) {
            is BookEvent.DeleteBook -> {
                viewModelScope.launch {
                    dao.deleteBook(event.book)
                }
            }

            BookEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingBook = false
                ) }
            }
            BookEvent.SaveBook -> {
                val title = state.value.title
                val description = state.value.description
                val price = state.value.price

                if(title.isBlank() || description.isBlank() || price==null) {
                    return
                }

                val book = Book(
                    title = title,
                    description = description,
                    price = price
                )
                viewModelScope.launch {
                    dao.upsertBook(book)
                }
                _state.update { it.copy(
                    isAddingBook = false,
                    title = "",
                    description = "",
                    price = 0
                ) }
            }
            is BookEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            is BookEvent.SetDescription -> {
                _state.update { it.copy(
                    description = event.description
                ) }
            }
            is BookEvent.SetPrice -> {
                _state.update { it.copy(
                    price = event.price
                ) }
            }
            BookEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingBook = true
                ) }
            }
            is BookEvent.ShowUpdate -> {
                _state.update { it.copy(
                    isUpdating = true,
                    currentBook = event.book
                ) }
            }
            BookEvent.HideUpdate -> {
                _state.update { it.copy(
                    isUpdating = false
                ) }
            }

            is BookEvent.SortBooks -> {
                _sortType.value = event.sortType
            }
            is BookEvent.UpdateBook ->{
                val title = state.value.title
                val description = state.value.description
                val price = state.value.price
                val currentBook = state.value.currentBook
                if(title.isBlank() || description.isBlank() || price==null) {
                    return
                }

                viewModelScope.launch {
                    dao.updateBooks(title, description, price.toString(), currentBook.id.toString())
                }
                _state.update { it.copy(
                    isUpdating = false,
                    title = "",
                    description = "",
                    price = 0
                ) }
            }

        }
    }
}