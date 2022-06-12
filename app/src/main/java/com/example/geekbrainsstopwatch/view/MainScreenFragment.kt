package com.example.geekbrainsstopwatch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.geekbrainsstopwatch.databinding.FragmentMainScreenBinding
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

        binding.stopwatchOneStartButton.setOnClickListener {
            viewModel.runCount(it.id)
            setTimeDigits(it.id)
        }

        binding.stopwatchOneStopButton.setOnClickListener {
            stopCounting(it.id)
        }
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
                    }
                }

            } else {
                when (compoundButton.id) {
                    binding.stopwatchTwoEnableCheckbox.id -> {
                        binding.stopwatchTwoLayout.visibility = View.GONE
                        binding.stopwatchThreeEnableCheckbox.visibility = View.GONE
                    }
                    binding.stopwatchThreeEnableCheckbox.id -> {
                        binding.stopwatchThreeLayout.visibility = View.GONE
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
                }
            }
        }
    }

    override fun stopCounting(viewId: Int) {
        when(viewId) {
            binding.stopwatchOneStopButton.id -> {
                viewModel.stopCount(viewId)
            }
        }
    }
}