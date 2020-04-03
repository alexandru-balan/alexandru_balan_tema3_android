package alexandru.balan.tema3.viewmodels

import alexandru.balan.tema3.data.AppDatabase
import alexandru.balan.tema3.data.Todo
import alexandru.balan.tema3.data.TodoRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

/**
 * [TodoListViewModel] is dependent on the userId since the todos are different for each user
 */
class TodoListViewModel (application: Application, userId : Int) : AndroidViewModel(application) {
    private val todoRepository : TodoRepository =
        TodoRepository.getInstance(AppDatabase.getInstance(application).todoDao())
    val todos : LiveData<List<Todo>> = todoRepository.retireveTodos(userId)
}