package com.example.tasks.utils

import android.app.Activity
import android.content.Intent

 fun Activity.returnToUserActivity(RESULT_CODE:Int, value:String) {//go before activity
    val resultIntent = Intent()
    resultIntent.putExtra("userAction", value)
    setResult(RESULT_CODE, resultIntent)
    finish()
}
