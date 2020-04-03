package alexandru.balan.tema3.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Here we have the relationship between the [User] and [Todo] classes
 * This class will be used by the Room database to retrieve the respective todos for each individual
 * user.
 */
data class UserWithTodos (
    @Embedded
    val user : User,

    @Relation(parentColumn = "id", entityColumn = "userId")
    val todos : List<Todo> = emptyList()
)