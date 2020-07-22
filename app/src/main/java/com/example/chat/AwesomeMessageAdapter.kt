package com.example.chat

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class AwesomeMessageAdapter(ctx: Context, resource: Int, messages: List<AwesomeMessage>) :
    ArrayAdapter<AwesomeMessage>(ctx, resource, messages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View
        if (convertView == null) {
            view = LayoutInflater.from(context as Activity).inflate(
                R.layout.message_item,
                parent,
                false
            )
        } else {
            view = convertView
        }

        val photoImage: ImageView = view.findViewById(R.id.imageView)
        val nameView: TextView = view.findViewById(R.id.nameView)
        val textView: TextView = view.findViewById(R.id.textView)

        val message: AwesomeMessage? = getItem(position)

        val is_text: Boolean = message?.photoURL == null

        if (is_text) {
            textView.visibility = View.VISIBLE
            photoImage.visibility = View.GONE
            textView.text = message?.text
        } else {
            textView.visibility = View.GONE
            photoImage.visibility = View.VISIBLE
            Glide
                .with(photoImage.context)
                .load(message?.photoURL)
                .into(photoImage)
        }

        nameView.text = message?.name

        return view
    }
}