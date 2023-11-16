package com.example.ai_flashcards

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.AsyncTask
import android.view.View
import android.widget.TextView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.CountDownLatch

interface OnApiDataReceivedListener {
    fun onApiDataReceived(data: String)
}


class BuildByTerm : AppCompatActivity() {
    private val openaiApiKey = "YourKeyHere"
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var inputTerm: EditText
    private lateinit var loading: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_by_term)

        loading = findViewById(R.id.loading_term)
        loading.visibility = View.INVISIBLE

        //sharedPreferences
        sharedPrefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        //buttons
        val topic_screen: Button
        val study_screen: Button

        inputTerm = findViewById<EditText>(R.id.input_term)

        //assign buttons
        topic_screen = findViewById(R.id.topic_button)
        study_screen = findViewById(R.id.buildByTerm)

        // Add_button add clicklistener
        topic_screen.setOnClickListener {
            val intent = Intent(this, BuildByTopicActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }

        study_screen.setOnClickListener {
            loading.visibility = View.VISIBLE

            val intent = Intent(this, StudyActivity::class.java)
            val apiTask = ApiTask(object : OnApiDataReceivedListener {
                override fun onApiDataReceived(data: String) {
                    // This callback is triggered when JSON data is received
                    Log.d("ByTerm", "Received API data: $data")

                    // Process the data or update shared preferences as needed
                    val spsize = sharedPrefs.all.size
                    val cardIndex = if (sharedPrefs.contains("input_topic")) spsize else spsize + 1

                    val content = parseJsonResponse(data)

                    with(sharedPrefs.edit()) {
                        putString("card$cardIndex", inputTerm.text.toString() + "|" + content)
                        apply()
                    }

                    val spterm = sharedPrefs.getString("card$cardIndex", "not found")
                    val term = if (spterm == null) "not found" else spterm
                    Log.d("ByTerm", "SHAREDPREFS: ${sharedPrefs.all}")


                    Log.d("ByTerm","Building...")

                    // start the activity connect to the specified class
                    startActivity(intent)
                }
            })

            apiTask.execute()


        }
    }

    private fun parseJsonResponse(data: String): String {
        try {
            val jsonObject = JSONObject(data)
            val choicesArray = jsonObject.getJSONArray("choices")

            if (choicesArray.length() > 0) {
                val firstChoice = choicesArray.getJSONObject(0)
                val message = firstChoice.getJSONObject("message")
                return message.getString("content")
            }
        } catch (e: Exception) {
            Log.e("ByTerm", "Error parsing JSON: ${e.message}")
        }

        return "Error parsing JSON"
    }

    private inner class ApiTask(private val onDataReceivedListener: OnApiDataReceivedListener) : AsyncTask<Void, Void, String>() {
        private lateinit var countDownLatch: CountDownLatch

        override fun doInBackground(vararg params: Void?): String {
            return makeApiCall()
        }

        override fun onPostExecute(result: String) {
            // Handle the API response here
            Log.d("API_RESPONSE", result)
            onDataReceivedListener.onApiDataReceived(result)
            //responseTextView.text = result

        }

        private fun makeApiCall(): String {
            val apiUrl = "https://api.openai.com/v1/chat/completions"
            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection

            try {
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Authorization", "Bearer $openaiApiKey")
                connection.doOutput = true

                val data = """
                    {
                        "model": "gpt-3.5-turbo",
                        "messages": [
                            {
                                "role": "user",
                                "content": "Generate a short definition for ${inputTerm.text.toString()}. Limit 1 sentence."
                            }
                        ]
                    }
                """.trimIndent()

                Log.d("ByTerm", data)

                val outputStream = DataOutputStream(connection.outputStream)
                outputStream.writeBytes(data)
                outputStream.flush()
                outputStream.close()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()
                    return response.toString()
                } else {
                    return "Error: $responseCode"
                }
            } finally {
                Log.d("ByTerm", "API")
                connection.disconnect()
            }
        }
    }
}
