package com.example.cat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    //Google

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    //

    //register
    private lateinit var createnewAccount: TextView
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize auth method + googlesignInCLient
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.googleBtn).setOnClickListener{
            signInGoogle()
        }
        //

        //Register

        createnewAccount = findViewById(R.id.createNewAccount)

        createnewAccount.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }
        //Login nrm
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            loginUser()

        }

        //
    }

//google
    private fun signInGoogle(){

        var signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    //

    private var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
                if (result.resultCode == Activity.RESULT_OK){
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleResults(task)
                }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>){
        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if(account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful)
            {
                val intent : Intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("email",account.email)
                intent.putExtra("name",account.displayName)

                startActivity(intent)
            }else{
                Toast.makeText(this, it.exception.toString(),Toast.LENGTH_SHORT).show()

            }
        }
    }
//  ---google

//login
private fun loginUser() {
    val email = findViewById<EditText>(R.id.inputEmail).text.toString()
    val password = findViewById<EditText>(R.id.inputPassword).text.toString()
    if (email.isNotEmpty() && password.isNotEmpty()) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main) {
                    Log.d("TAG", "This is a debug login message")
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

    override fun onStart() {

        super.onStart()

    }
//
}
















