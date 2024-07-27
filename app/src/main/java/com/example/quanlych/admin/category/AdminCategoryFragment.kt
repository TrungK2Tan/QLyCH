package com.example.quanlych.admin.category

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlych.R
import com.example.quanlych.data.DatabaseHelper
import com.example.quanlych.model.Category
import com.example.quanlych.ui.category.CategoryAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminCategoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var editTextSearch: EditText
    private var allCategories: List<Category> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_category)
        editTextSearch = view.findViewById(R.id.editTextSearch)
        recyclerView.layoutManager = LinearLayoutManager(context)

        databaseHelper = DatabaseHelper(requireContext())
        allCategories = databaseHelper.getAllProductCategories()

        categoryAdapter = CategoryAdapter(allCategories, { category ->
            showEditCategoryDialog(category)
        }, { category ->
            showDeleteConfirmationDialog(category)
        })

        recyclerView.adapter = categoryAdapter

        editTextSearch.addTextChangedListener { text ->
            filterCategories(text.toString())
        }

        val addCategoryButton: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        addCategoryButton.setOnClickListener {
            showAddCategoryDialog()
        }
    }

    private fun filterCategories(query: String) {
        val filteredCategories = allCategories.filter { category ->
            category.TenLoaiSanPham.contains(query, ignoreCase = true)
        }
        categoryAdapter.updateCategories(filteredCategories)
    }

    private fun showAddCategoryDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_add_category, null)
        val categoryNameEditText = dialogView.findViewById<EditText>(R.id.editTextCategoryName)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Category")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val categoryName = categoryNameEditText.text.toString()
                if (categoryName.isNotBlank()) {
                    addCategory(categoryName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addCategory(name: String) {
        databaseHelper.addCategory(name)
        // Refresh the category list
        allCategories = databaseHelper.getAllProductCategories()
        filterCategories(editTextSearch.text.toString()) // Re-filter categories based on current query
    }

    private fun showEditCategoryDialog(category: Category) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_add_category, null)
        val categoryNameEditText = dialogView.findViewById<EditText>(R.id.editTextCategoryName)
        categoryNameEditText.setText(category.TenLoaiSanPham)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Category")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val updatedCategoryName = categoryNameEditText.text.toString()
                if (updatedCategoryName.isNotBlank()) {
                    updateCategory(category, updatedCategoryName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateCategory(category: Category, updatedName: String) {
        databaseHelper.updateCategory(category.MaLoaiSanPham, updatedName)
        allCategories = databaseHelper.getAllProductCategories()
        filterCategories(editTextSearch.text.toString()) // Re-filter categories based on current query
        Toast.makeText(requireContext(), "Category updated", Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteConfirmationDialog(category: Category) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Category")
            .setMessage("Are you sure you want to delete this category?")
            .setPositiveButton("Delete") { _, _ ->
                deleteCategory(category)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteCategory(category: Category) {
        databaseHelper.deleteCategory(category.MaLoaiSanPham)
        allCategories = databaseHelper.getAllProductCategories()
        filterCategories(editTextSearch.text.toString()) // Re-filter categories based on current query
        Toast.makeText(requireContext(), "Category deleted", Toast.LENGTH_SHORT).show()
    }
}
