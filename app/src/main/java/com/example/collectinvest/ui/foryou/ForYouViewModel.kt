package com.example.collectinvest.ui.foryou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForYouViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is For You Fragment"
    }
    val text: LiveData<String> = _text

}