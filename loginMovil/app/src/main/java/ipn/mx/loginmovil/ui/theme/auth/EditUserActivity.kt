package ipn.mx.loginmovil.ui.theme.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ipn.mx.loginmovil.R
import ipn.mx.loginmovil.data.models.User
import org.mindrot.jbcrypt.BCrypt

class EditUserActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_user_activity)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        val nameField: EditText = findViewById(R.id.etNombre)
        val lastNameField: EditText = findViewById(R.id.etApellidoPaterno)
        val lastName2Field: EditText = findViewById(R.id.etApellidoMaterno)
        val birthdateField: EditText = findViewById(R.id.etFechaNacimiento)
        val usernameField: EditText = findViewById(R.id.etUsuario)
        val passwordField: EditText = findViewById(R.id.etContrasena)
        val updateButton: Button = findViewById(R.id.btnGuardarCambios)
        val backButton: Button = findViewById(R.id.btnRegresar)

        val username = intent.getStringExtra("USERNAME") ?: "defaultUsername"
        viewModel.getUserProfile(username)

        // Observa los datos del usuario y carga la información en los campos
        viewModel.userLiveData.observe(this) { user ->
            if (user != null) {
                currentUser = user
                nameField.setText(user.nombre)
                lastNameField.setText(user.apaterno)
                lastName2Field.setText(user.amaterno)
                birthdateField.setText(user.cumple)
                usernameField.setText(user.username)
            }
        }

        // Observa el estado de la actualización para mostrar mensajes al usuario
        viewModel.updateStatus.observe(this) { status ->
            if (status == 200) {
                Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_LONG).show()
                setResult(RESULT_OK, intent)
                finish() // Cierra la actividad después de la actualización exitosa
            } else {
                Toast.makeText(this, "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura el botón de actualización
        updateButton.setOnClickListener {
            val name = nameField.text.toString()
            val lastName = lastNameField.text.toString()
            val lastName2 = lastName2Field.text.toString()
            val birthdate = birthdateField.text.toString()
            val password = passwordField.text.toString()

            if (name.isNotEmpty() && lastName.isNotEmpty() && lastName2.isNotEmpty() &&
                birthdate.isNotEmpty() && username.isNotEmpty()) {
                // Encripta la nueva contraseña solo si se ingresó una nueva
                val hashedPassword = if (password.isNotEmpty()) BCrypt.hashpw(password, BCrypt.gensalt()) else currentUser.password

                // Crea un objeto actualizado de User con los nuevos datos, incluyendo roles
                val updatedUser = currentUser.copy(
                    nombre = name,
                    apaterno = lastName,
                    amaterno = lastName2,
                    cumple = birthdate,
                    password = hashedPassword,
                    enabled = currentUser.enabled,
                    roles = currentUser.roles
                )

                // Llama al ViewModel para actualizar el usuario
                viewModel.updateUser(updatedUser)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish() // Finaliza la actividad actual y regresa a la anterior
        }
    }
}
