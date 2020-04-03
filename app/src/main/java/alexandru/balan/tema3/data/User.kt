package alexandru.balan.tema3.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The [User] class represents one of the 10 users to be displayed in the main recyclerview.
 * This class has a relationship of type one-to-many with the class [Todo].
 * The relationship is described to the room database in the [UserWithTodos] class.
 */
@Entity(tableName = "users")
data class User(@PrimaryKey val id : Int, val name : String, val username : String, val email : String)