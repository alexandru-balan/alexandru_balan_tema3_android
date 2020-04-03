package alexandru.balan.tema3.adapters

import alexandru.balan.tema3.R
import alexandru.balan.tema3.data.User
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_layout.view.*

class UserListAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>(){

    private val inflater : LayoutInflater = LayoutInflater.from(context)
    private var users : List<User> = emptyList()

    inner class UserViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        val userItemView: ConstraintLayout = view.findViewById(R.id.user_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = inflater.inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = users[position]
        holder.userItemView.user_fullName.text = currentUser.name
        holder.userItemView.user_username.text = currentUser.username
        holder.userItemView.user_email.text = context.getString(R.string.emailMutedDetail, currentUser.email)
    }

    internal fun setUsers (users : List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return users.size
    }
}