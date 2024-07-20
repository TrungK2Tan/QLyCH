package com.example.quanlych.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quanlych.R
import com.example.quanlych.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        registerViewModel.text.observe(viewLifecycleOwner) { text ->
            binding.textRegister.text = text
        }

        // Set up click listener for signup TextView
        binding.signupText.setOnClickListener {
            // Navigate to the Register Fragment
            findNavController().navigate(R.id.action_register_to_login)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
