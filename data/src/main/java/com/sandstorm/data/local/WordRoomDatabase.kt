package com.sandstorm.data.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import com.sandstorm.data.local.dao.WordDao
import com.sandstorm.data.local.entity.Word


@Database(entities = [(Word::class)], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        private var INSTANCE: WordRoomDatabase? = null

        fun getInstance(context: Context): WordRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(WordRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<WordRoomDatabase>(context.applicationContext, WordRoomDatabase::class.java, "word_database")
                                .addCallback(sRoomDatabaseCallback)
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        @JvmStatic
        val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }

        private class PopulateDbAsync internal constructor(db: WordRoomDatabase?) : AsyncTask<Void, Void, Void>() {

            private val mDao: WordDao? = db?.wordDao()

            override fun doInBackground(vararg params: Void): Void? {
                mDao?.deleteAll()
                var word = Word("Hello")
                mDao?.insert(word)
                word = Word("World")
                mDao?.insert(word)
                return null
            }
        }
    }


}