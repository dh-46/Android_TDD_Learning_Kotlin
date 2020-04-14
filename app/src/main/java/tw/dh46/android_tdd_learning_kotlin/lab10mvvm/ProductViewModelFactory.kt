package tw.dh46.android_tdd_learning_kotlin.lab10mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.IProductRepository
import java.lang.IllegalArgumentException

/**
 * Day20_MVVM_ViewModel & LiveData
 * -------------------------------
 * 因為ProductViewModel需要在建構子中傳入IProductRepository
 * 所以我們需要建立ProductViewModelFactory類別，方便傳入。
 */
class ProductViewModelFactory(private val productRepository: IProductRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(productRepository) as T
        }
        // 非我族類
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}