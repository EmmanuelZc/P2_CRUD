package ipn.mx.loginmovil.ui.theme.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import ipn.mx.loginmovil.R
import ipn.mx.loginmovil.data.models.User
import ipn.mx.loginmovil.ui.theme.auth.AuthViewModel
import ipn.mx.loginmovil.ui.theme.auth.EditUserActivity
import ipn.mx.loginmovil.ui.theme.auth.LoginActivity
import ipn.mx.loginmovil.ui.theme.auth.RegisterActivity

class AdminActivity : ComponentActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var userListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        userListView = findViewById(R.id.userListView)
        val addUserButton: Button = findViewById(R.id.btnAgregarUsuario)
        val logoutButton: Button = findViewById(R.id.btnCerrarSesion)

        loadUserList()

        addUserButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, REGISTER_USER_REQUEST)
        }

        userListView.setOnItemClickListener { _, _, position, _ ->
            val selectedUser = userListView.adapter.getItem(position) as User
            val intent = Intent(this, EditUserActivity::class.java).apply {
                putExtra("USERNAME", selectedUser.username)
            }
            startActivityForResult(intent, EDIT_USER_REQUEST)
        }

        logoutButton.setOnClickListener {
            // Realiza cualquier acción adicional de cierre de sesión aquí, como limpiar datos del usuario

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Finaliza la actividad actual
        }
    }

    private fun loadUserList() {
        viewModel.getAllUsers { success, userList ->
            if (success && userList != null) {
                val adapter = UserListAdapter(this, userList)
                userListView.adapter = adapter
            } else {
                Toast.makeText(this, "Error al cargar la lista de usuarios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == REGISTER_USER_REQUEST || requestCode == EDIT_USER_REQUEST) && resultCode == RESULT_OK) {
            loadUserList()
        }
    }


    companion object {
        const val REGISTER_USER_REQUEST = 1
        const val EDIT_USER_REQUEST = 2
    }
}
