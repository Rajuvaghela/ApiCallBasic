package com.example.apicallbasic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apicallbasic.databinding.RawMoviesItemLayoutBinding
import com.example.example.Data

class MoviesRecyclerAdapter : RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesViewHolder>() {

    inner class MoviesViewHolder(private val rawMoviesItemLayoutBinding: RawMoviesItemLayoutBinding) :
        RecyclerView.ViewHolder(rawMoviesItemLayoutBinding.root) {

        fun bind(results: Data){

            rawMoviesItemLayoutBinding.apply {
                Glide.with(root.context)
                    .load(results.avatar)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageOfMovies)

                titleOfMovie.text = results.firstName
                descriptionOfMovie.text = results.lastName
            }

        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Data>()
    {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var result : List<Data>
        get() = differ.currentList
        set(value){
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(RawMoviesItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(result[position])
    }

    override fun getItemCount() = result.size

}