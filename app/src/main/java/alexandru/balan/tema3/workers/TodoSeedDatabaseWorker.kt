package alexandru.balan.tema3.workers

import alexandru.balan.tema3.data.AppDatabase
import alexandru.balan.tema3.data.Todo
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.Exception

class TodoSeedDatabaseWorker (context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val todoType = object : TypeToken<List<Todo>>(){}.type
        val database = AppDatabase.getInstance(applicationContext)
        for (i in 1..10) {
            try {
                val inputStream : InputStream = applicationContext.assets.open("todos$i.json")
                val jsonReader = JsonReader(inputStream.reader())
                val todos : List<Todo> = Gson().fromJson(jsonReader, todoType)
                database.todoDao().insertAll(todos)
            }
            catch (exception : Exception) {
                Log.e(TAG, "Can't seed the database", exception)
                Result.failure()
            }
        }
        Result.success()
    }

    companion object {
        private const val TAG : String = "Todo-Seed-Database-Worker"
    }
}