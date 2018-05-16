package com.example.natalie.timemanager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import com.example.natalie.timemanager.helpers.MyDatabaseOpenHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*


class MainActivity : AppCompatActivity() {
    private lateinit var database: MyDatabaseOpenHelper
    private var listTickets = ArrayList<Ticket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = MyDatabaseOpenHelper.getInstance(this)
        listTickets = LoadDataTask(database).execute().get()

        val ts = database.use {
            select("tickets")
                    .parseList(classParser<Ticket>()
                    )
        }
        Log.d("ticketCount", ts.count().toString())

        var ticketsAdapter = TicketsAdapter(this, listTickets)
        lvTickets.adapter = ticketsAdapter
        lvTickets.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(this, "Click on " + listTickets[position].title, Toast.LENGTH_SHORT).show()

            // Create an Intent to start the second activity
            val timerIntent = Intent(this, TimerActivity::class.java)
            timerIntent.putExtra(TimerActivity.TICKET_ID, listTickets[position].id.toString())

            // Start the new activity.
            startActivity(timerIntent)
        }
    }
}
