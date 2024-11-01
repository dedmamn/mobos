package com.example.vasilyevda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotesViewModel : ViewModel() {
    val notes = mutableListOf(
        Note("1","Первая заметка", "Допустим здесь очень длинный, осознанный текст, который передает смысл существования и бытия каждого существа на земле (но это не точно)."),
        Note("2","Вторая заметка", "Текст второй заметки")
    )

    private val _currentNote = MutableLiveData<Note?>()
    val currentNote: LiveData<Note?> = _currentNote

    fun selectNoteForEditing(note: Note) {
        _currentNote.value = note
    }

    fun addNewNote() {
        val newNote = Note("", "")
        _currentNote.value = newNote
        notes.add(newNote)
    }

    fun updateCurrentNoteTitle(newTitle: String) {
        _currentNote.value = _currentNote.value?.copy(title = newTitle)
    }

    fun updateCurrentNoteText(newText: String) {
        _currentNote.value = _currentNote.value?.copy(text = newText)
    }

    fun saveCurrentNote() {
        currentNote.value?.let { note ->
            val index = notes.indexOfFirst { it.id == note.id } // Поиск по id
            if (index >= 0) {
                notes[index] = note // Обновление заметки в списке
            }
        }
        _currentNote.value = null // Сброс текущей заметки
    }
}