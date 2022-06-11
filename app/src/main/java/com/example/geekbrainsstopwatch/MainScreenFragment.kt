package com.example.geekbrainsstopwatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.geekbrainsstopwatch.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

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
}