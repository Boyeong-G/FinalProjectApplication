package com.example.finalprojectapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):SQLiteOpenHelper(context, "searchWords", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
//        db?.execSQL("create table searchWords (_id integer primary key autoincrement, username not null, words not null)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}