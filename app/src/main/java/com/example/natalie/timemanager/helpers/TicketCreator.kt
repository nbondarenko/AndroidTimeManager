package com.example.natalie.timemanager.helpers

import android.content.ContentValues
import android.util.Log
import com.example.natalie.timemanager.Ticket
import org.jetbrains.anko.db.*

/**
 * Created by natalie on 5/13/18.
 */
class TicketCreator(var db: MyDatabaseOpenHelper) {
    fun showLast() : Ticket {
        val t = db.use {
            select("tickets")
                    .limit(1)
                    .parseList(classParser<Ticket>()
                    )
        }
        return t[0]
    }

    fun createOrUpdate(id: String, ticket: Ticket): Ticket {
        val t = db.use {
            select("tickets")
                    .whereArgs("id = {id}", "id" to id)
                    .limit(1)
                    .parseList(classParser<Ticket>()
                    )
        }
        if (t.count() == 0) {
            Log.d("ticket id from db creator", t.count().toString())
            db.use {
                insert("tickets",
                        "id" to id,
                        "title" to ticket.title,
                        "due" to ticket.due,
                        "url" to ticket.url,
                        "desc" to ticket.desc,
                        "closed" to ticket.closed,
                        "labels" to ticket.labels
                )
            }
        } else {
            Log.d("ticket id from db updater", t[0].title.toString())
            db.use {
                update("tickets",
                        "title" to ticket.title,
                        "due" to ticket.due,
                        "url" to ticket.url,
                        "desc" to ticket.desc,
                        "closed" to ticket.closed,
                        "labels" to ticket.labels)
                        .whereArgs("id = {id}", "id" to id)
                        .exec()
            }

        }

        var newTicket = db.use {
            select("tickets")
                    .whereArgs("id = {id}", "id" to id)
                    .limit(1)
                    .parseSingle(classParser<Ticket>()
                    )
        }

        return newTicket
    }
}