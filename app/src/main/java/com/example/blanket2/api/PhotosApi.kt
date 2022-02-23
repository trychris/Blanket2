package com.example.blanket2.api

import android.content.Context
import com.android.volley.toolbox.JsonArrayRequest
import com.example.blanket2.Model.Comment
import com.example.blanket2.Model.Photo
import com.example.blanket2.Model.PhotoUrls
import com.example.blanket2.utilities.Listener
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.suspendCoroutine

const val PHOTOS_URL = "https://api.unsplash.com/photos"
const val API_KEY = Insert Unsplash API key here

class PhotosApi(private val context: Context) {
    fun loadPhotos(callBack: (List<Photo>) -> Unit){
        var request: JsonArrayRequest = object: JsonArrayRequest(PHOTOS_URL,
            {
                try{
                    var result: ArrayList<Photo> = ArrayList()
                    for(i in 0 until it.length()){
                        var jsonPhoto: JSONObject = it.getJSONObject(i)
                        result.add(
                            jsonPhoto.run{
                                Photo(
                                    getString("id"),
                                    getString("created_at"),
                                    getString("updated_at"),
                                    getInt("width"),
                                    getInt("height"),
                                    getString("color"),
                                    getInt("likes"),
                                    getString("description"),
                                    getJSONObject("urls").run{
                                        PhotoUrls(
                                            getString("raw"),
                                            getString("full"),
                                            getString("regular"),
                                            getString("small"),
                                            getString("thumb")
                                        )
                                    }
                                )
                            }
                        )
                    }
                    callBack(result)
                } catch(e: JSONException){
                    e.printStackTrace()
                }
            }, {
                it.printStackTrace()
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Client-ID $API_KEY"
                return headers
            }
        }
        VolleyQueueSingleton.getInstance(context).addToRequestQueue(request)
    }

    suspend fun loadPhotosAsync(): List<Photo>{
        return suspendCoroutine { continuation ->
            var request: JsonArrayRequest = object: JsonArrayRequest(PHOTOS_URL,
                {
                    try{
                        var result: ArrayList<Photo> = ArrayList()
                        for(i in 0 until it.length()){
                            var jsonPhoto: JSONObject = it.getJSONObject(i)
                            result.add(
                                jsonPhoto.run{
                                    Photo(
                                        getString("id"),
                                        getString("created_at"),
                                        getString("updated_at"),
                                        getInt("width"),
                                        getInt("height"),
                                        getString("color"),
                                        getInt("likes"),
                                        getString("description"),
                                        getJSONObject("urls").run{
                                            PhotoUrls(
                                                getString("raw"),
                                                getString("full"),
                                                getString("regular"),
                                                getString("small"),
                                                getString("thumb")
                                            )
                                        }
                                    )
                                }
                            )
                        }
                        continuation.resumeWith(Result.success(result))
                    } catch(e: JSONException){
                        continuation.resumeWith(Result.failure(e))
                    }
                }, {
                    continuation.resumeWith(Result.failure(it))
                }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Client-ID $API_KEY"
                    return headers
                }
            }
            VolleyQueueSingleton.getInstance(context).addToRequestQueue(request)
        }

    }
}