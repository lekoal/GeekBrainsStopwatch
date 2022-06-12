package com.example.geekbrainsstopwatch.view

import android.util.Log
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
                        Log.i("MY_TAG", firstStopwatchDigits.value.toString())
                    }
                }
            }
        }

    }

    override fun stopCount(viewId: Int) {
        when(viewId) {
            R.id.stopwatch_one_stop_button -> {
                firstJob?.cancel()
            }
        }
    }

    override fun onRestore() {

    }
}