package com.example.geekbrainsstopwatch.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.geekbrainsstopwatch.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

class MainViewModel : StopwatchMainContract.ViewModel() {
    override val firstStopwatchDigits: MutableLiveData<Int> = MutableLiveData(0)
    override val secondStopwatchDigits: MutableLiveData<Int> = MutableLiveData(0)
    override val thirdStopwatchDigits: MutableLiveData<Int> = MutableLiveData(0)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("SOME_ERROR", "Error found: $throwable")
    }

    private var firstCount = 0
    private var secondCount = 0
    private var thirdCount = 0

    private val scope = CoroutineScope(
        Dispatchers.IO
                + coroutineExceptionHandler
                + SupervisorJob()
    )

    private var firstJob: Job? = null
    private var secondJob: Job? = null
    private var thirdJob: Job? = null

    private val firstFlow = flow {
        while (true) {
            delay(1000)
            firstCount++
            emit(firstCount)
        }
    }

    private val secondFlow = flow {
        while (true) {
            delay(1000)
            secondCount++
            emit(secondCount)
        }
    }

    private val thirdFlow = flow {
        while (true) {
            delay(1000)
            thirdCount++
            emit(thirdCount)
        }
    }

    override fun runCount(viewId: Int) {
        when (viewId) {
            R.id.stopwatch_one_start_button -> {
                firstJob = scope.launch {
                    while (true) {
                        firstFlow.collect {
                            firstStopwatchDigits.postValue(it)
                        }
                    }
                }
            }
            R.id.stopwatch_two_start_button -> {
                secondJob = scope.launch {
                    while (true) {
                        secondFlow.collect {
                            secondStopwatchDigits.postValue(it)
                        }
                    }
                }
            }
            R.id.stopwatch_three_start_button -> {
                thirdJob = scope.launch {
                    while (true) {
                        thirdFlow.collect {
                            thirdStopwatchDigits.postValue(it)
                        }
                    }
                }
            }
        }
    }

    override fun stopCount(viewId: Int) {
        when (viewId) {
            R.id.stopwatch_one_stop_button -> {
                if (firstJob?.isActive == true) {
                    firstJob?.cancel()
                }
            }
            R.id.stopwatch_two_stop_button -> {
                if (secondJob?.isActive == true) {
                    secondJob?.cancel()
                }
            }
            R.id.stopwatch_three_stop_button -> {
                if (thirdJob?.isActive == true) {
                    thirdJob?.cancel()
                }
            }
        }
    }

    override fun resetCount(viewId: Int) {
        when (viewId) {
            R.id.stopwatch_one_reset_button -> {
                stopCount(R.id.stopwatch_one_stop_button)
                firstCount = 0
                firstStopwatchDigits.postValue(0)
            }
            R.id.stopwatch_two_reset_button -> {
                stopCount(R.id.stopwatch_two_stop_button)
                secondCount = 0
                secondStopwatchDigits.postValue(0)
            }
            R.id.stopwatch_three_reset_button -> {
                stopCount(R.id.stopwatch_three_stop_button)
                thirdCount = 0
                thirdStopwatchDigits.postValue(0)
            }
        }
    }

    override fun onRestore() {

    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}