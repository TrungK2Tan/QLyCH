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

        // Gán quyền "quản lý"
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
    fun loginUser(email: String, password: String): Boolean {
        val db = dbHelper.readableDatabase

        // Kiểm tra thông tin đăng nhập
        val query = "SELECT MaTaiKhoan FROM TaiKhoan WHERE Email = ? AND MatKhau = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        return if (cursor.moveToFirst()) {
            val maTaiKhoan = cursor.getLong(0)

            // Kiểm tra quyền của tài khoản
            val roleQuery = "SELECT TenQuyen FROM ChiTietTaiKhoan ct JOIN Quyen q ON ct.MaQuyen = q.MaQuyen WHERE ct.MaTaiKhoan = ?"
            val roleCursor = db.rawQuery(roleQuery, arrayOf(maTaiKhoan.toString()))

            if (roleCursor.moveToFirst()) {
                val tenQuyen = roleCursor.getString(0)
                roleCursor.close()
                if (tenQuyen == "quản lý") {
                    // Nếu quyền là "quản lý", trả về true để chuyển hướng đến trang Home
                    true
                } else {
                    // Quyền không phải "quản lý"
                    false
                }
            } else {
                // Không tìm thấy quyền
                false
            }
        } else {
            // Đăng nhập không thành công
            false
        }
    }
    // Lấy tất cả các tài khoản
    fun getAllAccounts(): List<String> {
        val db = dbHelper.readableDatabase
        val accounts = mutableListOf<String>()

        val cursor: Cursor = db.rawQuery("SELECT * FROM ChiTietTaiKhoan", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("MaTaiKhoan"))
                val idQuyen = cursor.getString(cursor.getColumnIndexOrThrow("MaQuyen"))

                accounts.add("MaTaiKhoan: $id, MaQuyen: $idQuyen")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return accounts
    }

}
