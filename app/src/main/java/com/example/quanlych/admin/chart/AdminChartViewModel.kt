import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quanlych.data.DatabaseHelper

class AdminChartViewModel(application: Application) : AndroidViewModel(application) {
    private val _totalProductsCount = MutableLiveData<Int>()
    val totalProductsCount: LiveData<Int> get() = _totalProductsCount

    private val databaseHelper = DatabaseHelper(application)

    fun loadProductsCount(date: String?) {
        _totalProductsCount.value = databaseHelper.getTotalProductsCount(date)
    }
}