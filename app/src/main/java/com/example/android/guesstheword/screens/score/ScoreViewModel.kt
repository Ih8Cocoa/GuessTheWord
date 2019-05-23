package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(val finalScore: Int) : ViewModel() {
    // wanna play again?
    private val _playAgain = MutableLiveData<Boolean>()

    val playAgain: LiveData<Boolean>
        get() = _playAgain

    init {
        Log.i("ScoreViewModel", "The final score is $finalScore")
    }

    fun onPlayAgain() {
        _playAgain.value = true
    }

    fun onPlayAgainDone() {
        _playAgain.value = false
    }
}