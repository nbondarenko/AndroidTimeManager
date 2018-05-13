package com.example.natalie.timemanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private var listTickets = ArrayList<Ticket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listTickets = LoadDataTask().execute().get()
        Log.d("ticket id", listTickets[0].id)

        var ticketsAdapter = TicketsAdapter(this, listTickets)
        lvTickets.adapter = ticketsAdapter
        lvTickets.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(this, "Click on " + listTickets[position].title, Toast.LENGTH_SHORT).show()
        }
    }
}
