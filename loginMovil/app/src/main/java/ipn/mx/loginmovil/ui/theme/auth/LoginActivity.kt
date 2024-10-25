package ipn.mx.loginmovil.ui.theme.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import ipn.mx.loginmovil.data.models.User
import ipn.mx.loginmovil.ui.theme.auth.AuthViewModel

class LoginActivity : ComponentActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aquí puedes setear un Compose UI si prefieres usarlo
        }
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        val usernameEditText = findViewById<EditText>(R.id.etUsername)
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.login(username, password) { success, user ->
                if (success) {
                    Toast.makeText(this, "Bienvenido, ${user?.username}", Toast.LENGTH_LONG).show()
                    // Aquí puedes navegar a otra actividad, por ejemplo, ProfileActivity
                } else {
                    Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
