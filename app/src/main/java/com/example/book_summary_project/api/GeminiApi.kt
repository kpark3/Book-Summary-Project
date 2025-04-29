package com.example.book_summary_project.api

import android.util.Log
import com.example.book_summary_project.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


/**
 * GeminiApiService is a singleton object responsible for making API requests to the Gemini language model.
 * It uses the OkHttpClient library to send HTTP requests and handle the responses.
 * The getSummaryResponse function takes a question string as input and sends a request to the Gemini API.
 * The API response is then parsed to extract the generated summary.
 * The callback function is invoked with the generated summary as a result.
 */
object GeminiApiService {
    private val client = OkHttpClient()
    private val apiKey = BuildConfig.GEMINI_API_KEY
    private val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$apiKey"

    // Function to send a request to the Gemini API and get the summary response
    fun getSummaryResponse(question: String, callback: (String) -> Unit) {
        val requestBody = """
            {
                "contents": [
                    {
                        "parts": [
                            {
                                "text": "$question"
                            }
                        ]
                    }
                ]
            }
        """.trimIndent()

        // Log the raw request body
        val request = Request.Builder()
            .url(url)
            .header("Content-Type", "application/json")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GeminiApiService", "API request failed", e)
            }

            // Handle the API response
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                // Log the raw API response
                Log.d("GeminiApiService", "Raw API response: $body")

                // Parse the response to extract the generated summary and invoke the callback
                if (body != null) {
                    try {
                        val jsonObject = JSONObject(body)
                        val candidatesArray = jsonObject.getJSONArray("candidates")
                        val content = candidatesArray.getJSONObject(0).getJSONObject("content")
                        val partsArray = content.getJSONArray("parts")
                        val textResult = partsArray.getJSONObject(0).getString("text")
                        callback(textResult)
                    } catch (e: Exception) {
                        Log.e("GeminiApiService", "Error parsing response", e)
                    }
                } else {
                    callback("No response body")
                }
            }
        })
    }
}