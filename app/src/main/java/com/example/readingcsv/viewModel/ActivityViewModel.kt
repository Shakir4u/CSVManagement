package com.example.readingcsv.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readingcsv.CSVReaderWriter
import com.example.readingcsv.R
import com.example.readingcsv.models.DataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.InputStream

class ActivityViewModel(application: Application):AndroidViewModel(application){

    var listData=MutableLiveData<List<DataModel>>()
    init {
        read(application.resources.openRawResource(R.raw.small_sample_data))
    }
    fun read(inputStream: InputStream){
        GlobalScope.launch(Dispatchers.Main) {
            listData.value=CSVReaderWriter.read(inputStream)
        }
    }

    fun createCSV(context: Context){
        GlobalScope.launch(Dispatchers.IO) {
            listData.value?.let {
                CSVReaderWriter.createCSV(listData.value!!,context)
            }
        }

    }

    // 1 case not written when searching for empty list and then search for free stuff
    fun filter(isChecked:Boolean){
        val filterData=listData.value?.filter{it.is_free==isChecked}
        listData.value=filterData

    }
    fun filter(isChecked:Boolean,client_id:Int){
        val filterData=listData.value?.filter{it.client_id ==client_id && it.is_free==isChecked }
        listData.value=filterData
    }

}