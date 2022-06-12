package com.example.geekbrainsstopwatch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.geekbrainsstopwatch.databinding.FragmentMainScreenBinding
import com.example.geekbrainsstopwatch.utils.ConvertedTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MainScreenFragment : Fragment(), StopwatchMainContract.MainScreenView {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel(named("main_view_model"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MainScreenFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBoxIsChecked(binding.stopwatchTwoEnableCheckbox)
        checkBoxIsChecked(binding.stopwatchThreeEnableCheckbox)

        setOnButtonsClick()
    }

    private fun checkBoxIsChecked(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                when (compoundButton.id) {
                    binding.stopwatchTwoEnableCheckbox.id -> {
                        binding.stopwatchTwoLayout.visibility = View.VISIBLE
                        binding.stopwatchThreeEnableCheckbox.visibility = View.VISIBLE
                    }
                    binding.stopwatchThreeEnableCheckbox.id -> {
                        binding.stopwatchThreeLayout.visibility = View.VISIBLE
                        binding.stopwatchTwoEnableCheckbox.isEnabled = false
                    }
                }
            } else {
                when (compoundButton.id) {
                    binding.stopwatchTwoEnableCheckbox.id -> {
                        viewModel.resetCount(binding.stopwatchTwoResetButton.id)
                        binding.stopwatchTwoLayout.visibility = View.GONE
                        binding.stopwatchThreeEnableCheckbox.visibility = View.GONE
                    }
                    binding.stopwatchThreeEnableCheckbox.id -> {
                        viewModel.resetCount(binding.stopwatchThreeResetButton.id)
                        binding.stopwatchThreeLayout.visibility = View.GONE
                        binding.stopwatchTwoEnableCheckbox.isEnabled = true
                    }
                }
            }
        }
    }

    override fun setTimeDigits(viewId: Int) {
        when (viewId) {
            binding.stopwatchOneStartButton.id -> {
                viewModel.firstStopwatchDigits.observe(viewLifecycleOwner) {
                    binding.stopwatchOneSecondsCounterTextview.text = it.toString()
                    val convertedTime = secondsConvert(it)

                    binding.stopwatchOneHourTextview.text = convertedTime.hours.toString()
                    binding.stopwatchOneMinutesTextview.text = convertedTime.minutes.toString()
                    binding.stopwatchOneSecondsTextview.text = convertedTime.seconds.toString()
                }
            }
            binding.stopwatchTwoStartButton.id -> {
                viewModel.secondStopwatchDigits.observe(viewLifecycleOwner) {
                    binding.stopwatchTwoSecondsCounterTextview.text = it.toString()
                    val convertedTime = secondsConvert(it)

                    binding.stopwatchTwoHourTextview.text = convertedTime.hours.toString()
                    binding.stopwatchTwoMinutesTextview.text = convertedTime.minutes.toString()
                    binding.stopwatchTwoSecondsTextview.text = convertedTime.seconds.toString()
                }
            }
            binding.stopwatchThreeStartButton.id -> {
                viewModel.thirdStopwatchDigits.observe(viewLifecycleOwner) {
                    binding.stopwatchThreeSecondsCounterTextview.text = it.toString()
                    val convertedTime = secondsConvert(it)

                    binding.stopwatchThreeHourTextview.text = convertedTime.hours.toString()
                    binding.stopwatchThreeMinutesTextview.text = convertedTime.minutes.toString()
                    binding.stopwatchThreeSecondsTextview.text = convertedTime.seconds.toString()
                }
            }
        }
    }

    private fun secondsConvert(fullSec: Int): ConvertedTime {
        val hours: Int = fullSec / 3600
        val minutes = (fullSec - (hours * 3600)) / 60
        val seconds = (fullSec - (hours * 3600)) - (minutes * 60)
        return ConvertedTime(hours, minutes, seconds)
    }

    override fun stopCounting(viewId: Int) {
        when (viewId) {
            binding.stopwatchOneStopButton.id -> {
                viewModel.stopCount(viewId)
            }
            binding.stopwatchTwoStopButton.id -> {
                viewModel.stopCount(viewId)
            }
            binding.stopwatchThreeStopButton.id -> {
                viewModel.stopCount(viewId)
            }
        }
    }

    override fun resetCounting(viewId: Int) {
        when (viewId) {
            binding.stopwatchOneResetButton.id -> {
                viewModel.resetCount(viewId)
            }
            binding.stopwatchTwoResetButton.id -> {
                viewModel.resetCount(viewId)
            }
            binding.stopwatchThreeResetButton.id -> {
                viewModel.resetCount(viewId)
            }
        }
    }

    private fun setOnButtonsClick() {
        setRunOnClick(binding.stopwatchOneStartButton)
        setRunOnClick(binding.stopwatchTwoStartButton)
        setRunOnClick(binding.stopwatchThreeStartButton)

        setStopOnClick(binding.stopwatchOneStopButton)
        setStopOnClick(binding.stopwatchTwoStopButton)
        setStopOnClick(binding.stopwatchThreeStopButton)

        setResetOnClick(binding.stopwatchOneResetButton)
        setResetOnClick(binding.stopwatchTwoResetButton)
        setResetOnClick(binding.stopwatchThreeResetButton)
    }

    private fun setRunOnClick(button: Button) {
        button.setOnClickListener {
            viewModel.runCount(it.id)
            setTimeDigits(it.id)
        }
    }

    private fun setStopOnClick(button: Button) {
        button.setOnClickListener {
            stopCounting(it.id)
        }
    }

    private fun setResetOnClick(button: Button) {
        button.setOnClickListener {
            resetCounting(it.id)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}