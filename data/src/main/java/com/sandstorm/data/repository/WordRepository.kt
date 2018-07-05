package com.sandstorm.data.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.sandstorm.data.local.WordRoomDatabase
import com.sandstorm.data.local.dao.WordDao
import com.sandstorm.data.local.entity.Word

class WordRepository(application: Application) {
    private var mWordDao: WordDao? = null
    private var mAllWords: LiveData<List<Word>>? = null

    init {
        val db = WordRoomDatabase.getInstance(application)
        mWordDao = db?.wordDao()
        mAllWords = mWordDao?.getAllWords()
    }

    fun getAllWords(): LiveData<List<Word>>? {
        return mAllWords
    }

    fun insert(word: Word) {
        InsertAsyncTask(mWordDao).execute(word)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: WordDao?) : AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg params: Word): Void? {
            mAsyncTaskDao?.insert(params[0])
            return null
        }
    }

}