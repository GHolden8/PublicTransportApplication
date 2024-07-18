package com.example.mapstest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.mapstest.R

class CustomDropdownAdapter(
    context: Context,
    private val items: MutableList<String>,
    private val deleteCallback: (String) -> Unit
) : ArrayAdapter<String>(context, 0, items) {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): String? {
        return items[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent, false)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent, true)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup, isDropdown: Boolean): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.dropdown_item_with_delete, parent, false)
        val item = getItem(position) ?: ""

        val textView = view.findViewById<TextView>(R.id.text_view_item)
        val deleteButton = view.findViewById<Button>(R.id.button_delete)

        textView.text = item

        deleteButton.visibility = View.VISIBLE

        textView.setOnClickListener {
            (parent as? AdapterView<*>)?.performItemClick(view, position, getItemId(position))
        }

        deleteButton.setOnClickListener {
            deleteCallback(item)
            items.remove(item)
            notifyDataSetChanged()
        }

        return view
    }
}
