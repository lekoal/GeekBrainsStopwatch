package com.example.geekbrainsstopwatch.view

import androidx.lifecycle.LiveData

interface StopwatchMainContract {
    interface MainScreenView {
        fun setTimeDigits(viewId: Int)
        fun stopCounting(viewId: Int)
        fun resetCounting(viewId: Int)
    }

    abstract class ViewModel: androidx.lifecycle.ViewModel() {
        abstract fun runCount(viewId: Int)
        abstract fun stopCount(viewId: Int)
        abstract fun resetCount(viewId: Int)
        abstract fun onRestore()
        abstract val firstStopwatchDigits: LiveData<Long>
        abstract val secondStopwatchDigits: LiveData<Long>
        abstract val thirdStopwatchDigits: LiveData<Long>
    }
}