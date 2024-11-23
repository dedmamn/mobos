package com.example.vasilyevda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(private val notesUseCase: NotesUseCase) : ViewModel() {
    private val _currentNote = MutableLiveData<Note?>()
    val currentNote: LiveData<Note?> = _currentNote


    val notesList = MutableLiveData<List<Note>>() // Состояние списка заметок

    init {
        fetchNotes() // Получаем заметки при инициализации
    }

    fun fetchNotes() {
        viewModelScope.launch {
            notesList.value = notesUseCase.getAllNotes()
        }
    }

    fun selectNoteForEditing(note: Note) {
        _currentNote.value = note
    }

    fun addNewNote() {
        val newNote = Note(title = "", text = "")
        viewModelScope.launch {
            notesUseCase.addNote(newNote)
            fetchNotes() // Обновляем список заметок после добавления
            _currentNote.value = newNote // Устанавливаем текущую заметку для редактирования
        }
    }

    fun updateCurrentNoteTitle(newTitle: String) {
        _currentNote.value = _currentNote.value?.copy(title = newTitle)
    }

    fun updateCurrentNoteText(newText: String) {
        _currentNote.value = _currentNote.value?.copy(text = newText)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesUseCase.deleteNote(note)
            fetchNotes()
        }
    }

    fun saveCurrentNote() {
        currentNote.value?.let { note ->
            viewModelScope.launch {
                notesUseCase.updateNote(note) // Обновляем заметку в базе данных
                fetchNotes() // Обновляем список заметок после сохранения
            }
        }
        _currentNote.value = null // Сбрасываем текущую заметку
    }
}
