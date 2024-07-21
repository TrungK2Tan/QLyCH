package com.example.quanlych.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quanlych.R
import com.example.quanlych.databinding.FragmentAdminHomeBinding

class AdminHomeFragment : Fragment() {

    private var _binding: FragmentAdminHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val AdminhomeViewModel =
            ViewModelProvider(this).get(AdminHomeViewModel::class.java)

        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAdminhome
        AdminhomeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        // Set up click listener for login button
        binding.chartImage.setOnClickListener {
            // Navigate to the Home Fragment on login button click
            findNavController().navigate(R.id.action_home_to_chart)
        }
        binding.orderImage.setOnClickListener {
            // Navigate to the Home Fragment on login button click
            findNavController().navigate(R.id.action_home_to_order)
        }
        binding.userImage.setOnClickListener {
            // Navigate to the Home Fragment on login button click
            findNavController().navigate(R.id.action_home_to_user)
        }
        binding.productImage.setOnClickListener {
            // Navigate to the Home Fragment on login button click
            findNavController().navigate(R.id.action_home_to_product)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}