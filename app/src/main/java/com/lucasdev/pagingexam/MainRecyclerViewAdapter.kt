package com.lucasdev.pagingexam

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucasdev.pagingexam.data.Result
import com.lucasdev.pagingexam.databinding.ItemRecyclerviewBinding

class MainRecyclerViewAdapter : PagedListAdapter<Result , MainRecyclerViewAdapter.MainRecyclerViewHolder>(object : DiffUtil.ItemCallback<Result>(){
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.name == newItem.name && oldItem.url == newItem.url
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {

        val binding = DataBindingUtil.inflate<ItemRecyclerviewBinding>(LayoutInflater.from(parent.context) , R.layout.item_recyclerview , parent , false)

        return MainRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MainRecyclerViewHolder(binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {

        private val viewModel: ViewModel = ViewModel()

        init {
            binding.viewModel = viewModel
        }

        fun bind(item: Result?){
            item?.let {
                viewModel.name.set(it.name)
                viewModel.url.set(it.url)
            }
        }
    }

}