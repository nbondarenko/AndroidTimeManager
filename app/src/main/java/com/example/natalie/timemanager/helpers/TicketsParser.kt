package com.example.natalie.timemanager.helpers

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.natalie.timemanager.Ticket
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Created by natalie on 5/13/18.
 */
class TicketsParser(jsonString: String) {
    private var jsonData = JSONObject(jsonString)
    private var jsonCards = jsonData.getJSONArray("cards")

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseTickets(): ArrayList<Ticket> {
        if(jsonCards == null || jsonCards.length() == 0) {
            return ArrayList<Ticket>()
        }
        var listTickets = ArrayList<Ticket>()
        var ticket: Ticket
        var id: String
        var title: String
        var due: String
        var url: String
        var desc: String
        var closed: Boolean
        var labels: String

        for (i in 0..(jsonCards.length() - 1)) {
            val jsonTicketCard = jsonCards.getJSONObject(i)

            id = jsonTicketCard.getString("id")
            title = jsonTicketCard.getString("name")
            url = jsonTicketCard.getString("url")
            desc = jsonTicketCard.getString("desc")
            due = getDueDate(jsonTicketCard)
            closed = jsonTicketCard.getBoolean("closed")
            labels = getLabels(jsonTicketCard)

            ticket = Ticket(id, title, due, url, desc, closed, labels)

            listTickets.add(ticket)
        }

        return listTickets
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDueDate(jsonTicket: JSONObject): String {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val dueDate = LocalDateTime.parse(jsonTicket.getString("due").dropLast(1))
        return dueDate.format(formatter)
    }

    fun getLabels(jsonTicket: JSONObject): String {
        var labelsArray = jsonTicket.getJSONArray("labels")
        var labelsNames = mutableListOf<String>()
        if(labelsArray == null || labelsArray.length() < 1) {
            return ""
        }

        for (i in 0..(labelsArray.length() - 1)) {
            val label = labelsArray.getJSONObject(i).getString("name")
            labelsNames.add(label)
        }
        return labelsNames.joinToString()
    }
}