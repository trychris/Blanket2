package com.example.blanket2.Model

/**
 * TODO
 * Convert to Date object
 */
data class Photo(
    val id: String,
    val createdAt: String,
    val updatedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int,
    val description: String,
    val photoUrls: PhotoUrls

)

/**
 * TODO
 * Convert to URI object
 */
data class PhotoUrls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)
