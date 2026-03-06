package com.example.peliculas_uamc

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PeliAdapter(private val contex: Activity,private val arraylist: ArrayList<Pelicula>):
    ArrayAdapter<Pelicula>(contex,R.layout.item,arraylist) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(contex)
        val view: View = inflater.inflate(R.layout.item,null)
        view.findViewById<TextView>(R.id.nombre).text=arraylist[position].nombre.toString()
        view.findViewById<TextView>(R.id.genero).text=arraylist[position].genero.toString()
        view.findViewById<TextView>(R.id.anio).text=arraylist[position].anio.toString()
        return view
    }
}