package com.example.cat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    //Google

    private lateinit var auth : FirebaseAuth

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        findViewById<FloatingActionButton>(R.id.fabCat).setOnClickListener {
            val intent = Intent(this@HomeActivity, AddActivity::class.java)
            startActivity(intent)
        }

//        val email = intent.getStringExtra("email")
//        val displayName = intent.getStringExtra("name")
//        findViewById<TextView>(R.id.textView).text = email + "\n" + displayName

        findViewById<Button>(R.id.signOutBtn).setOnClickListener{
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }




    }
}