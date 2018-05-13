package com.example.natalie.timemanager

import android.os.AsyncTask
import android.util.Log
import com.example.natalie.timemanager.helpers.TicketsParser
import java.net.URL

/**
 * Created by natalie on 5/13/18.
 */
class LoadDataTask: AsyncTask<String, String, ArrayList<Ticket>>() {
    private var listTickets = ArrayList<Ticket>()
    override fun doInBackground(vararg p0: String?): ArrayList<Ticket>? {
        try {
            val result = URL("https://api.trello.com/1/boards/LFFSZcYL/?cards=all&key=2305ecf8603d372cc910909bdfc69f53&token=8d66af343986ceaf9d0cba5e291d271fd906934c5d61d245c34c5d9a7ab7a25c").readText()
            val parser = TicketsParser(result)
            listTickets = parser.parseTickets()
        } catch(Ex: Exception) {
            Log.d("LoadDataTask", "Error in LoadDataTask " + Ex.message);
        }
        return listTickets
    }
}