package com.example.geekbrainsstopwatch.view

import androidx.lifecycle.MutableLiveData
import com.example.geekbrainsstopwatch.R
import kotlinx.coroutines.*

class MainViewModel : StopwatchMainContract.ViewModel() {
    override val firstStopwatchDigits: MutableLiveData<Long> = MutableLiveData(0L)
    override val secondStopwatchDigits: MutableLiveData<Long> = MutableLiveData(0L)
    override val thirdStopwatchDigits: MutableLiveData<Long> = MutableLiveData(0L)

    private var firstCount = 0L
    private var secondCount = 0L
    private var thirdCount = 0L

    private val scope = CoroutineScope(Dispatchers.IO)

    private var firstJob: Job? = null
    private var secondJob: Job? = null
    private var thirdJob: Job? = null

    override fun runCount(viewId: Int) {
        when(viewId) {
            R.id.stopwatch_one_start_button -> {
                firstJob = scope.launch {
                    while(true) {
                        delay(1000)
                        firstCount++
                        firstStopwatchDigits.postValue(firstCount)
                    }
                }
            }
            R.id.stopwatch_two_start_button -> {
                secondJob = scope.launch {
                    while (true) {
                        delay(1000)
                        secondCount++
                        secondStopwatchDigits.postValue(secondCount)
                    }
                }
            }
            R.id.stopwatch_three_start_button -> {
                thirdJob = scope.launch {
                    while (true) {
                        delay(1000)
                        thirdCount++
                        thirdStopwatchDigits.postValue(thirdCount)
                    }
                }
            }
        }
    }

    override fun stopCount(viewId: Int) {
        when(viewId) {
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
        when(viewId) {
            R.id.stopwatch_one_reset_button -> {
                stopCount(R.id.stopwatch_one_stop_button)
                firstCount = 0L
                firstStopwatchDigits.postValue(0L)
            }
            R.id.stopwatch_two_reset_button -> {
                stopCount(R.id.stopwatch_two_stop_button)
                secondCount = 0L
                secondStopwatchDigits.postValue(0L)
            }
            R.id.stopwatch_three_reset_button -> {
                stopCount(R.id.stopwatch_three_stop_button)
                thirdCount = 0L
                thirdStopwatchDigits.postValue(0L)
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