package com.example.natalie.timemanager.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by natalie on 5/13/18.
 */
class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "TimeManagerDb", null, 3) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("tickets", true,
                "id" to SqlType.create("TEXT PRIMARY KEY"),
                "title" to TEXT,
                "desc" to TEXT,
                "url" to TEXT,
                "due" to TEXT,
                "closed" to INTEGER,
                "labels" to TEXT,
                "timeSpent" to SqlType.create("INTEGER DEFAULT 0")
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}