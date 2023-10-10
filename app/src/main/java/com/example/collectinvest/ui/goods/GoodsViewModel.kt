package com.example.collectinvest.ui.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GoodsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Goods Fragment"
    }
    val text: LiveData<String> = _text
}