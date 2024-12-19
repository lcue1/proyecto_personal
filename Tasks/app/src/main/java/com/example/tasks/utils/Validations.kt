package com.example.tasks.utils

import android.content.Context
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.tasks.R

fun validateEditText(context: Context, edits:List<EditText>):Boolean {
    val wrongEdit = edits.filter { it.text.toString().isEmpty() }
    wrongEdit.forEach {
        it.setHintTextColor(ContextCompat.getColor(context, R.color.red))
        it.error = "Cammpo requerido"
    }
    return wrongEdit.isEmpty()
}