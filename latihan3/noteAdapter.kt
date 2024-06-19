//package com.example.latihan3
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import com.google.android.material.imageview.ShapeableImageView
//import kotlinx.coroutines.MainScope
//import kotlinx.coroutines.launch
//
//class noteAdapter(private var context: Context, private var noteData: MutableList<noteModel>, private var resource: Int): RecyclerView.Adapter<ViewHolder>() {
//
//    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//    private var imageLoad = MainScope()
//
////    override fun getCount(): Int {
////        return noteData.size
////    }
////
////    override fun getItem(position: Int): Any {
////        return noteData[position]
////    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        var viewnote = inflater.inflate(resource, parent, false)
//        return ViewHolder(viewnote)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val note = noteData[position]
//    }
//
////    override fun getItemId(position: Int): Long {
////        return position.toLong()
////    }
//
//    override fun getItemCount(): Int {
//        return noteData.size
//    }
//
////    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
////        var viewnote = convertView ?: inflater.inflate(resource, null, false)
////
////        var username = viewnote.findViewById<TextView>(R.id.username_note)
////        var image = viewnote.findViewById<ShapeableImageView>(R.id.image_note)
////        var connection = Connection()
////        var data = getItem(position) as noteModel
////        username.text = data.username
////        imageLoad.launch {
////            val bitmap = LoadImageFromURL(connection.Connection + data.image)
////            image.setImageBitmap(bitmap)
////        }
////
////        return viewnote
////    }
//}