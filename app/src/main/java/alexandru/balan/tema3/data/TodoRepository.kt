package alexandru.balan.tema3.data

/**
 * The [TodoRepository] sits between the Dao and ant the outer world, providing methods to get data
 * from the Room database.
 *
 * @constructor takes in a [TodoDao] and gives back an instance of the repository that you can use
 */
class TodoRepository private constructor(private val todoDao: TodoDao){

    fun retireveTodos (userId : Int) = todoDao.retrieveTodos(userId)

    /*TODO: If there is time I want to implement insert and delete methods*/

    companion object {

        @Volatile private var instance : TodoRepository? = null

        fun getInstance(todoDao: TodoDao) : TodoRepository {
            return instance ?: synchronized(this) {
                instance ?: TodoRepository(todoDao).also { instance = it }
            }
        }
    }
}