package com.example.androidfragment

import java.io.Serializable

class Task(
    val id: Int,
    val text: String,
    var flag: Boolean = false,
    val dataCreate: String,
):Serializable {
}