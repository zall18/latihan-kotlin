package com.example.latihan3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    private lateinit var postAdapter: postAdapter
    private lateinit var postData: MutableList<postModel>
    private lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listview: ListView = view.findViewById(R.id.listview_post)
        session = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        val connection: Connection = Connection()

        lifecycleScope.launch {
            val result = getrequest(connection.Connection + "api/products", session.getString("token", ""))

            result.fold(
                onSuccess = {
                    response ->

                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("produk")
                    postData = mutableListOf<postModel>()

                    for (i in 0 until jsonArray.length()){
                        val jsonObject2 = jsonArray.getJSONObject(i)
                        postData.add(postModel(jsonObject2.getString("id"), jsonObject2.getString("nama_barang"), jsonObject2.getString("gambar"), jsonObject2.getString("deskripsi")))
                    }
                    postAdapter = postAdapter(requireContext().applicationContext, postData, R.layout.postitem, this@Home)
                    listview.adapter = postAdapter
                },
                onFailure = {
                    error -> println(error)
                }
            )
        }

        val chatActv = view.findViewById<ImageView>(R.id.chat_button)
        chatActv.setOnClickListener {
            startActivity(Intent(requireContext(), chat::class.java))
        }
    }

}