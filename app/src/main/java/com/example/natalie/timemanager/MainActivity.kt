package com.example.natalie.timemanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var listTickets = ArrayList<Ticket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        doAsync {
//            val result = URL("https://api.trello.com/1/boards/LFFSZcYL/?cards=all&key=2305ecf8603d372cc910909bdfc69f53&token=8d66af343986ceaf9d0cba5e291d271fd906934c5d61d245c34c5d9a7ab7a25c").readText()
//            uiThread {
//                Log.d("Request", result)
//            }
//        }
        listTickets.add(Ticket("1", "Lab1", "CV", "aaa", "", "desc1", true))
        listTickets.add(Ticket("2", "Lab2", "CV", "aaa", "", "desc1", true))

        var ticketsAdapter = TicketsAdapter(this, listTickets)
        lvTickets.adapter = ticketsAdapter
        lvTickets.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(this, "Click on " + listTickets[position].title, Toast.LENGTH_SHORT).show()
        }
    }

//    protected fun onStop() {
//        super.onStop()
//        requestQueue?.cancelAll(TAG)
//    }
}
