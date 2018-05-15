package com.example.natalie.timemanager

import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.natalie.timemanager.helpers.MyDatabaseOpenHelper
import com.example.natalie.timemanager.helpers.TicketCreator
import com.example.natalie.timemanager.helpers.TicketsParser
import java.net.URL

/**
 * Created by natalie on 5/13/18.
 */
class LoadDataTask(val database: MyDatabaseOpenHelper) : AsyncTask<String, String, ArrayList<Ticket>>() {
    private var listTickets = ArrayList<Ticket>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doInBackground(vararg p0: String?): ArrayList<Ticket>? {
        try {
            val result = URL("https://api.trello.com/1/boards/bayp41u3/?cards=all&key=2305ecf8603d372cc910909bdfc69f53&token=8d66af343986ceaf9d0cba5e291d271fd906934c5d61d245c34c5d9a7ab7a25c").readText()
            val parser = TicketsParser(result)
            val ticketCreator = TicketCreator(database)
            try {
                parser.parseTickets().forEach {
                    Log.d("ticket id", it.id)
                    val a = ticketCreator.createOrUpdate(it.id, it)
                    Log.d("after createOrUpdate", a.id)
                    listTickets.add(a)
                }
//                listTickets = parser.parseTickets()
            } catch (Ex: Exception) {

            }

        } catch(Ex: Exception) {
            Log.d("LoadDataTask", "Error in LoadDataTask " + Ex.message);
        }
        return listTickets
    }
}