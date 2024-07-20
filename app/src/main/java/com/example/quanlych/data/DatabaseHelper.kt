package com.example.quanlych.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Tạo bảng Quyen với các quyền mặc định
        db.execSQL("CREATE TABLE Quyen (MaQuyen INTEGER PRIMARY KEY AUTOINCREMENT, TenQuyen TEXT NOT NULL)")
        db.execSQL("INSERT INTO Quyen (TenQuyen) VALUES ('quản lý')")
        db.execSQL("INSERT INTO Quyen (TenQuyen) VALUES ('người dùng')")

        // Tạo bảng TaiKhoan
        db.execSQL("CREATE TABLE TaiKhoan (MaTaiKhoan INTEGER PRIMARY KEY AUTOINCREMENT, TenNguoiDung TEXT, Email TEXT UNIQUE, MatKhau TEXT)")

        // Tạo bảng ChiTietTaiKhoan
        db.execSQL("CREATE TABLE ChiTietTaiKhoan (MaTaiKhoan INTEGER, MaQuyen INTEGER, PRIMARY KEY (MaTaiKhoan, MaQuyen), FOREIGN KEY (MaTaiKhoan) REFERENCES TaiKhoan(MaTaiKhoan), FOREIGN KEY (MaQuyen) REFERENCES Quyen(MaQuyen))")
        // Tạo bảng SanPham
        db.execSQL("CREATE TABLE SanPham (MaSanPham INTEGER PRIMARY KEY AUTOINCREMENT, TenSanPham TEXT, HinhAnh INTEGER, MoTa TEXT, Gia TEXT, TrangThai INTEGER, SoLuong INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ChiTietTaiKhoan")
        db.execSQL("DROP TABLE IF EXISTS TaiKhoan")
        db.execSQL("DROP TABLE IF EXISTS Quyen")
        db.execSQL("DROP TABLE IF EXISTS SanPham")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "quanlych.db"
    }
}
