package com.example.latihan3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.json.JSONObject

class chat : AppCompatActivity() {

//    lateinit var noteAdapter: noteAdapter
    lateinit var notedata: MutableList<noteModel>
    lateinit var notelist: RecyclerView
    lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var connection = Connection()
        notelist = findViewById(R.id.note_list)
        session = getSharedPreferences("session", Context.MODE_PRIVATE)
//        lifecycleScope.launch {
//
//            var result = getrequest(connection.Connection, session.getString("token", ""))
//            result.fold(
//                onSuccess = {
//                        response ->
//
//                    val jsonObject = JSONObject(response)
//                    val jsonArray = jsonObject.getJSONArray("produk")
//                    notedata = mutableListOf<noteModel>()
//
//                    for (i in 0 until jsonArray.length()){
//                        val jsonObject2 = jsonArray.getJSONObject(i)
//                        notedata.add(noteModel(jsonObject2.getString("nama_barang"), jsonObject2.getString("gambar")))
//                    }
//                    noteAdapter = noteAdapter(applicationContext.applicationContext, notedata, R.layout.postitem)
//                    notelist.layoutManager = LinearLayoutManager(this@chat, LinearLayoutManager.HORIZONTAL, false)
//                    notelist.adapter = noteAdapter
//                },
//                onFailure = {
//                        error -> println(error)
//                }
//            )
//
//        }

        val back : ImageView = findViewById(R.id.back_chat)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, BottomNav::class.java))
        }
    }
}