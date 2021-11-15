package com.example.appto.utils

import android.util.Log
import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter : JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            if (reader.peek() != JsonReader.Token.NULL) {
                val dateAsString = reader.nextString()
                synchronized(dateFormat) {
                    dateFormat.parse(dateAsString)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("ERROR DATE", it) }
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            synchronized(dateFormat) {
                writer.value(value.toString())
            }
        }
    }

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd")
    }
}