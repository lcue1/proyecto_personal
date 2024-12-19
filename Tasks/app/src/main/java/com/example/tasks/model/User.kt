package com.example.tasks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id:Int,
    val name:String,
    val password:String,
    val image:String
): Parcelable
