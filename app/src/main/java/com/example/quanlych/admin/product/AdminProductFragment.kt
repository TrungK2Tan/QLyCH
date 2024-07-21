package com.example.quanlych.admin.product



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quanlych.databinding.FragmentAdminProductBinding

class AdminProductFragment : Fragment() {

    private var _binding: FragmentAdminProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val AdminproductViewModel =
            ViewModelProvider(this).get(AdminProductViewModel::class.java)

        _binding = FragmentAdminProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAdminproduct
        AdminproductViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}