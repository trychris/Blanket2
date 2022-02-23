package com.example.blanket2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blanket2.Model.Post
import com.example.blanket2.R
import com.example.blanket2.utilities.Listener
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

/**
 * Create ListAdapter
 */
class GalleryRecyclerViewAdapter(
    var postList: List<Post>,
    var listener: Listener<Int>
): RecyclerView.Adapter<GalleryRecyclerViewAdapter.PostViewHolder>() {
    class PostViewHolder(view: View): RecyclerView.ViewHolder(view){
        var posterName: TextView = view.findViewById(R.id.posterNameTextView)
        var photoPost: ImageView = view.findViewById(R.id.photoPostImageView)
        var description: TextView = view.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_layout, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.posterName.text = postList[position].id
        holder.description.text = postList[position].description
        Picasso.get().load(postList[position].photoUrls.regular).into(holder.photoPost)
        holder.itemView.setOnClickListener{listener.receive(position)}
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun changeData(newData: List<Post>){
        postList = newData
        notifyDataSetChanged()
    }

}