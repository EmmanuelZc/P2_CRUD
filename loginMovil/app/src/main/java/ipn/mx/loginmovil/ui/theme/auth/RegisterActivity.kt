package ipn.mx.loginmovil.ui.theme.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import ipn.mx.loginmovil.R
import ipn.mx.loginmovil.data.models.Rol
import ipn.mx.loginmovil.data.models.User
import org.mindrot.jbcrypt.BCrypt

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_activity)

        // Inicializa el ViewModel para manejar la lógica de registro
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        // Referencia a los campos de texto en el layout
        val nameField: EditText = findViewById(R.id.etNombre)
        val lastNameField: EditText = findViewById(R.id.etApellidoPaterno)
        val lastName2Field: EditText = findViewById(R.id.etApellidoMaterno)
        val birthdateField: EditText = findViewById(R.id.etFechaNacimiento)
        val usernameField: EditText = findViewById(R.id.etUsuario)
        val passwordField: EditText = findViewById(R.id.etContrasena)
        val registerButton: Button = findViewById(R.id.btnRegistrar)
        val backButton: Button = findViewById(R.id.btnRegresar)

        // Observa la respuesta del registro
        viewModel.registerStatus.observe(this) { status ->
            if (status == 201) {
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                setResult(RESULT_OK)
                finish() // Cierra la actividad después del registro exitoso
            } else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            }
        }

        // Observa los posibles errores
        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }

        // Configura el click listener para el botón de registro
        registerButton.setOnClickListener {
            val name = nameField.text.toString()
            val lastName = lastNameField.text.toString()
            val lastName2 = lastName2Field.text.toString()
            val birthdate = birthdateField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (validateInput(passwordField, password)) {
                // Encripta la contraseña usando BCrypt
                val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

                // Crea un objeto User con los datos ingresados y la contraseña encriptada
                val user = User(
                    id = 0, // Cambia el id si es necesario o deja en 0 para nuevo usuario
                    nombre = name,
                    apaterno = lastName,
                    amaterno = lastName2,
                    cumple = birthdate,
                    username = username,
                    password = hashedPassword,
                    enabled = true, // Establecer el estado del usuario
                    roles = listOf(Rol(id = 2, nombre = "ROLE_USER")) // Asigna el rol por defecto
                )
                // Llama al método del ViewModel para registrar el usuario
                viewModel.registerUser(user)
            }
        }

        backButton.setOnClickListener {
            finish() // Finaliza la actividad actual y regresa a la anterior
        }
    }

    private fun validateInput(passwordField: EditText, password: String): Boolean {
        var isValid = true

        // Validar longitud de contraseña
        if (password.length < 8) {
            passwordField.error = "La contraseña debe tener al menos 8 caracteres"
            passwordField.requestFocus()
            animateView(passwordField)
            isValid = false
        }

        return isValid
    }

    private fun animateView(view: EditText) {
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.errorColor))
        view.animate()
            .setDuration(100)
            .alpha(0.5f)
            .withEndAction {
                view.alpha = 1.0f
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultColor))
            }
    }
}
