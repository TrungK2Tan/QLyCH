package com.example.quanlych.data


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.quanlych.model.Account
import com.example.quanlych.model.Category
import com.example.quanlych.model.Order
import com.example.quanlych.model.Product
import java.security.MessageDigest

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quanlych.db"
        private const val DATABASE_VERSION = 3 // Incremented version to reflect schema changes


        // Table names
        private const val TABLE_SANPHAM = "SanPham"
        private const val TABLE_TAIKHOAN = "TaiKhoan"
        private const val TABLE_QUYEN = "Quyen"
        private const val TABLE_CHITIETTAIKHOAN = "ChiTietTaiKhoan"
        private const val TABLE_LOAISANPHAM = "LoaiSanPham"
        private const val TABLE_HOADON = "HoaDon"
        private const val TABLE_CHITIETHOADON = "ChiTietHoaDon"
        private const val TABLE_HINHTHUCTHANHTOAN = "HinhThucThanhToan"
        // Create table statements
        private const val CREATE_TABLE_LOAISANPHAM = """
        CREATE TABLE $TABLE_LOAISANPHAM (
            MaLoaiSanPham INTEGER PRIMARY KEY AUTOINCREMENT, 
            TenLoaiSanPham TEXT NOT NULL
        )
    """

        private const val CREATE_TABLE_SANPHAM = """
            CREATE TABLE $TABLE_SANPHAM (
                MaSanPham INTEGER PRIMARY KEY AUTOINCREMENT, 
                TenSanPham TEXT, 
                HinhAnh TEXT,  -- Ensure this column can store image paths or URLs
                MoTa TEXT, 
                Gia REAL, 
                TrangThai INTEGER, 
                SoLuong INTEGER,
                MaLoaiSanPham INTEGER,
                FOREIGN KEY (MaLoaiSanPham) REFERENCES $TABLE_LOAISANPHAM(MaLoaiSanPham)
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
        private const val CREATE_TABLE_HOADON = """
        CREATE TABLE $TABLE_HOADON (
            MaHoaDon INTEGER PRIMARY KEY AUTOINCREMENT,
            MaTaiKhoan INTEGER,
            NgayLap TEXT,
            TongTien REAL,
            TrangThai INTEGER,
            DiaChi TEXT,
            Sdt TEXT,
            MaHinhThuc INTEGER,
            FOREIGN KEY (MaTaiKhoan) REFERENCES $TABLE_TAIKHOAN(MaTaiKhoan),
            FOREIGN KEY (MaHinhThuc) REFERENCES $TABLE_HINHTHUCTHANHTOAN(MaHinhThuc)
        )
        """

        private const val CREATE_TABLE_CHITIETHOADON = """
        CREATE TABLE $TABLE_CHITIETHOADON (
            MaChiTiet INTEGER PRIMARY KEY AUTOINCREMENT,
            MaHoaDon INTEGER,
            MaSanPham INTEGER,
            SoLuong INTEGER,
            Gia REAL,
            FOREIGN KEY (MaHoaDon) REFERENCES $TABLE_HOADON(MaHoaDon),
            FOREIGN KEY (MaSanPham) REFERENCES $TABLE_SANPHAM(MaSanPham)
        )
        """

        private const val CREATE_TABLE_HINHTHUCTHANHTOAN = """
        CREATE TABLE $TABLE_HINHTHUCTHANHTOAN (
            MaHinhThuc INTEGER PRIMARY KEY AUTOINCREMENT,
            TenHinhThuc TEXT NOT NULL
        )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SANPHAM)
        db.execSQL(CREATE_TABLE_TAIKHOAN)
        db.execSQL(CREATE_TABLE_QUYEN)
        db.execSQL(CREATE_TABLE_CHITIETTAIKHOAN)
        db.execSQL(CREATE_TABLE_LOAISANPHAM)
        db.execSQL(CREATE_TABLE_HOADON)
        db.execSQL(CREATE_TABLE_CHITIETHOADON)
        db.execSQL(CREATE_TABLE_HINHTHUCTHANHTOAN)
        // Insert default roles
        db.execSQL("INSERT INTO $TABLE_QUYEN (TenQuyen) VALUES ('quản lý')")
        db.execSQL("INSERT INTO $TABLE_QUYEN (TenQuyen) VALUES ('người dùng')")

        // Insert default product categories related to cosmetics
        db.execSQL("INSERT INTO $TABLE_LOAISANPHAM (TenLoaiSanPham) VALUES ('Chăm sóc da')")
        db.execSQL("INSERT INTO $TABLE_LOAISANPHAM (TenLoaiSanPham) VALUES ('Trang điểm')")
        db.execSQL("INSERT INTO $TABLE_LOAISANPHAM (TenLoaiSanPham) VALUES ('Chăm sóc tóc')")
        // Insert payment methods
        db.execSQL("INSERT INTO $TABLE_HINHTHUCTHANHTOAN (TenHinhThuc) VALUES ('COD')")
        db.execSQL("INSERT INTO $TABLE_HINHTHUCTHANHTOAN (TenHinhThuc) VALUES ('MoMo')")
        // Insert admin account
        val adminValues = ContentValues().apply {
            put("TenNguoiDung", "admin1")
            put("Email", "admin2@gmail.com")
            put("MatKhau","123") // Hash the password
        }
        val adminId = db.insert(TABLE_TAIKHOAN, null, adminValues)

        // Insert admin role
        val adminRoleValues = ContentValues().apply {
            put("MaTaiKhoan", adminId)
            put("MaQuyen", 1) // Assuming '1' is the 'quản lý' role ID
        }
        db.insert(TABLE_CHITIETTAIKHOAN, null, adminRoleValues)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HOADON")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CHITIETHOADON")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HINHTHUCTHANHTOAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SANPHAM")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TAIKHOAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUYEN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CHITIETTAIKHOAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOAISANPHAM")

        // Create new tables
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
                val imageResource = cursor.getString(cursor.getColumnIndexOrThrow("HinhAnh"))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow("Gia"))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow("SoLuong"))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("MaLoaiSanPham"))

                val product = Product(id, name, description, imageResource, price, quantity, categoryId = categoryId)
                productList.add(product)

                Log.d("DatabaseHelper", "Product - ID: $id, Name: $name, Image URL: $imageResource")
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

    fun addProduct(product: Product) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("TenSanPham", product.name)
            put("HinhAnh", product.imageResource)
            put("MoTa", product.description)
            put("Gia", product.price)
            put("TrangThai", if (product.isSelected) 1 else 0) // Assuming 'isSelected' is used for TrangThai
            put("SoLuong", product.quantity)
            put("MaLoaiSanPham", product.categoryId)
        }

        val result = db.insert(TABLE_SANPHAM, null, values)
        Log.d("DatabaseHelper", "Product insertion result: $result")
    }

    fun getProductById(productId: Int): Product? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_SANPHAM WHERE MaSanPham = ?",
            arrayOf(productId.toString())
        )

        return if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("MaSanPham"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("TenSanPham"))
            val description = cursor.getString(cursor.getColumnIndexOrThrow("MoTa"))
            val imageResource = cursor.getString(cursor.getColumnIndexOrThrow("HinhAnh"))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow("Gia"))
            val quantity = cursor.getInt(cursor.getColumnIndexOrThrow("SoLuong"))
            val categoryId =
                cursor.getInt(cursor.getColumnIndexOrThrow("MaLoaiSanPham"))

            cursor.close()
            Product(
                id,
                name,
                description,
                imageResource,
                price,
                quantity,
                categoryId = categoryId
            )
        } else {
            cursor.close()
            null
        }
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
            put("TrangThai", if (product.isSelected) 1 else 0) // Check if this field is used correctly
            put("SoLuong", product.quantity)
            put("MaLoaiSanPham", product.categoryId)
        }

        val result = db.update(TABLE_SANPHAM, values, "MaSanPham = ?", arrayOf(product.id.toString()))
        Log.d("DatabaseHelper", "Product update result: $result")
    }


    fun addTestProduct() {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("TenSanPham", "Test Product") // Product name
            put("HinhAnh", "https://i.pinimg.com/originals/ba/0e/b8/ba0eb82dbe74fb21925083c2ea7475b4.jpg") // Placeholder image URL
            put("MoTa", "This is a test product.") // Product description
            put("Gia", 99.99) // Product price
            put("TrangThai", 1) // Default status or availability
            put("SoLuong", 10) // Product quantity
            put("MaLoaiSanPham", 1) // Category ID (default or test category ID, adjust as needed)
        }
        db.insert(TABLE_SANPHAM, null, values)
    }

    fun getProductCountsByType(): Int {
        val db = readableDatabase
        var totalCount = 0

        // Query to count the total number of products
        val query = "SELECT COUNT(*) FROM $TABLE_SANPHAM"

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            totalCount = cursor.getInt(0) // Total count of products
        }

        cursor.close()
        db.close()
        return totalCount
    }

    fun getCountOfAccountsWithRole(roleId: Int): Int {
        val db = readableDatabase
        val query =
            "SELECT COUNT(*) FROM $TABLE_TAIKHOAN WHERE MaTaiKhoan IN (SELECT MaTaiKhoan FROM $TABLE_CHITIETTAIKHOAN WHERE MaQuyen = ?)"
        val cursor = db.rawQuery(query, arrayOf(roleId.toString()))
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    fun getAllProductCategories(): List<Category> {
        val categoryList = mutableListOf<Category>()
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_LOAISANPHAM", null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("MaLoaiSanPham"))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("TenLoaiSanPham"))
                    val category = Category(id, name)
                    categoryList.add(category)
                } while (cursor.moveToNext())
            } else {
                Log.d("DatabaseHelper", "No categories found in database.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("DatabaseHelper", "Error fetching product categories", e)
        } finally {
            cursor?.close()
            db.close()
        }

        // Log the number of categories retrieved
        Log.d("DatabaseHelper", "Categories retrieved: ${categoryList.size}")

        return categoryList
    }
    fun getUserIdByEmail(email: String): Int {
        val db = readableDatabase
        val query = "SELECT MaTaiKhoan FROM $TABLE_TAIKHOAN WHERE Email = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        var userId = -1
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("MaTaiKhoan"))
        }
        cursor.close()
        return userId
    }
    fun addOrder(userId: Int, orderDate: String, totalPrice: Double, address: String, phoneNumber: String, paymentMethod: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("MaTaiKhoan", userId)
            put("NgayLap", orderDate)
            put("TongTien", totalPrice)
            put("DiaChi", address)
            put("Sdt", phoneNumber)
            put("MaHinhThuc", paymentMethod)
        }

        return db.insert(TABLE_HOADON, null, values)
    }

    fun addOrderDetail(orderId: Long, productId: Int, quantity: Int, price: Double) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("MaHoaDon", orderId)
            put("MaSanPham", productId)
            put("SoLuong", quantity)
            put("Gia", price)
        }

        db.insert(TABLE_CHITIETHOADON, null, values)
    }
    fun getOrdersByUserId(userId: Int): List<Order> {
        val orders = mutableListOf<Order>()
        val db = readableDatabase
        val query = """
        SELECT o.MaHoaDon, o.NgayLap, o.TongTien, o.TrangThai, o.DiaChi, o.Sdt, o.MaHinhThuc 
        FROM $TABLE_HOADON o 
        WHERE o.MaTaiKhoan = ?
    """
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, arrayOf(userId.toString()))

            if (cursor.moveToFirst()) {
                do {
                    val order = Order(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("MaHoaDon")),
                        userId = userId, // Assuming userId is the same as the parameter passed to this function
                        date = cursor.getString(cursor.getColumnIndexOrThrow("NgayLap")),
                        total = cursor.getDouble(cursor.getColumnIndexOrThrow("TongTien")),
                        status = cursor.getInt(cursor.getColumnIndexOrThrow("TrangThai")), // Adjusted to match the new Order class
                        address = cursor.getString(cursor.getColumnIndexOrThrow("DiaChi")),
                        phone = cursor.getString(cursor.getColumnIndexOrThrow("Sdt")),
                        paymentMethodId = cursor.getInt(cursor.getColumnIndexOrThrow("MaHinhThuc"))
                    )
                    orders.add(order)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error getting orders by user ID", e)
        } finally {
            cursor?.close()
        }

        return orders
    }
}