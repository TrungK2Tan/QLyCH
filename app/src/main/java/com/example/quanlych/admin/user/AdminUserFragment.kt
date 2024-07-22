package com.example.quanlych.admin.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quanlych.R
import com.example.quanlych.adapter.UserAdapter
import com.example.quanlych.data.User
import com.example.quanlych.data.UserRepository
import com.example.quanlych.databinding.FragmentAdminUserBinding

class AdminUserFragment : Fragment() {

    private var _binding: FragmentAdminUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRepository: UserRepository
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adminUserViewModel = ViewModelProvider(this).get(AdminUserViewModel::class.java)

        _binding = FragmentAdminUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up RecyclerView
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        userRepository = UserRepository(requireContext())
        val userList = userRepository.getAllUsers()
        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter

        val textView = binding.textAdminuser
        adminUserViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                val filteredList = if (query.isEmpty()) {
                    userRepository.getAllUsers()
                } else {
                    userRepository.searchUsers(query)
                }
                userAdapter.updateData(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.floatingActionButton.setOnClickListener {
            // Navigate to AddUserFragment
            findNavController().navigate(R.id.action_admin_to_adduser)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
