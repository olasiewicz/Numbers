package com.example.tapptic.viewModel

import android.view.View

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.tapptic.model.Api
import com.example.tapptic.model.NumberLightDetails


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentDetailsViewModel : ViewModel() {

    private var numberDetails: MutableLiveData<NumberLightDetails>? = null
    private var text: MutableLiveData<String>? = null

    open fun getText(): LiveData<String> {
        if (text == null) {
            text = MutableLiveData()
        }
        return text!!
    }

    fun getNumberDetails(name: String?): LiveData<NumberLightDetails> {

        if (numberDetails == null) {
            numberDetails = MutableLiveData()
            loadNumberDetails(name ?: "1")
        }

        return numberDetails!!
    }

    fun loadNumberDetails(name: String) {

        val retrofit = Retrofit.Builder()
                .baseUrl("http://dev.tapptic.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(Api::class.java)
        val call = api.getNumberDetails(name)

        call.enqueue(object : Callback<NumberLightDetails> {
            override fun onResponse(call: Call<NumberLightDetails>, response: Response<NumberLightDetails>) {

                numberDetails!!.value = response.body()
            }

            override fun onFailure(call: Call<NumberLightDetails>, t: Throwable) {
                text!!.value = "No internet"
            }
        })
    }
}
