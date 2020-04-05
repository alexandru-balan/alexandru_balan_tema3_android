package alexandru.balan.tema3.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TodoViewModelFactory(private val application: Application, private val userId : Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Application::class.java, Int::class.java).newInstance(application, userId)
    }
}