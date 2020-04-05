package alexandru.balan.tema3.adapters

import alexandru.balan.tema3.R
import alexandru.balan.tema3.data.Todo
import alexandru.balan.tema3.interfaces.ItemClickListener
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_layout.view.*

class TodoListAdapter internal constructor (
    context: Context,
    private val itemClickListener: ItemClickListener<ConstraintLayout>
) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){

    private val inflater : LayoutInflater = LayoutInflater.from(context)
    private var todos : List<Todo> = emptyList()

    inner class TodoViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val todoItemView : ConstraintLayout = view.findViewById(R.id.todo_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val todoView = inflater.inflate(R.layout.todo_layout, parent, false)
        return TodoViewHolder(todoView)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = todos[position]
        holder.todoItemView.todo_checkbox.isChecked = currentTodo.completed
        holder.todoItemView.todo_text.text = currentTodo.title

        bindListener(holder.todoItemView, itemClickListener, currentTodo.id)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    private fun bindListener(itemView : ConstraintLayout, itemClickListener: ItemClickListener<ConstraintLayout>, id: Int) {
        itemView.setOnClickListener {itemClickListener.onItemClick(itemView, id)}
    }

    internal fun setTodos(todos : List<Todo>) {
        this.todos = todos
        notifyDataSetChanged()
    }
}