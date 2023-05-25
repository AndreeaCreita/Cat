package com.example.cat

import CatAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity(), CatAdapter.OnItemClickListener {
    // Google
    private lateinit var auth: FirebaseAuth

    private lateinit var recyclerView: RecyclerView
    private lateinit var catAdapter: CatAdapter
    private lateinit var catList: ArrayList<CatData>

    // ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        // ...

        recyclerView = findViewById(R.id.postList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        catList = ArrayList()
        catAdapter = CatAdapter(catList, this)
        recyclerView.adapter = catAdapter

        // Add sample data to catList (replace with Firestore retrieval logic)
        catList.add(CatData("https://example.com/image1.jpg", "Cat 1", "Description 1", "2023-05-01"))
        catList.add(CatData("https://example.com/image2.jpg", "Cat 2", "Description 2", "2023-05-02"))
        catList.add(CatData("https://example.com/image3.jpg", "Cat 3", "Description 3", "2023-05-03"))

        // ...

        findViewById<FloatingActionButton>(R.id.fabCat).setOnClickListener {
            val intent = Intent(this@HomeActivity, AddActivity::class.java)
            startActivity(intent)
        }

        // ...

        findViewById<Button>(R.id.signOutBtn).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onItemClick(position: Int) {
        // Handle item click event (share functionality)
        val currentItem = catList[position]

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, currentItem.title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "${currentItem.title}\n${currentItem.description}")
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}


//class HomeActivity : AppCompatActivity() {
//    private lateinit var auth: FirebaseAuth
//    private lateinit var db: FirebaseFirestore
//    private lateinit var catList: MutableList<CatData>
//    private lateinit var catAdapter: CatAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        auth = FirebaseAuth.getInstance()
//        db = FirebaseFirestore.getInstance()
//        catList = mutableListOf()
//
//        val recyclerView: RecyclerView = findViewById(R.id.postList)
//        catAdapter = CatAdapter(catList)
//        recyclerView.adapter = catAdapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        findViewById<FloatingActionButton>(R.id.fabCat).setOnClickListener {
//            val intent = Intent(this@HomeActivity, AddActivity::class.java)
//            startActivity(intent)
//        }
//
//        findViewById<Button>(R.id.signOutBtn).setOnClickListener {
//            auth.signOut()
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        retrieveCatData()
//    }
//
//    private fun retrieveCatData() {
//        db.collection("add_cat")
//            .get()
//            .addOnSuccessListener { querySnapshot: QuerySnapshot ->
//                catList.clear()
//                for (document: DocumentSnapshot in querySnapshot.documents) {
//                    val imageUri = document.getString("imageUri")
//                    val title = document.getString("title")
//                    val description = document.getString("description")
//                    val date = document.getString("date")
//                    if (imageUri != null && title != null && description != null && date != null) {
//                        val catData = CatData(imageUri, title, description, date)
//                        catList.add(catData)
//                    }
//                }
//                catAdapter.notifyDataSetChanged()
//            }
//            .addOnFailureListener { exception ->
//                // Handle any errors
//                // ...
//            }
//    }
//
//
//}
