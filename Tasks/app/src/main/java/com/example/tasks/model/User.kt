package com.example.tasks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id: String?,
    val name:String?,
    val image:String?
): Parcelable
