package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // Create a companion object (idk)
    companion object {
        private const val ONE_SEC = 1000L
        private const val COUNTDOWN = 30000L
        private const val COUNTDOWN_PANIC_SECONDS = 10L
    }

    private val _timer = object : CountDownTimer(COUNTDOWN, ONE_SEC) {
        override fun onFinish() {
            _timeLeft.value = 0
            _buzzing.value = BuzzType.GAME_OVER
            _gameFinished.value = true
        }

        override fun onTick(millisUntilFinished: Long) {
            _timeLeft.value = millisUntilFinished / ONE_SEC
            if (millisUntilFinished / ONE_SEC <= COUNTDOWN_PANIC_SECONDS) {
                _buzzing.value = BuzzType.COUNTDOWN_PANIC
            }
        }
    }

    // Time left (used internally)
    private val _timeLeft = MutableLiveData<Long>()

    // The current word (used internally)
    private var _word = MutableLiveData("")

    // The current score (used internally)
    private val _score = MutableLiveData(0)

    // Is the game finished? (used internally)
    private val _gameFinished = MutableLiveData(false)

    // Buzzing stuff
    private val _buzzing = MutableLiveData<BuzzType>()
    val buzzing: LiveData<BuzzType>
        get() = _buzzing

    // immutable backing properties, which can be used by the fragment
    val score: LiveData<Int>
        get() = _score

    val word: LiveData<String>
        get() = _word

    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    // Convert _timeLeft to string for use in XML
    val timeLeftString = Transformations.map(_timeLeft) {
        DateUtils.formatElapsedTime(it)
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "Created GameViewModel")
        resetList()
        nextWord()
        _timer.start()
    }

    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        _buzzing.value = BuzzType.CORRECT
        nextWord()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "Destroyed GameViewModel")
        _timer.cancel()
    }

    fun onGameFinishedDisplayed() {
        _gameFinished.value = false
    }

    fun onBuzzComplete() {
        _buzzing.value = BuzzType.NO_BUZZ
    }
}