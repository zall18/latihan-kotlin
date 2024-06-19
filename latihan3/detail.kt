package com.example.latihan3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class detail : AppCompatActivity() {
    private lateinit var session: SharedPreferences
    private lateinit var profilePostAdapter: profilePostAdapter
    private lateinit var data: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username:TextView = findViewById(R.id.username_profile_detail)
        val name:TextView = findViewById(R.id.name_profile_detail)
        val image: ShapeableImageView = findViewById(R.id.image_detail)
        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        val intent: Intent = getIntent()
        val id: String = intent.getStringExtra("id").toString()
        val connection: Connection = Connection()

        var gridView: GridView = findViewById(R.id.gridview_detail)
        val postCount: TextView = findViewById(R.id.postCountDetail)

        data = mutableListOf<String>()
        for (i in 0 until 30){
            data.add("$i")
        }

        profilePostAdapter = profilePostAdapter(applicationContext, data, R.layout.griditem)
        gridView.adapter = profilePostAdapter
        var imageUrl = MainScope()

        lifecycleScope.launch {
            val result = getrequest(connection.Connection + "api/products/" + id, session.getString("token", ""))

            result.fold(
                onSuccess = {
                        response ->

                    var jsonObject = JSONObject(response)

                    if(jsonObject.getString("produk").isNotEmpty()){

                        var jsonArray = JSONArray(jsonObject.getString("produk"))
//                        var jsonObject2 = JSONObject(jsonObject.getString("produk"))

                        for (i in 0 until jsonArray.length()){
                            var jsonObject2 = jsonArray.getJSONObject(i)
                            username.text = jsonObject2.getString("nama_barang")
                            name.text = jsonObject2.getString("nama_barang")
                            postCount.text = data.size.toString()

                            imageUrl.launch {
                                val bitmap = LoadImageFromURL(connection.Connection + jsonObject2.getString("gambar"))
                                image.setImageBitmap(bitmap)
                            }
                        }




                    }
                },
                onFailure = {
                        error -> println(error)
                }
            )
        }

        val back: ImageView = findViewById(R.id.back)

        back.setOnClickListener {
            startActivity(Intent(applicationContext, BottomNav::class.java))
        }
    }
}