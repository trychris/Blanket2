package com.example.blanket2.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.blanket2.Model.Comment
import com.example.blanket2.Model.Photo
import com.example.blanket2.utilities.Listener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.suspendCoroutine

const val COMMENTS_URL = "https://jsonplaceholder.typicode.com/comments"

/**
 * There are 2 ways to resolve an asynchronous request
 * First way is to use callbacks
 * Second way is to use Kotlin's coroutine API
 * TODO
 * Implement custom suspendCoroutine
 */
class CommentApi(private val context: Context) {

    /**
     * Once response is received, call the onClickListener (Callback way)
     */
    fun loadComments(callBack: (List<Comment>) -> Unit){
        var request: JsonArrayRequest = JsonArrayRequest(COMMENTS_URL,
            {
                try{
                    var result: ArrayList<Comment> = ArrayList()
                    for(i in 0 until it.length()){
                        var jsonComment: JSONObject = it.getJSONObject(i)
                        result.add(
                            Comment(
                                jsonComment.getInt("postId"),
                                jsonComment.getInt("id"),
                                jsonComment.getString("name"),
                                jsonComment.getString("email"),
                                jsonComment.getString("body")
                            )
                        )
                    }
                    callBack(result)
                } catch(e: JSONException){
                    e.printStackTrace()
                }
            }, {
                it.printStackTrace()
            })
        VolleyQueueSingleton.getInstance(context).addToRequestQueue(request)
    }

    /**
     * Convert a callback into a coroutine
     * https://vineeth.ink/coroutine-basics-converting-callbacks-to-coroutines-207c9d59eb02
     */
    suspend fun loadCommentsAsync(): List<Comment>{
        return suspendCoroutine { continuation ->
            var request: JsonArrayRequest = JsonArrayRequest(COMMENTS_URL,
                {
                    try{
                        var result: ArrayList<Comment> = ArrayList()
                        for(i in 0 until it.length()){
                            var jsonComment: JSONObject = it.getJSONObject(i)
                            result.add(
                                Comment(
                                    jsonComment.getInt("postId"),
                                    jsonComment.getInt("id"),
                                    jsonComment.getString("name"),
                                    jsonComment.getString("email"),
                                    jsonComment.getString("body")
                                )
                            )
                        }
                        continuation.resumeWith(Result.success(result))
                    } catch(e: JSONException){
                        continuation.resumeWith(Result.failure(e))
                    }
                }, {
                    continuation.resumeWith(Result.failure(it))
                })
            VolleyQueueSingleton.getInstance(context).addToRequestQueue(request)
        }

    }



}