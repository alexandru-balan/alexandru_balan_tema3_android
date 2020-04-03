package alexandru.balan.tema3.data

import alexandru.balan.tema3.workers.TodoSeedDatabaseWorker
import alexandru.balan.tema3.workers.UserSeedDatabaseWorker
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

/**
 * The [AppDatabase] class has the role of instantiating the Room Database once, this instance being
 * the one that will be used throughout the application. When the instance is created, the database
 * will be seeded by the [SeedDatabaseWorker] with information from the json files in assets folder.
 */
@Database(entities = [User::class, Todo::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun todoDao() : TodoDao

    companion object {

        // Singleton instantiation
        @Volatile private var instance : AppDatabase? = null

        fun getInstance(context : Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it}
            }
        }

        private fun buildDatabase (context: Context) : AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "Tema3_Database")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val userRequest = OneTimeWorkRequestBuilder<UserSeedDatabaseWorker>().build()
                        val todoRequest = OneTimeWorkRequestBuilder<TodoSeedDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(userRequest)
                        WorkManager.getInstance(context).enqueue(todoRequest)
                    }
                }).fallbackToDestructiveMigration().build()
        }
    }
}