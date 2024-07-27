package com.example.quanlych.admin.chart
import AdminChartViewModel
import MonthPickerDialog
import YearPickerDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.quanlych.databinding.FragmentAdminChartBinding
import java.util.*

class AdminChartFragment : Fragment() {

    private var _binding: FragmentAdminChartBinding? = null
    private val binding get() = _binding!!
    private lateinit var adminChartViewModel: AdminChartViewModel

    private var currentSelectedDateType = -1 // Lưu loại ngày hiện tại để xử lý việc mở lại các dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adminChartViewModel = ViewModelProvider(this).get(AdminChartViewModel::class.java)
        _binding = FragmentAdminChartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Quan sát LiveData để cập nhật TextView khi dữ liệu thay đổi
        adminChartViewModel.totalProductsCount.observe(viewLifecycleOwner) { count ->
            binding.sumproduct.text = count.toString()
        }
        adminChartViewModel.totalCoinCount.observe(viewLifecycleOwner) { count ->
            binding.tongtien.text = count.toString()
        }

        // Thiết lập Spinner và các dialog
        setupSpinner()

        return root
    }

    private fun setupSpinner() {
        val spinner: Spinner = binding.spinnerDateType
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != currentSelectedDateType) {
                    currentSelectedDateType = position
                    when (position) {
                        0 -> showDatePicker() // Ngày
                        1 -> showMonthPicker() // Tháng
                        2 -> showYearPicker() // Năm
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Xử lý nếu không có mục nào được chọn
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.textSelectedDate.text = selectedDate
            adminChartViewModel.loadProductsCount(selectedDate)
            adminChartViewModel.loadcoinCount(selectedDate)
        }, year, month, day).show()
    }

    private fun showMonthPicker() {
        val dialog = MonthPickerDialog()
        dialog.setOnDateSetListener { selectedMonth ->
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val formattedMonth = selectedMonth.toString().padStart(2, '0')
            val selectedDate = "$formattedMonth/$year"
            binding.textSelectedDate.text = selectedDate
            adminChartViewModel.loadProductsCount(selectedDate)
            adminChartViewModel.loadcoinCount(selectedDate)
        }
        dialog.show(parentFragmentManager, "MonthPickerDialog")
    }

    private fun showYearPicker() {
        val dialog = YearPickerDialog()
        dialog.setOnDateSetListener { selectedYear ->
            binding.textSelectedDate.text = selectedYear.toString()
            adminChartViewModel.loadProductsCount(selectedYear.toString())
            adminChartViewModel.loadcoinCount(selectedYear.toString())
        }
        dialog.show(parentFragmentManager, "YearPickerDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateSelectedDate(date: String) {
        binding.textSelectedDate.text = date
        adminChartViewModel.loadProductsCount(date)
    }
}
