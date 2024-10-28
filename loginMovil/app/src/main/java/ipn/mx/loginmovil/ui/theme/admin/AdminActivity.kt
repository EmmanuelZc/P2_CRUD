package ipn.mx.loginmovil.ui.theme.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import ipn.mx.loginmovil.R
import ipn.mx.loginmovil.ui.theme.auth.AuthViewModel
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

        loadUserList()

        addUserButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, REGISTER_USER_REQUEST)
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
        if (requestCode == REGISTER_USER_REQUEST && resultCode == RESULT_OK) {
            loadUserList()
        }
    }

    companion object {
        const val REGISTER_USER_REQUEST = 1
    }
}
