package com.example.latihan3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import java.util.zip.Inflater

class profilePostAdapter(private var context: Context, private var imageList: MutableList<String>, private var resource: Int):BaseAdapter() {

    var layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return imageList.size
    }

    override fun getItem(position: Int): Any {
        return imageList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var rowview = convertView ?: layoutInflater.inflate(resource, parent, false)

        var image = rowview.findViewById<ImageView>(R.id.image_grid)

        image.setImageResource(R.drawable.gojo2)

        return rowview
    }
}