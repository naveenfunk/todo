package com.todo.utils

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("app:showTime")
fun showTime(textView : AppCompatTextView, timeInMillis : Long){
    textView.text = DATE_FORMAT.format(Date(timeInMillis))
}