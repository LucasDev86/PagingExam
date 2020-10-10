package com.lucasdev.pagingexam

import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField

class ViewModel{
    var name = ObservableField<String>()
    var url = ObservableField<String>()

    fun openDetail(view: View){
        val parts = url.get()?.split("/")
        val pid = parts!![6].toInt()
        val context = view.context
        val intent = Intent(context , DetailActivity::class.java)
        intent.putExtra("pid" , pid)
        context.startActivity(intent)
    }

}