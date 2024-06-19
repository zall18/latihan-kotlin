package com.example.latihan3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.w3c.dom.Text


class Profile : Fragment() {

    private lateinit var session: SharedPreferences
    private lateinit var profilePostAdapter: profilePostAdapter
    private lateinit var data: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = view.findViewById<TextView>(R.id.username_profile)
        val name = view.findViewById<TextView>(R.id.name_profile)
        session = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        val connection: Connection = Connection()

        var gridView: GridView = view.findViewById(R.id.gridview)
        val postCount: TextView = view.findViewById(R.id.postCount)

        data = mutableListOf<String>()
        for (i in 0 until 30){
            data.add("$i")
        }

        val menu_button = view.findViewById<ImageView>(R.id.menu_button);

        menu_button.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), menu_button)
            popupMenu.menuInflater.inflate(R.menu.toolbar, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener{
                item ->
                when(item.itemId){
                    R.id.item1 -> {
                        Toast.makeText(requireContext(), "You click item 1", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    R.id.item2 -> {
                        Toast.makeText(requireContext(), "You click item 2", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    R.id.item3 -> {
                        Toast.makeText(requireContext(), "You click item 3", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        profilePostAdapter = profilePostAdapter(requireContext().applicationContext, data, R.layout.griditem)
        gridView.adapter = profilePostAdapter

        lifecycleScope.launch {
            val result = getrequest(connection.Connection + "api/users/" + session.getString("id", ""), session.getString("token", ""))

            result.fold(
                onSuccess = {
                    response ->

                    var jsonObject = JSONObject(response)

                    if(jsonObject.getString("id").isNotEmpty()){
                        username.text = jsonObject.getString("username")
                        name.text = jsonObject.getString("name")
                        postCount.text = data.size.toString()


                    }
                },
                onFailure = {
                    error -> println(error)
                }
            )
        }



    }



}