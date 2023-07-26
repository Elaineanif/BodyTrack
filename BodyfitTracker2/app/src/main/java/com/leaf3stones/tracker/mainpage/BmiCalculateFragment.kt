package com.leaf3stones.tracker.mainpage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leaf3stones.tracker.App
import com.leaf3stones.tracker.R
import com.leaf3stones.tracker.databinding.FragmentBmiCalculateBinding

class BmiCalculateFragment : Fragment() {

    private lateinit var binding: FragmentBmiCalculateBinding
    private lateinit var viewModel: BmiCalculateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBmiCalculateBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BmiCalculateViewModel::class.java]

        binding.personalInfoButton.setOnClickListener {
            findNavController().navigate(R.id.action_bmiCalculateFragment_to_detailFragment)
        }


        binding.heightEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // no need
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    viewModel.height = p0.toString().toFloat()
                } catch (e: Exception) {
                    viewModel.height = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // no need
            }
        })

        binding.weightEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // no need
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    viewModel.weight = p0.toString().toFloat()
                } catch (e: Exception) {
                    viewModel.weight = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // no need
            }
        })

        binding.calculateButton.setOnClickListener {
            val res: Float? = viewModel.calculateBMI()
            if (res != null) {
                binding.bmiTextview.text = String.format("%.2f", res)
            } else {
                Toast.makeText(
                    App.getAppContext(),
                    "Fill both weight and height",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}