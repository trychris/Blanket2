package com.example.blanket2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val galleryFragment = GalleryFragment()
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmentContainer, galleryFragment)
            addToBackStack(null)
            commit()
        }

    }
}