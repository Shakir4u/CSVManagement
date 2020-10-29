package com.example.readingcsv

import android.content.Context
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.example.readingcsv.models.DataModel
import java.io.*


class CSVReaderWriter{
    // sample line
    /*
    * [99,
    *  "{""test_name"": ""Mock Test 99"",  ""q_count"": 100,  ""question_data"":
    * [{""q_string"": ""some question"",  ""options"": [""option1"",  ""option2"",  ""option3""]}]
    * }",
    *  false,
    * 74]
    * */


    //parsed line
    /*
 E/DATA:: Data at 0 1
 E/DATA: Data at 1 "{""test_name"": ""Mock Test 1""
 E/DATA: Data at 2  ""q_count"": 100
 E/DATA: Data at 3  ""question_data"": [{""q_string"": ""some question""
 E/DATA: Data at 4  ""options"": [""option1""
 E/DATA: Data at 5  ""option2""
 E/DATA: Data at 6  ""option3""]}]}"
 E/DATA: Data at 7 false
 E/DATA: Data at 8 5
    * */



    companion object{
        val CSV_FILE_NAME="test.csv"

        suspend  fun read(inputSteam: InputStream):List<DataModel>{
            val dataList= mutableListOf<DataModel>()
            val reader=BufferedReader(InputStreamReader(inputSteam))
            while (reader.readLine() != null){
                val data=reader.readLine()?.split(",")
                data?.let {
                    val model=DataModel(
                        if (data.get(0).isDigitsOnly()) data.get(0).toInt() else -1,
                        data.get(1),
                        data.get(7).toBoolean(),
                        if (data.get(8).isDigitsOnly()) data.get(8).toInt() else -1,
                    )
                    dataList.add(model)
                }
            }
            return dataList
        }



        suspend  fun createCSV(dataList: List<DataModel>, context: Context){
            val dirFile:File?=context.getExternalFilesDir("csv")
            dirFile?.mkdirs()
            val file=File(dirFile, CSV_FILE_NAME)
            try {
                //for Creating column title
                val outputStream = FileOutputStream(file,false)
                outputStream.write(("test id" + ",").toByteArray())
                outputStream.write(("test_data" + ",").toByteArray())
                outputStream.write(("is_free" + ",").toByteArray())
                outputStream.write(("client_id" + "," + "\n").toByteArray())

                // writting data here from list
                for (model in dataList){
                    outputStream.write((model.test_id.toString() + ",").toByteArray())
                    outputStream.write((model.test_data + ",").toByteArray())
                    outputStream.write((model.is_free.toString() + ",").toByteArray())
                    outputStream.write((model.client_id.toString() + "," + "\n").toByteArray())
                }
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {

                Log.e("FILE","File Written")
            }
        }
    }
}