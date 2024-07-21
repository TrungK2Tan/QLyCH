package com.example.quanlych.admin.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quanlych.adapter.UserAdapter
import com.example.quanlych.data.UserRepository
import com.example.quanlych.databinding.FragmentAdminUserBinding

class AdminUserFragment : Fragment() {

    private var _binding: FragmentAdminUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val AdminuserViewModel =
            ViewModelProvider(this).get(AdminUserViewModel::class.java)

        _binding = FragmentAdminUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up RecyclerView
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val userRepository = UserRepository(requireContext())
        val userList = userRepository.getAllUsers()
        val adapter = UserAdapter(userList)
        recyclerView.adapter = adapter

        val textView = binding.textAdminuser
        AdminuserViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
