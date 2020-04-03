package alexandru.balan.tema3.data

import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * This class has a many-to-one relationship with the [User] class.
 * @see [User] to find out more.
 */
@Entity (
    tableName = "todos",
    foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"])]
)
data class Todo (
    val userId : Int,
    val id : Int,
    val title : String,
    val completed : Boolean
)