package alexandru.balan.tema3

import alexandru.balan.tema3.adapters.UserListAdapter
import alexandru.balan.tema3.data.AppDatabase
import alexandru.balan.tema3.data.UserRepository
import alexandru.balan.tema3.interfaces.ItemClickListener
import alexandru.balan.tema3.viewmodels.UserListViewModel
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [UserListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserListFragment : Fragment() {

    private lateinit var userListViewModel : UserListViewModel
    private lateinit var adapter : UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Writing to shared preferences
        val sharedPreferences : SharedPreferences? = activity?.getPreferences(Context.MODE_PRIVATE)
        val showTutorial : Boolean? = sharedPreferences?.contains("showTutorial")

        if (!showTutorial!!) {
            sharedPreferences.edit().putBoolean("showTutorial", true).apply()
        }

        userListViewModel = ViewModelProvider(this).get(UserListViewModel::class.java)

        adapter = activity?.let { UserListAdapter(it, object : ItemClickListener<ConstraintLayout> {
            override fun onItemClick(item: ConstraintLayout, id : Int) {
                Log.i(TAG, "Clicked an item")
                val args = bundleOf("userId" to id)

                findNavController().navigate(R.id.todoListFragment_flow2, args)
            }

            override fun onLongItemClick(item: ConstraintLayout, id : Int): Boolean {
                return false;
            }
        }) }!!

        userListViewModel.users.observe(this, Observer { users ->
            users?.let { adapter.setUsers(it) }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_list, container, false);

        // Create adapter and attach to recyclerview
        val recyclerView : RecyclerView = view.findViewById(R.id.userListRV)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserListFragment()
        private const val TAG = "User-List-Fragment"
    }
}
