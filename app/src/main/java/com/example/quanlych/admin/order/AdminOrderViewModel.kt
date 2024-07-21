package com.example.quanlych.admin.order



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminOrderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ĐƠN HÀNG"
    }
    val text: LiveData<String> = _text
}