package alexandru.balan.tema3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {

    @Query("Select * from todos Where userId = :userId")
    fun retrieveTodos(userId : Int) : LiveData<List<Todo>>

    @Insert
    suspend fun insert (todo: Todo)

    @Insert
    suspend fun insertAll(todos : List<Todo>)

    @Delete
    suspend fun delete (todo : Todo)
}