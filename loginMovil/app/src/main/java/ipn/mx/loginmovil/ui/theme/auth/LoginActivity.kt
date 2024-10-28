package ipn.mx.loginmovil.ui.theme.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import ipn.mx.loginmovil.R
import ipn.mx.loginmovil.ui.theme.admin.AdminActivity
import ipn.mx.loginmovil.ui.theme.profile.ProfileActivity

class LoginActivity : ComponentActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        // Referencias a los elementos de UI
        val usernameEditText = findViewById<EditText>(R.id.etUsername)
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val registerButton = findViewById<Button>(R.id.btnRegister)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Llamada al ViewModel para hacer el login
            viewModel.login(username, password) { success, user ->
                if (success && user != null) {
                    Toast.makeText(this, "Bienvenido, ${user.username}", Toast.LENGTH_LONG).show()

                    // Verificar el rol del usuario y redirigir
                    if (user.roles.any { it.nombre == "ROLE_ADMIN" }) {
                        // Si el usuario es administrador, iniciar AdminActivity
                        val intent = Intent(this, AdminActivity::class.java)
                        startActivity(intent)
                    } else if (user.roles.any { it.nombre == "ROLE_USER" }) {
                        // Si el usuario es un usuario normal, iniciar ProfileActivity con los datos del perfil
                        val intent = Intent(this, ProfileActivity::class.java).apply {
                            putExtra("nombre", user.nombre)
                            putExtra("apellidoPaterno", user.apaterno)
                            putExtra("apellidoMaterno", user.amaterno)
                            putExtra("fechaNacimiento", user.cumple)
                            putExtra("username", user.username)
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Usuario no válido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
