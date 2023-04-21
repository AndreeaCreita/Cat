package com.example.cat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_add_place)
        setSupportActionBar(toolbar) // Use the toolbar to set the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // This is to use the home back button.
        // Setting the click event to the back button
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}