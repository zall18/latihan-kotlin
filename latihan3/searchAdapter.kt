package com.example.latihan3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class searchAdapter(private var context: Context, private var searchData: MutableList<searchModel>, private var resource: Int): BaseAdapter(), Filterable {

    private var inflate: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var data: MutableList<searchModel> = searchData
    private var loadImage = MainScope()


    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var rowview = convertView ?: inflate.inflate(resource, parent, false)

        var image = rowview.findViewById<ShapeableImageView>(R.id.image_search)
        var name = rowview.findViewById<TextView>(R.id.nama_searc)
        var desc = rowview.findViewById<TextView>(R.id.desc_search)
        var details = rowview.findViewById<LinearLayout>(R.id.search_detail)
        val connection:Connection = Connection()

        val item = getItem(position) as searchModel

        name.text = item.nama
        desc.text = item.desc

        loadImage.launch {
            val bitmap = LoadImageFromURL(connection.Connection + item.gambar)
            image.setImageBitmap(bitmap)
        }

        details.setOnClickListener {
            var intent: Intent = Intent(context, detail::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("id", item.id)
            ContextCompat.startActivity(context, intent, null)
        }

        return rowview
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filtering = mutableListOf<searchModel>()

                if (constraint.isNullOrBlank()){
                    filtering.addAll(searchData)
                }else{
                    val filterPattern = constraint.toString().lowercase().trim()

                    for (item in searchData)
                    {
                        if(item.nama.lowercase().contains(filterPattern)){
                            filtering.add(searchModel(item.id, item.nama, item.desc, item.gambar))
                        }
                    }
                }

                val result = FilterResults()
                result.values = filtering
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                data = results?.values as ArrayList<searchModel>
                notifyDataSetChanged()
            }
            }

    }
}