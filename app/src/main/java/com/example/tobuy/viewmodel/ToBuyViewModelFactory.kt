import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tobuy.repository.ToBuyRepository
import com.example.tobuy.viewmodel.ToBuyViewModel

@Suppress("UNCHECKED_CAST")
class ToBuyViewModelFactory(
    private val repository: ToBuyRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToBuyViewModel(repository) as T
    }
}

// return ToBuyViewModel(repository) as T