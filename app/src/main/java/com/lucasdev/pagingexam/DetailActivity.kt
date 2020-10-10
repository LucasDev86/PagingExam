package com.lucasdev.pagingexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import coil.api.load
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lucasdev.pagingexam.data.PokemonResponse
import com.lucasdev.pagingexam.databinding.ActivityDetailBinding
import com.lucasdev.pagingexam.network.PokeAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this , R.layout.activity_detail)
        val viewModel = DetailViewModel()
        binding.viewModel = viewModel

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val pokeAPI = retrofit.create(PokeAPI::class.java)

        val intent = intent
        val pid  = intent.getIntExtra("pid", 1)
        pokeAPI.getPokemon(pid).enqueue(object : Callback<PokemonResponse?>{
            override fun onFailure(call: Call<PokemonResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<PokemonResponse?>,
                response: Response<PokemonResponse?>
            ) {
                val pokemonResponse = response.body()
                viewModel.response.set(pokemonResponse)
            }

        })

    }
}