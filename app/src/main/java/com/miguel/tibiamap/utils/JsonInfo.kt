package com.miguel.tibiamap.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.miguel.tibiamap.domain.Coordinates
import org.apache.commons.io.IOUtils
import java.io.IOException

class JsonInfo {

    fun parseWithGson(context: Context) {
        val jsonString = loadJSONFromAsset(context, "data.json")
        val gson = Gson()
        val user = gson.fromJson(jsonString, Coordinates::class.java)
        //Log.d("JSON", "Name: ${user.name}, Age: ${user.age}")
    }

    fun loadJSONFromAsset(context: Context, fileName: String): String? {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun readJSON(context: Context, resId: Int): String? {
        val inputStream = context.resources.openRawResource(resId)
        val jsonString = IOUtils.toString(inputStream, "UTF-8")
        return jsonString
    }



}