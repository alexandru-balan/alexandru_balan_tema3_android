package alexandru.balan.tema3.data

/**
 * The [UserRepository] is used by the outer world to interact with the users table in the Room
 * Database
 *
 * @constructor Receives the userDao from the AppDatabase and gives back an instance of the repository
 */
class UserRepository private constructor(private val userDao: UserDao){

    fun retrieveUsers() = userDao.retrieveUsers()

    fun retrieveUser(userId : Int) = userDao.retrieveUser(userId)

    /*TODO: If I have time I want to implement the ability to add new users HERE*/

    companion object {

        @Volatile private var instance : UserRepository? = null

        fun getInstance (userDao: UserDao): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository(userDao).also { instance = it }
            }
        }
    }
}