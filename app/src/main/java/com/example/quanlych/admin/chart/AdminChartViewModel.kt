package com.example.quanlych.admin.chart


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminChartViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "THỐNG KÊ"
    }
    val text: LiveData<String> = _text
}