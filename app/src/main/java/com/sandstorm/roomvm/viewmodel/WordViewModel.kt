package com.sandstorm.roomvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sandstorm.data.local.entity.Word
import com.sandstorm.data.repository.WordRepository


class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: WordRepository = WordRepository(application)

    internal val allWords: LiveData<List<Word>>?

    init {
        allWords = mRepository.getAllWords()
    }

    fun insert(word: Word) {
        mRepository.insert(word)
    }
}