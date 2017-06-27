package com.prototype.shane.shaneprototype.view.kotlinFun

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.prototype.shane.shaneprototype.R
import com.prototype.shane.shaneprototype.model.kotlinFun.KotlinFunMovieModel
import com.squareup.picasso.Picasso

/**
 * Created by shane1 on 6/26/17.
 */
class KotlinFunAdapter(val movies: ArrayList<KotlinFunMovieModel>) : RecyclerView.Adapter<KotlinFunAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        return ViewHolder(layoutInflater.inflate(R.layout.kotlin_fun_movie_cell, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val name = movies.get(position).name
        val url = movies.get(position).url

        holder!!.textViewMovieTitle.setText(name)
        Picasso.with(holder.imageViewMovie.context).load(url).fit().centerInside().into(holder.imageViewMovie)
    }

    fun addMovie(movie: KotlinFunMovieModel){
        movies.add(movie)
    }

    fun addMovie(movieName: String, movieUrl: String){
        val movie = KotlinFunMovieModel(movieName, movieUrl)
        movies.add(movie)
    }


    class ViewHolder(rootView : View) : RecyclerView.ViewHolder(rootView){
        val textViewMovieTitle = rootView.findViewById(R.id.textViewMovieTitle) as TextView
        val imageViewMovie = rootView.findViewById(R.id.imageViewMovie) as ImageView
    }
}