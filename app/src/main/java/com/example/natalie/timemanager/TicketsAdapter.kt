package com.example.natalie.timemanager

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by natalie on 5/13/18.
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

class TicketsAdapter(val context: Context, val ticketsList: ArrayList<Ticket>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolder

        if (convertView == null) {
            view = parent?.inflate(R.layout.ticket)
            vh = ViewHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        vh.due.text = ticketsList[position].due
        vh.labels.text = ticketsList[position].labels
        vh.title.text = ticketsList[position].title
        vh.desc.text = ticketsList[position].desc
        vh.closed.text = if (ticketsList[position].closed == true) "The ticket is closed" else "The ticket is opened"

        return view
    }

    override fun getItem(position: Int): Any {
        return ticketsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return ticketsList.size
    }

    private class ViewHolder(view: View?) {
        val due: TextView = view?.findViewById<TextView>(R.id.dueText) as TextView
        val title: TextView = view?.findViewById<TextView>(R.id.titleText) as TextView
        val desc: TextView = view?.findViewById<TextView>(R.id.descText) as TextView
        val closed: TextView = view?.findViewById<TextView>(R.id.closedText) as TextView
        val labels: TextView = view?.findViewById<TextView>(R.id.labelsText) as TextView
    }
}