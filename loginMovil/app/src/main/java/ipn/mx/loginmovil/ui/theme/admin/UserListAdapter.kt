package ipn.mx.loginmovil.ui.theme.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import ipn.mx.loginmovil.R
import ipn.mx.loginmovil.data.models.User

class UserListAdapter(private val context: Context, private val users: List<User>) : BaseAdapter() {
    override fun getCount(): Int {
        return users.size
    }

    override fun getItem(position: Int): Any {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return users[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)

        val user = getItem(position) as User
        val usernameView = view.findViewById<TextView>(R.id.item_username)
        val modifyButton = view.findViewById<Button>(R.id.btnModificarUsuario)
        val deleteButton = view.findViewById<Button>(R.id.btnEliminarUsuario)

        usernameView.text = user.username

        modifyButton.setOnClickListener {
            // Lógica para modificar el usuario
            Toast.makeText(context, "Modificar usuario: ${user.username}", Toast.LENGTH_SHORT).show()
        }

        deleteButton.setOnClickListener {
            // Lógica para eliminar el usuario
            Toast.makeText(context, "Eliminar usuario: ${user.username}", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
