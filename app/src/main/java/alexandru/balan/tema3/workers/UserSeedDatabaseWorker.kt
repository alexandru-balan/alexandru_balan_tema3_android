package alexandru.balan.tema3.workers

import alexandru.balan.tema3.data.AppDatabase
import alexandru.balan.tema3.data.User
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * The [UserSeedDatabaseWorker] will read the content of the json files in the assets folder and load it
 * in the Room Database.
 */
class UserSeedDatabaseWorker (context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            applicationContext.assets.open("users.json").use {
                inputStream -> JsonReader(inputStream.reader()).use {
                jsonReader ->
                val userType = object : TypeToken<List<User>>(){}.type
                val users : List<User> = Gson().fromJson(jsonReader, userType)

                val database = AppDatabase.getInstance(applicationContext)
                database.userDao().insertAll(users)
                }

                Result.success()
            }
        }
        catch (exception : Exception) {
            Log.e(TAG, "Cannot seed the database", exception)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "User-Seed-Database-Worker"
    }
}