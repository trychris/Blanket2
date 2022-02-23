package com.example.blanket2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blanket2.Model.Post
import com.example.blanket2.adapters.GalleryRecyclerViewAdapter
import com.example.blanket2.viewmodels.GalleryViewModel

/**
 * TODO
 * Use view binding
 */
const val TAG = "Fragment"
class GalleryFragment : Fragment() {
    private lateinit var postList: List<Post>
    private lateinit var adapter: GalleryRecyclerViewAdapter
    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        postList = ArrayList()
        adapter = GalleryRecyclerViewAdapter(postList){}
        val recyclerView = view.findViewById<RecyclerView>(R.id.galleryFragmentRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        galleryViewModel.getPosts().observe(this){
            adapter.changeData(it)
            Log.d(TAG, "$it")
        }
        return view
    }


}