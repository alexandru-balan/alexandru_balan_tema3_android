package alexandru.balan.tema3.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The [UserDao] is a data access object which defines the queries allowed to be executed on the
 * users table.
 */
@Dao
interface UserDao {

    @Query("Select * From users Order By name Asc")
    fun retrieveUsers() : LiveData<List<User>>

    @Query ("Select * From users Where id = :id")
    fun retrieveUser(id : Int) : LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users : List<User>)

    @Delete
    suspend fun delete(user: User)
}