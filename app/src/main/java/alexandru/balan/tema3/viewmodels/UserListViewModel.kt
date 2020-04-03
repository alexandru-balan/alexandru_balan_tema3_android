package alexandru.balan.tema3.viewmodels

import alexandru.balan.tema3.data.AppDatabase
import alexandru.balan.tema3.data.User
import alexandru.balan.tema3.data.UserRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

/**
 * The ViewModel is the component that binds the UI to the database
 */
class UserListViewModel (application: Application) : AndroidViewModel(application) {
    private val userRepository : UserRepository =
        UserRepository.getInstance(AppDatabase.getInstance(application).userDao())
    val users : LiveData<List<User>> = userRepository.retrieveUsers()
}