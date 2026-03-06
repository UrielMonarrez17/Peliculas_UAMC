package com.example.peliculas_uamc

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import com.google.firebase.database.ValueEventListener

class Home : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth

    val database = Firebase.database
    val myRef = database.getReference("peliculas")

    lateinit var peliculas: ArrayList<Pelicula>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val extras = intent.extras
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var lista: ListView = findViewById(R.id.lista)
        lista.setOnItemClickListener { parent, view, position, id ->

            startActivity(Intent(this, Detalle::class.java)
                .putExtra("key", datos[position].key)
                .putExtra("pelicula", datos[position].pelicula?.nombre.toString())
                .putExtra("pelicula", datos[position].pelicula?.genero.toString())
            )
        }

        lista.setOnItemClickListener{parent,view,position,id->
            val item = parent.getItemAtPosition(position)
            Toast.makeText(this, peliculas[position].nombre.toString(), Toast.LENGTH_LONG)
                .show()
        }
        auth = Firebase.auth



        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                peliculas = ArrayList<Pelicula>()
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.value
                Log.d("base-tiempo-real", "Value is: " + value)

                snapshot.children.forEach {
                    peli ->
                    var pelicula = Pelicula(peli.child("nombre").value.toString(),
                        peli.child("genero").value.toString(),
                        peli.child("anio").value.toString(),
                        peli.key.toString())

                    peliculas.add(pelicula)
                }
            llenaListView()

            }



            override fun onCancelled(error: DatabaseError) {
                Log.w("base-tiempo-real", "Failed to read value.", error.toException())
            }

        })
    }

    fun llenaListView(){
        val listView= findViewById<ListView>(R.id.lista)
        val adaptador = PeliAdapter(this, peliculas)

        listView.adapter = adaptador
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            logout()
        }
        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

