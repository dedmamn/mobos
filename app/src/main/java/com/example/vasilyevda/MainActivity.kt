package com.example.vasilyevda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vasilyevda.ui.theme.VasilyevdaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var notesUseCase: NotesUseCase
    private lateinit var notesViewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = NotesDatabase.getDatabase(this)
        val notesDao = database.notesDao()
        notesUseCase = NotesUseCase(notesDao)
        notesViewModel = NotesViewModel(notesUseCase)

        // Инициализация базы данных в корутине
        CoroutineScope(Dispatchers.IO).launch {
            val initialNotes = listOf(
                Note(title = "Первая заметка", text = "Текст первой заметки"),
                Note(title = "Вторая заметка", text = "Текст второй заметки")
            )
            notesUseCase.initializeNotes(initialNotes)

            withContext(Dispatchers.Main) {
                notesViewModel.fetchNotes() // добавьте этот метод в NotesViewModel
            }
        }

        setContent {
            VasilyevdaTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NotesScreen(viewModel = notesViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VasilyevdaTheme {
        Greeting("Android")
    }
}