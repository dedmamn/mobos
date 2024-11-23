package com.example.vasilyevda

class NotesUseCase(private val notesDao: NotesDao) {
    suspend fun initializeNotes(initialNotes: List<Note>) {
        for (note in initialNotes) {
            notesDao.insert(note) // Вставляем каждую заметку в базу данных
        }
    }
    suspend fun addNote(note: Note) {
        notesDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        notesDao.update(note)
    }

    suspend fun getAllNotes(): List<Note> {
        return notesDao.getAllNotes()
    }

    suspend fun deleteNote(note: Note) {
        notesDao.delete(note)
    }
}
