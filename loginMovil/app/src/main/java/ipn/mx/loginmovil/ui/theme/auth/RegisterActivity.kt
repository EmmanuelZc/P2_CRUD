package ipn.mx.loginmovil.ui.theme.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ipn.mx.loginmovil.R
import ipn.mx.loginmovil.data.models.User
import ipn.mx.loginmovil.ui.theme.auth.AuthViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_activity)

        val nameField: EditText = findViewById(R.id.etNombre)
        val lastNameField: EditText = findViewById(R.id.etApellidoPaterno)
        val lastName2Field: EditText = findViewById(R.id.etApellidoMaterno)
        val birthdateField: EditText = findViewById(R.id.etFechaNacimiento)
        val usernameField: EditText = findViewById(R.id.etUsuario)
        val passwordField: EditText = findViewById(R.id.etContrasena)
        val registerButton: Button = findViewById(R.id.btnRegistrar)

        val viewModel: AuthViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        // Observa la respuesta del registro
        viewModel.userLiveData.observe(this) { user ->
            if (user != null) {
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                // Aquí puedes navegar a otra actividad, por ejemplo, LoginActivity
            }
        }

        // Observa los posibles errores
        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val user = User(
                nombre = nameField.text.toString(),
                apaterno = lastNameField.text.toString(),
                amaterno = lastName2Field.text.toString(),
                cumple = birthdateField.text.toString(),
                username = usernameField.text.toString(),
                password = passwordField.text.toString()
            )
            viewModel.registerUser(user)
        }
    }

}
