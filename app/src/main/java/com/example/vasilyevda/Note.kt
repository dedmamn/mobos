package com.example.vasilyevda

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var text: String = ""
)