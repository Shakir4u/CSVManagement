package com.example.readingcsv.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readingcsv.CSVReaderWriter
import com.example.readingcsv.DataAdapter
import com.example.readingcsv.R
import com.example.readingcsv.databinding.ActivityMainBinding
import com.example.readingcsv.models.DataModel
import com.example.readingcsv.viewModel.ActivityViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DataAdapter
    private lateinit var viewModel: ActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(this.application).create(ActivityViewModel::class.java)
        adapter=DataAdapter(this, mutableListOf())

        viewModel.listData.observe(this, Observer {
            adapter.setData(it)
            Log.e(MainActivity::class.java.simpleName,it.toString())
        })

        binding.rvData.adapter=adapter
        binding.rvData.layoutManager=LinearLayoutManager(this)

        binding.btFilter.setOnClickListener(this)
        binding.btExport.setOnClickListener(this)


    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btFilter->{
                val input=binding.etClientId.text.toString()
                if(TextUtils.isEmpty(input)&& !binding.cbFreeTest.isChecked){
                    viewModel.read(resources.openRawResource(R.raw.small_sample_data))
                }else if(TextUtils.isEmpty(input)){
                    viewModel.filter(binding.cbFreeTest.isChecked)
                }else{
                    val num=input.toInt()
                    viewModel.filter(binding.cbFreeTest.isChecked,num)
                }
            }

            R.id.btExport->{
                viewModel.createCSV(this@MainActivity)
                Toast.makeText(this@MainActivity,"Click on export",Toast.LENGTH_SHORT).show()
            }
        }
    }
}