package com.example.latihan3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.json.JSONObject


class Search : Fragment() {

    private lateinit var searchAdapter: searchAdapter
    private lateinit var searchData: MutableList<searchModel>
    private lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listview: ListView = view.findViewById(R.id.listview_search)
        session = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        val connection: Connection = Connection()

        lifecycleScope.launch {
            val result = getrequest(connection.Connection + "api/products", session.getString("token", ""))

            result.fold(
                onSuccess = {
                        response ->

                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("produk")
                    searchData = mutableListOf<searchModel>()

                    for (i in 0 until jsonArray.length()){
                        val jsonObject2 = jsonArray.getJSONObject(i)
                        searchData.add(searchModel(jsonObject2.getString("id"), jsonObject2.getString("nama_barang"), jsonObject2.getString("deskripsi"), jsonObject2.getString("gambar")))
                    }
                    searchAdapter = searchAdapter(requireContext().applicationContext, searchData, R.layout.searchitem)
                    listview.adapter = searchAdapter
                },
                onFailure = {
                        error -> println(error)
                }
            )
        }

        var search: SearchView = view.findViewById(R.id.searchBar)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                searchAdapter.filter.filter(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
    }


}