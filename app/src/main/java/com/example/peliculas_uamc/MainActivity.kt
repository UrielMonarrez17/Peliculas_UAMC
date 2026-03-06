package com.example.peliculas_uamc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        //onBackPressedDispatcher.addCallback()

        }

    override fun onStart(){
        super.onStart()

        var usuarioActual = auth.currentUser
        verificaUsuario(usuarioActual)
    }

    fun verificaUsuario(usuario: FirebaseUser?){

        if(usuario!=null){
            Toast.makeText(this,"Usuario ya verificado", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Home::class.java))
        }
    }

    fun loginsito(view: View){
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        if( email.text.toString()!=null && password.text.toString()!=null) {
            Log.d("password", "Pass: " + password)
            Log.d("email", "Email: " + email)
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login exitoso", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, Home::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
        else{
            Toast.makeText(this, "Introduce todos los campos >:c", Toast.LENGTH_LONG)
                .show()
        }

    }


}

