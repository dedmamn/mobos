package com.example.vasilyevda

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NoteEditScreen(viewModel: NotesViewModel = viewModel(), innerPadding: PaddingValues) {
    val currentNote by viewModel.currentNote.observeAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(20.dp)
    ) {
        TextField(
            value = currentNote?.title ?: "",
            onValueChange = { newTitle -> viewModel.updateCurrentNoteTitle(newTitle) },
            label = { Text("Заголовок") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        TextField(
            value = currentNote?.text ?: "",
            onValueChange = { newText -> viewModel.updateCurrentNoteText(newText) },
            label = { Text("Текст заметки") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
    }
}