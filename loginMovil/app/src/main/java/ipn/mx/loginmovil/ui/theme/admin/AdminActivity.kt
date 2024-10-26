package ipn.mx.loginmovil.ui.theme.admin

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import ipn.mx.loginmovil.data.models.User
import ipn.mx.loginmovil.ui.theme.auth.AuthViewModel
import ipn.mx.loginmovil.R

class AdminActivity : ComponentActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aquí puedes setear un Compose UI si prefieres usarlo
        }
        setContentView(R.layout.admin_activity)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        //val userListView: ListView = findViewById(R.id.userListView)
        val addUserButton: Button = findViewById(R.id.btnAgregarUsuario)

        viewModel.getAllUsers { success, userList ->
            if (success && userList != null) {
                // Aquí setearías los datos al adaptador de la lista
            } else {
                Toast.makeText(this, "Error al cargar la lista de usuarios", Toast.LENGTH_SHORT).show()
            }
        }

        addUserButton.setOnClickListener {
            // Lógica para añadir un nuevo usuario
        }
    }
}
