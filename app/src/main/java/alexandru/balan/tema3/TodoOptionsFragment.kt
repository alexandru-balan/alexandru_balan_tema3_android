package alexandru.balan.tema3

import alexandru.balan.tema3.broadcasters.TodoNotificationBroadcast
import alexandru.balan.tema3.data.Todo
import alexandru.balan.tema3.viewmodels.TodoListViewModel
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.CharacterPickerDialog
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_todo_options.*
import java.util.*
import kotlin.math.min

/**
 * A simple [Fragment] subclass.
 * Use the [TodoOptionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoOptionsFragment : Fragment() {

    private var todoId : Int? = null
    private var userId : Int? = null
    private lateinit var todoListViewModel : TodoListViewModel
    private var todos : List<Todo> = emptyList()

    private val calendar : Calendar = Calendar.getInstance()
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    private var month = calendar.get(Calendar.MONTH)
    private var year = calendar.get(Calendar.YEAR)
    private var minute = calendar.get(Calendar.MINUTE)
    private var hour = calendar.get(Calendar.HOUR)
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog

    private lateinit var notificationManager : NotificationManager
    private val NOTIFICATION_ID : Int = 0
    private val NOTIFICATION_CHANNEL_ID : String = "primary_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoId = arguments?.getInt("todoId")
        userId = arguments?.getInt("userId")

        todoListViewModel = activity?.application?.let { TodoListViewModel(it, userId!!) }!!

        todoListViewModel.todos.observe(this, Observer { viewModelTodos ->
            viewModelTodos.let { todos = it }
        })

        notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateText : TextView = view.findViewById(R.id.todo_date_text)
        val dateHour : TextView = view.findViewById(R.id.todo_time_text)

        /*Set click listeners*/
        todo_option_date.setOnClickListener {
            datePickerDialog = context?.let { it1 ->
                DatePickerDialog(it1, DatePickerDialog.OnDateSetListener{ date_view, year, month, dayOfMonth ->
                    dateText.text = "$dayOfMonth/${month+1}/$year"
                    this.year = year
                    this.month = month
                    this.day = dayOfMonth
                }, year,month,day)
            }!!
            datePickerDialog.show()
        }

        todo_option_time.setOnClickListener {
            timePickerDialog = context?.let { it1 ->
                TimePickerDialog(it1, TimePickerDialog.OnTimeSetListener {view, hourOfDay, minute ->
                    var displayHour : String = "$hourOfDay"
                    var displayMinute : String = "$minute"
                    if (hourOfDay < 10) {
                        displayHour = "0$hourOfDay"
                    }
                    if (minute < 10) {
                        displayMinute = "0$minute"
                    }
                    dateHour.text = "$displayHour:$displayMinute"
                    this.hour = hourOfDay
                    this.minute = minute
                },hour, minute, true)
            }!!
            timePickerDialog.show()
        }

        todo_setAlarm.setOnClickListener {
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)

            makeNotification(todos.find { todo -> todo.id == todoId!! }?.title!!, calendar.timeInMillis)
        }
    }

    private fun createNotification(contentText: String) : Notification {
        return activity?.let {
            NotificationCompat.Builder(it, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_check_box_black_24dp)
                .setContentTitle(getString(R.string.todo_alarm))
                .setContentText(contentText)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL).build()
        }!!
    }

    private fun scheduleNotification (notification: Notification, delay : Long) {
        val contentIntent = Intent(activity, TodoNotificationBroadcast::class.java)

        contentIntent.putExtra("NOTIFICATION_ID", NOTIFICATION_ID)
        contentIntent.putExtra("NOTIFICATION", notification)

        val contentPendingIntent : PendingIntent = PendingIntent.getBroadcast(
            activity,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager : AlarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, delay, contentPendingIntent)

        /*val alarmUp = (PendingIntent.getBroadcast(activity, NOTIFICATION_ID, contentIntent,
            PendingIntent.FLAG_NO_CREATE) != null)

        if(alarmUp) {
            Log.i(TAG, "Alarm is up")
        }*/
    }

    fun makeNotification (contentText: String, delay: Long) {
        val notification : Notification = createNotification(contentText)
        scheduleNotification(notification, delay)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel : NotificationChannel =
                NotificationChannel(NOTIFICATION_CHANNEL_ID, "TODO Notification", NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.enableVibration(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.description = "A todo notification"
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance() = TodoOptionsFragment()

        private const val TAG = "Todo-Options-Fragment"
    }
}
