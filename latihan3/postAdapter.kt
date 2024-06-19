package com.example.latihan3

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.zip.Inflater

class postAdapter(private var context: Context, private var postData: MutableList<postModel>, private var resource: Int, private var fragment: Fragment): BaseAdapter() {

    private var inflater: LayoutInflater =context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var loadImage = MainScope()

    override fun getCount(): Int {
        return postData.size
    }

    override fun getItem(position: Int): Any {
        return postData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var rowview = convertView ?: inflater.inflate(resource, parent, false)

        val username1 = rowview.findViewById<TextView>(R.id.username_post)
        val username2 = rowview.findViewById<TextView>(R.id.username2)
        var image = rowview.findViewById<ImageView>(R.id.image_post)
        val desc = rowview.findViewById<TextView>(R.id.desc)
        var love = rowview.findViewById<ImageView>(R.id.love)
        val comment = rowview.findViewById<ImageView>(R.id.comment)
        val bookmark = rowview.findViewById<ImageView>(R.id.bookmark)
        var connection = Connection()

        var data = getItem(position) as postModel
        username2.text = data.nama
        username1.text = data.nama
        desc.text = data.desc

        loadImage.launch {
            val bitmap = LoadImageFromURL(connection.Connection + data.gambar)
            image.setImageBitmap(bitmap)
        }

        love.setOnClickListener {
            love.setImageResource(R.drawable.baseline_favorite_24)
        }

        bookmark.setOnClickListener {
            bookmark.setImageResource(R.drawable.baseline_bookmark_24)
        }

        comment.setOnClickListener {
            var dialog =  BottomSheetDialog(fragment.requireActivity())
            var view = inflater.inflate(R.layout.commentview, null)

            dialog.setContentView(view)
            dialog.show()
        }

        return rowview

    }

}