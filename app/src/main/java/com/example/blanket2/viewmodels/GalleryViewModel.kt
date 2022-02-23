package com.example.blanket2.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.blanket2.Model.GalleryRepository
import com.example.blanket2.Model.Post
import kotlinx.coroutines.launch

/**
 * https://developer.android.com/jetpack/guide
 * https://developer.android.com/topic/libraries/architecture/viewmodel
 */
const val TAG = "ViewModel"
class GalleryViewModel(application: Application): AndroidViewModel(application) {
    private var galleryRepository = GalleryRepository(application)
    private val postList: MutableLiveData<List<Post>> = MutableLiveData<List<Post>>()

    init{
        loadPosts()
    }

    fun getPosts(): MutableLiveData<List<Post>> {
        return postList
    }

    private fun loadPosts(){
        viewModelScope.launch{
            postList.value = galleryRepository.getPosts()
            Log.d(TAG, "${postList.value}")
        }
    }
}