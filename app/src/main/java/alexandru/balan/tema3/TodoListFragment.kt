package alexandru.balan.tema3

import alexandru.balan.tema3.adapters.TodoListAdapter
import alexandru.balan.tema3.adapters.UserListAdapter
import alexandru.balan.tema3.factories.TodoViewModelFactory
import alexandru.balan.tema3.interfaces.ItemClickListener
import alexandru.balan.tema3.viewmodels.TodoListViewModel
import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_todo_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [TodoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoListFragment : Fragment() {

    private var userId : Int = -1
    private lateinit var todoListViewModel : TodoListViewModel
    private lateinit var adapter : TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userId = requireArguments().getInt("userId")

        val todoFactory : TodoViewModelFactory = TodoViewModelFactory(requireActivity().application, userId)
        todoListViewModel = ViewModelProvider(this, todoFactory).get(TodoListViewModel::class.java)

        adapter = activity?.let {
            TodoListAdapter(it, object : ItemClickListener<ConstraintLayout>{
                override fun onItemClick(item: ConstraintLayout, id : Int) {
                    val args = bundleOf("todoId" to id, "userId" to userId)
                    findNavController().navigate(R.id.todoOptionsFragment_flow3, args)
                }

                override fun onLongItemClick(item: ConstraintLayout, id : Int): Boolean {
                    Log.i(TAG, "Todo item long-clicked")

                    return true
                }
            })
        }!!

        todoListViewModel.todos.observe(this, Observer { todos ->
            todos?.let { adapter.setTodos(it) }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_todo_list, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.todoListRV)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }

    companion object {

        private const val TAG = "Todo-List-Fragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance() = TodoListFragment()
    }
}
