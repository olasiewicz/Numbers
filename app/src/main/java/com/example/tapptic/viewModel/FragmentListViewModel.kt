package com.example.tapptic.viewModel

import android.view.View

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation

import com.example.tapptic.model.Api
import com.example.tapptic.model.NumberLight
import com.example.tapptic.model.NumberLightDetails
import com.example.tapptic.view.FragmentListDirections

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentListViewModel : ViewModel() {

    private var numbersList: MutableLiveData<List<NumberLight>>? = null
    private var numberLightDetails: MutableLiveData<NumberLightDetails>? = null

     var text: MutableLiveData<String>? = null


    val details: LiveData<NumberLightDetails>
        get() {
            if (numberLightDetails == null) {
                numberLightDetails = MutableLiveData()
            }

            return this.numberLightDetails!!
        }

    private val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
                .baseUrl("http://dev.tapptic.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    fun getTextValue(): LiveData<String> {
        if (text == null) {
            text = MutableLiveData()
        }
        return text!!
    }

    fun getNumbersList(): LiveData<List<NumberLight>> {

        if (numbersList == null) {
            numbersList = MutableLiveData()
        }
        loadNumbers()


        return numbersList!!
    }

    fun loadNumbers() {

        val api = retrofitInstance.create(Api::class.java)
        val call = api.numbers

        call.enqueue(object : Callback<List<NumberLight>> {
            override fun onResponse(call: Call<List<NumberLight>>, response: Response<List<NumberLight>>) {
                numbersList!!.value = response.body()
            }

            override fun onFailure(call: Call<List<NumberLight>>, t: Throwable) {
                text!!.value = "No internet"
            }
        })
    }

    private fun loadNumberDetails(name: String) {

        val api = retrofitInstance.create(Api::class.java)
        val call = api.getNumberDetails(name)

        call.enqueue(object : Callback<NumberLightDetails> {
            override fun onResponse(call: Call<NumberLightDetails>, response: Response<NumberLightDetails>) {
                numberLightDetails!!.value = response.body()

            }

            override fun onFailure(call: Call<NumberLightDetails>, t: Throwable) {
                text!!.value = "no internet"
            }
        })
    }

    fun actionOnClickItem(view: View, name: String?, isTabletLandMode: Boolean) {
        loadNumberDetails(name ?: "1")
        if (!isTabletLandMode) {
            Navigation.findNavController(view).navigate(FragmentListDirections.actionFragmentListToFragmentDetails(name!!))
        }
    }

}
