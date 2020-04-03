package alexandru.balan.tema3

import alexandru.balan.tema3.adapters.UserListAdapter
import alexandru.balan.tema3.data.AppDatabase
import alexandru.balan.tema3.data.UserRepository
import alexandru.balan.tema3.viewmodels.UserListViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        userListViewModel = ViewModelProvider(this).get(UserListViewModel::class.java)

        adapter = activity?.let { UserListAdapter(it) }!!

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
    }
}