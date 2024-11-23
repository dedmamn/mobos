package com.example.vasilyevda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel: NotesViewModel = viewModel()) {
    val currentNote by viewModel.currentNote.observeAsState()
    val notesList by viewModel.notesList.observeAsState(emptyList()) // Наблюдаем за списком заметок

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Заметки") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (currentNote == null) {
                    viewModel.addNewNote() // Создаем новую заметку
                } else {
                    viewModel.saveCurrentNote() // Сохраняем изменения
                }
            }) {
                // Меняем иконку в зависимости от состояния
                Icon(
                    imageVector = if (currentNote == null) Icons.Filled.Add else Icons.Filled.Done,
                    contentDescription = if (currentNote == null) "Добавить заметку" else "Сохранить заметку"
                )
            }
        }
    ) { innerPadding ->
        if (currentNote == null) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(innerPadding)
            ) {
                items(notesList) { note -> // Используем notesList
                    NoteCard(
                        note = note,
                        onClick = { viewModel.selectNoteForEditing(note)},
                        onDelete = { viewModel.deleteNote(note) })
                }
            }
        } else {
            NoteEditScreen(viewModel = viewModel, innerPadding = innerPadding)
        }
    }
}

@Composable
fun NoteCard(note: Note, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 14.dp, bottom = 4.dp) // Добавляем отступ справа
                )
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.TopEnd) // Выравнивание по верхнему правому углу
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Удалить заметку", modifier = Modifier.size(20.dp))
                }
            }
            Text(
                text = note.text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


