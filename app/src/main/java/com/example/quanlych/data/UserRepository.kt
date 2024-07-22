package com.example.quanlych.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // Đăng ký người dùng mới
    fun registerUser(name: String, email: String, password: String) {
        val db = dbHelper.writableDatabase

        // Insert into TaiKhoan
        val values = ContentValues().apply {
            put("TenNguoiDung", name)
            put("Email", email)
            put("MatKhau", password)
        }

        val newRowId = db.insert("TaiKhoan", null, values)

        // Gán quyền "người dùng"
        val query = "SELECT MaQuyen FROM Quyen WHERE TenQuyen = 'người dùng'"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            val maQuyen = cursor.getLong(0)
            val detailValues = ContentValues().apply {
                put("MaTaiKhoan", newRowId)
                put("MaQuyen", maQuyen)
            }
            db.insert("ChiTietTaiKhoan", null, detailValues)
        }
        cursor.close()
    }

    // Đăng nhập người dùng
    fun loginUser(email: String, password: String): Pair<Boolean, String?> {
        val db = dbHelper.readableDatabase

        // Kiểm tra thông tin đăng nhập
        val query = "SELECT MaTaiKhoan, TenNguoiDung FROM TaiKhoan WHERE Email = ? AND MatKhau = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        return if (cursor.moveToFirst()) {
            val maTaiKhoan = cursor.getLong(0)
            val tenNguoiDung = cursor.getString(1)

            // Kiểm tra quyền của tài khoản
            val roleQuery = "SELECT TenQuyen FROM ChiTietTaiKhoan ct JOIN Quyen q ON ct.MaQuyen = q.MaQuyen WHERE ct.MaTaiKhoan = ?"
            val roleCursor = db.rawQuery(roleQuery, arrayOf(maTaiKhoan.toString()))

            if (roleCursor.moveToFirst()) {
                val tenQuyen = roleCursor.getString(0)
                roleCursor.close()
                Pair(true, tenQuyen)
            } else {
                Pair(false, null)
            }
        } else {
            Pair(false, null)
        }
    }
    // Fetch all users from the database
    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM TaiKhoan", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("MaTaiKhoan"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("TenNguoiDung"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("Email"))
                val password = cursor.getString(cursor.getColumnIndexOrThrow("MatKhau"))
                users.add(User(id, name, email, password))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return users
    }
    // Search for users by name or email
    fun searchUsers(query: String): List<User> {
        val userList = mutableListOf<User>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM TaiKhoan WHERE TenNguoiDung LIKE ? OR Email LIKE ?",
            arrayOf("%$query%", "%$query%")
        )
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("MaTaiKhoan"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("TenNguoiDung"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("Email"))
                val password = cursor.getString(cursor.getColumnIndexOrThrow("MatKhau"))
                userList.add(User(id, name, email, password))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

}
data class User(val id: Long, val name: String, val email: String, val password: String)
