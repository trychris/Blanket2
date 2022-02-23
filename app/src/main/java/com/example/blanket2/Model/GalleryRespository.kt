package com.example.blanket2.Model

import android.content.Context
import android.util.Log
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.example.blanket2.api.CommentApi
import com.example.blanket2.api.PhotosApi
import kotlinx.coroutines.*
import org.json.JSONException

/**
 * Decomposition of parallel tasks
 * There are 3 possible ways: withContext, coroutineScope or Launch
 * https://stackoverflow.com/questions/47055804/kotlin-coroutines-with-returning-value
 * https://stackoverflow.com/questions/55103285/launching-coroutines-from-a-suspended-function
 * https://discuss.kotlinlang.org/t/get-current-coroutinecontext-inside-a-regular-function/11568
 * https://discuss.kotlinlang.org/t/easy-way-to-access-coroutine-context-from-nested-suspending-functions/3647
 * https://stackoverflow.com/questions/61606630/should-you-pass-coroutinescope-as-function-argument
 * https://stackoverflow.com/questions/56858624/kotlin-coroutines-what-is-the-difference-between-coroutinescope-and-withcontext
 * Combine Asynchronous coroutines and synchronous LiveData
 * https://developer.android.com/topic/libraries/architecture/coroutines
 */
const val TAG = "Repository"
class GalleryRepository(private val context: Context) {
    var photoApi = PhotosApi(context)
    var commentApi = CommentApi(context)
    suspend fun getPosts(): List<Post> = withContext(Dispatchers.IO){
            val d1 = async{
                try{
                    photoApi.loadPhotosAsync()
                } catch (e: Exception){
                    if(e is VolleyError || e is JSONException){
                        ArrayList()
                    } else {
                        throw e
                    }
                }
            }
            val d2 = async{
                try{
                    commentApi.loadCommentsAsync()
                } catch (e: Exception){
                    if(e is VolleyError || e is JSONException){
                        ArrayList()
                    } else {
                        throw e
                    }
                }
            }
            val p = d1.await()
            val c = d2.await()
            val result = ArrayList<Post>()
            for(i in 0 until Math.min(p.size, c.size)){
                result.add(Post(p[i], c[i]))
            }
            Log.d(TAG, "$result")
            return@withContext result // or just result
        }

    /**
     * Version 2?
     */
    suspend fun getPosts2(): List<Post> = coroutineScope{
        val d1 = async{photoApi.loadPhotosAsync()}
        val d2 = async{commentApi.loadCommentsAsync()}
        val p = d1.await()
        val c = d2.await()
        val result = ArrayList<Post>()
        for(i in 0 .. Math.min(p.size, c.size)){
            result.add(Post(p[i], c[i]))
        }
        return@coroutineScope result // or just result
    }

    /**
     * Version 3?
     */
    suspend fun getPosts3(): List<Post> = coroutineScope{
        val d1 = async(Dispatchers.IO){photoApi.loadPhotosAsync()}
        val d2 = async(Dispatchers.IO){commentApi.loadCommentsAsync()}
        val p = d1.await()
        val c = d2.await()
        val result = ArrayList<Post>()
        for(i in 0 .. Math.min(p.size, c.size)){
            result.add(Post(p[i], c[i]))
        }
        return@coroutineScope result // or just result
    }


}