package com.example.blanket2.Model

/**
 * A post consists of an image and a comment
 * Delgating to another property: https://kotlinlang.org/docs/delegated-properties.html#delegating-to-another-property
 * We use delegation because Post.description = Post.photo.description, so we reduce boilerplate code
 */
data class Post(val photo: Photo, val comment: Comment){
    val id: String by photo::id
    val createdAt: String by photo::createdAt
    val updatedAt: String by photo::updatedAt
    val likes: Int by photo::likes
    val description: String by photo::description
    val photoUrls: PhotoUrls by photo::photoUrls
}

