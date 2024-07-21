package com.example.quanlych.admin.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminHomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Adminhome Fragment"
    }
    val text: LiveData<String> = _text
}