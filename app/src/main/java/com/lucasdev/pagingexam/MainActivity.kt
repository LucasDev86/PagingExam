package com.lucasdev.pagingexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.*
import com.lucasdev.pagingexam.data.Result
import com.lucasdev.pagingexam.databinding.ActivityMainBinding
import com.lucasdev.pagingexam.network.PokeAPI
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var pokeAPI: PokeAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val adapter = MainRecyclerViewAdapter()
        binding.recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pokeAPI = retrofit.create(PokeAPI::class.java)

        createLiveData().observe(this, Observer { results ->
            adapter.submitList(results)
        })
    }

    private inner class DataSource : PageKeyedDataSource<String, Result>() {

        override fun loadInitial(
            params: LoadInitialParams<String>,
            callback: LoadInitialCallback<String, Result>
        ) {
            try {
                val body = pokeAPI.listPokemons().execute().body()
                body?.let { body ->
                    callback.onResult(body.results, body.previous, body.next)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Result>) {
            val quertPart = params.key.split("?")[1]
            val queries = quertPart.split("&")
            val map = mutableMapOf<String, String>()
            for (query in queries) {
                val parts = query.split("=")
                map[parts[0]] = parts[1]
            }

            try {
                val body = pokeAPI.listPokemons(map["offset"]!!, map["limit"]!!).execute().body()
                body?.let {
                    callback.onResult(body.results, body.next)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun loadBefore(
            params: LoadParams<String>,
            callback: LoadCallback<String, Result>
        ) {
            val quertPart = params.key.split("?")[1]
            val queries = quertPart.split("&")
            val map = mutableMapOf<String, String>()
            for (query in queries) {
                val parts = query.split("=")
                map[parts[0]] = parts[1]
            }

            try {
                val body = pokeAPI.listPokemons(map["offset"]!!, map["limit"]!!).execute().body()
                body?.let {
                    callback.onResult(body.results, body.previous)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun createLiveData(): LiveData<PagedList<Result>> {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .setPrefetchDistance(10)
            .build()

        return LivePagedListBuilder(
            object : androidx.paging.DataSource.Factory<String, Result>() {
                override fun create(): androidx.paging.DataSource<String, Result> {
                    return DataSource()
                }
            }, config
        ).build()
    }
}