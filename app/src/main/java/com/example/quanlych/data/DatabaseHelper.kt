package com.example.quanlych.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.quanlych.model.Product

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quanlych.db"
        private const val DATABASE_VERSION = 2

        // Table names
        private const val TABLE_SANPHAM = "SanPham"
        private const val TABLE_TAIKHOAN = "TaiKhoan"
        private const val TABLE_QUYEN = "Quyen"
        private const val TABLE_CHITIETTAIKHOAN = "ChiTietTaiKhoan"

        // Create table statements
        private const val CREATE_TABLE_SANPHAM = """
    CREATE TABLE $TABLE_SANPHAM (
        MaSanPham INTEGER PRIMARY KEY AUTOINCREMENT, 
        TenSanPham TEXT, 
        HinhAnh INTEGER, 
        MoTa TEXT, 
        Gia REAL, 
        TrangThai INTEGER, 
        SoLuong INTEGER
    )
"""

        private const val CREATE_TABLE_TAIKHOAN = """
            CREATE TABLE $TABLE_TAIKHOAN (
                MaTaiKhoan INTEGER PRIMARY KEY AUTOINCREMENT, 
                TenNguoiDung TEXT, 
                Email TEXT UNIQUE, 
                MatKhau TEXT
            )
        """

        private const val CREATE_TABLE_QUYEN = """
            CREATE TABLE $TABLE_QUYEN (
                MaQuyen INTEGER PRIMARY KEY AUTOINCREMENT, 
                TenQuyen TEXT NOT NULL
            )
        """

        private const val CREATE_TABLE_CHITIETTAIKHOAN = """
            CREATE TABLE $TABLE_CHITIETTAIKHOAN (
                MaTaiKhoan INTEGER, 
                MaQuyen INTEGER, 
                PRIMARY KEY (MaTaiKhoan, MaQuyen), 
                FOREIGN KEY(MaTaiKhoan) REFERENCES $TABLE_TAIKHOAN(MaTaiKhoan), 
                FOREIGN KEY(MaQuyen) REFERENCES $TABLE_QUYEN(MaQuyen)
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SANPHAM)
        db.execSQL(CREATE_TABLE_TAIKHOAN)
        db.execSQL(CREATE_TABLE_QUYEN)
        db.execSQL(CREATE_TABLE_CHITIETTAIKHOAN)

        // Insert default roles
        db.execSQL("INSERT INTO $TABLE_QUYEN (TenQuyen) VALUES ('quản lý')")
        db.execSQL("INSERT INTO $TABLE_QUYEN (TenQuyen) VALUES ('người dùng')")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CHITIETTAIKHOAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TAIKHOAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUYEN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SANPHAM")
        onCreate(db)
    }

    // Method to fetch all products
    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SANPHAM", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("MaSanPham"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("TenSanPham"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("MoTa"))
                val imageResource = cursor.getInt(cursor.getColumnIndexOrThrow("HinhAnh"))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow("Gia"))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow("SoLuong"))

                val product = Product(id.toString(), name, description, imageResource, price, quantity)
                productList.add(product)

                // Log the product details
                Log.d("DatabaseHelper", "Product - ID: $id, Name: $name, Description: $description, Price: $price, Quantity: $quantity")
            } while (cursor.moveToNext())
        } else {
            Log.d("DatabaseHelper", "No products found.")
        }
        cursor.close()
        return productList
    }



    // Method to count records in a table
    fun getCount(tableName: String): Int {
        val db = readableDatabase
        val countQuery = "SELECT COUNT(*) FROM $tableName"
        val cursor = db.rawQuery(countQuery, null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    // Method to add a product
    fun addProduct(product: Product) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("TenSanPham", product.name)
            put("HinhAnh", product.imageResource)
            put("MoTa", product.description)
            put("Gia", product.price)
            put("TrangThai", 1) // Assuming default status is 1
            put("SoLuong", product.quantity)
        }
        db.insert(TABLE_SANPHAM, null, values)
    }


    // Method to delete a product
    fun deleteProduct(productId: Int) {
        val db = writableDatabase
        db.delete(TABLE_SANPHAM, "MaSanPham = ?", arrayOf(productId.toString()))
        db.close()
    }

    // Method to update a product
    fun updateProduct(product: Product) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("TenSanPham", product.name)
            put("HinhAnh", product.imageResource)
            put("MoTa", product.description)
            put("Gia", product.price)
            put("SoLuong", product.quantity)
            put("TrangThai", if (product.isSelected) 1 else 0)
        }
        db.update(TABLE_SANPHAM, values, "MaSanPham = ?", arrayOf(product.id))
    }

}
