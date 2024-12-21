package com.example.tasks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String,
    val time:String
):Parcelable

