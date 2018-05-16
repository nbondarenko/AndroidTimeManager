package com.example.natalie.timemanager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_timer.*
import android.widget.Toast
import android.os.SystemClock
import android.util.Log
import com.example.natalie.timemanager.helpers.MyDatabaseOpenHelper
import com.example.natalie.timemanager.helpers.TicketHelper


class TimerActivity : AppCompatActivity() {
    var startTime : Long = 0
    var duration: Long = 0
    lateinit var ticketId: String
    private lateinit var database: MyDatabaseOpenHelper
    private lateinit var ticketHelper: TicketHelper

    companion object {
        const val TICKET_ID = "ticketId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras == null) {
            ticketId = null.toString()
        } else {
            ticketId = extras.getString(TICKET_ID)
        }
        database = MyDatabaseOpenHelper.getInstance(this)
        ticketHelper = TicketHelper(database)
        val ticket = ticketHelper.getById(ticketId)
        Log.d("Time before dvizucha: ", ticket.timeSpent.toString())
        setContentView(R.layout.activity_timer)
    }

    private fun showElapsedTime() {
        val elapsedMillis = SystemClock.elapsedRealtime() - chronometr.getBase()
        Toast.makeText(this, "Elapsed milliseconds: " + elapsedMillis,
                Toast.LENGTH_SHORT).show()
    }

    fun startTimer(view: View) {
        chronometr.setBase(SystemClock.elapsedRealtime())
        chronometr.start()
        startTime = SystemClock.elapsedRealtime()
        Log.d("startTimer: startTime", startTime.toString())
        Log.d("startTimer: duration", duration.toString())
        Log.d("startTimer: chronometr.getBase()", chronometr.getBase().toString())
        showElapsedTime()
    }

    fun stopTimer(view: View) {
        chronometr.stop()
        duration = SystemClock.elapsedRealtime() - startTime
        Log.d("stopTimer: startTime", startTime.toString())
        Log.d("stopTimer: duration", duration.toString())
        Log.d("stopTimer: chronometr.getBase()", chronometr.getBase().toString())
        showElapsedTime()
    }

    fun writeTime(view: View) {
        var durationInSeconds = duration / 1000
        Log.d("durationInSeconds: ", durationInSeconds.toString())
        ticketHelper.writeTimeSpent(ticketId, durationInSeconds)
        val ticket = ticketHelper.getById(ticketId)
        Log.d("Time after dvizucha: ", ticket.timeSpent.toString())

        val mainScreenIntent = Intent(this, MainActivity::class.java)

        // Start the new activity.
        startActivity(mainScreenIntent)
    }
}
