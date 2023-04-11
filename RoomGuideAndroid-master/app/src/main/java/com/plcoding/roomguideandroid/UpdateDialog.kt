package com.plcoding.roomguideandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun UpdateDialog(
    state: BookState,
    onEvent: (BookEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(BookEvent.HideUpdate)
        },
        title = { Text(text = "Update book") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(BookEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = state.currentBook.title)
                    }
                )
                TextField(
                    value = state.description,
                    onValueChange = {
                        onEvent(BookEvent.SetDescription(it))
                    },
                    placeholder = {
                        Text(text = state.currentBook.description)
                    }
                )
                TextField(
                    value = state.price.toString(),
                    onValueChange = {
                        onEvent(BookEvent.SetPrice(it.toInt()))
                    },
                    placeholder = {
                        Text(text = state.currentBook.price.toString())
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(BookEvent.UpdateBook(state.currentBook))

                }) {
                    Text(text = "Update")
                }
            }
        }
    )
}

