package com.example.apicallbasic


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallbasic.Const.Companion.API_KEY
import com.example.example.ExampleJson2KtKotlin

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val retrofitInstance: APIService) : ViewModel() {

    val responseContainer = MutableLiveData<ExampleJson2KtKotlin>()
    val errorMessage = MutableLiveData<String>()
    val isShowProgress = MutableLiveData<Boolean>()
    val expressionToSearch = MutableLiveData("")

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled : ${throwable.localizedMessage}")
    }

    fun getMoviesFromAPI() {
        isShowProgress.value = true
        job = viewModelScope.launch {
            val response = retrofitInstance.imdbFunction()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    responseContainer.postValue(response.body())
                    isShowProgress.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }


    }

    private fun onError(message: String) {
        errorMessage.value = message
        isShowProgress.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}