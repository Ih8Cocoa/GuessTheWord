package com.example.android.guesstheword.screens.game

enum class BuzzType(val pattern: LongArray) {
    CORRECT(longArrayOf(100, 100, 100, 100, 100, 100)),
    GAME_OVER(longArrayOf(0, 200)),
    COUNTDOWN_PANIC(longArrayOf(0, 400)),
    NO_BUZZ(longArrayOf(0));
}