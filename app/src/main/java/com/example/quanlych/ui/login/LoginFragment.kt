package com.example.quanlych.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quanlych.R
import com.example.quanlych.data.UserRepository
import com.example.quanlych.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up click listener for login button
        binding.loginButton.setOnClickListener {
            val email = binding.username.text.toString()
            val password = binding.password.text.toString()
            val userRepository = UserRepository(requireContext())
            if (userRepository.loginUser(email, password)) {
                // Nếu quyền là "quản lý", chuyển hướng đến Home
                findNavController().navigate(R.id.action_login_to_home)
            } else {
                findNavController().navigate(R.id.action_login_to_cart)
                // Hiển thị thông báo lỗi hoặc chuyển hướng đến một trang khác nếu cần
            }
        }

        // Set up click listener for signup TextView
        binding.signupText.setOnClickListener {
            // Navigate to the Register Fragment
            findNavController().navigate(R.id.action_login_to_register)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
