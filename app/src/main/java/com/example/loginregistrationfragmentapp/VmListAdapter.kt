package com.example.loginregistrationfragmentapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.loginregistrationfragmentapp.vmlist.Deployment

class VmListAdapter(
    context: Context,
    private val vmList: List<Deployment>,
    private val onItemClick: (Deployment) -> Unit
) : ArrayAdapter<Deployment>(context, R.layout.list_item_with_image, vmList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_with_image, parent, false)

        val vm = getItem(position)
        val imageView = view.findViewById<ImageView>(R.id.row_image)
        val textView = view.findViewById<TextView>(R.id.row_text)

        when (vm.monitoring.state) {
            0 -> imageView.setImageResource(R.drawable.vmicon_error)
            1 -> imageView.setImageResource(R.drawable.vmicon)
            2 -> imageView.setImageResource(R.drawable.vmicon_on)
            3-> imageView.setImageResource(R.drawable.vmicon_suspended)
            4 -> imageView.setImageResource(R.drawable.vmicon_error)
            5 -> imageView.setImageResource(R.drawable.vmicon_warning)
            6 -> imageView.setImageResource(R.drawable.vmicon_warning)
            7 -> imageView.setImageResource(R.drawable.vmicon_suspended)
            8 -> imageView.setImageResource(R.drawable.vmicon_warning)
            9 -> imageView.setImageResource(R.drawable.vmicon_warning)
            10 -> imageView.setImageResource(R.drawable.vmicon_warning)
        }

        vm?.let {
            textView.text = it.name
            view.setOnClickListener { _ -> onItemClick(it) }
        }

        return view
    }

    override fun getItem(position: Int): Deployment = vmList[position]
}