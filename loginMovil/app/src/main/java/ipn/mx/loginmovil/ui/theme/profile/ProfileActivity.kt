package ipn.mx.loginmovil.ui.theme.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ipn.mx.loginmovil.R
import ipn.mx.loginmovil.ui.theme.auth.AuthViewModel
import ipn.mx.loginmovil.ui.theme.auth.EditUserActivity
import ipn.mx.loginmovil.ui.theme.auth.LoginActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var nameView: TextView
    private lateinit var lastNameView: TextView
    private lateinit var middleNameView: TextView
    private lateinit var birthDateView: TextView
    private lateinit var usernameView: TextView
    private lateinit var editButton: Button
    private lateinit var logoutButton: Button

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_usuario_activity)

        nameView = findViewById(R.id.tvNombre)
        lastNameView = findViewById(R.id.tvApellidoPaterno)
        middleNameView = findViewById(R.id.tvApellidoMaterno)
        birthDateView = findViewById(R.id.tvFechaNacimiento)
        usernameView = findViewById(R.id.tvUsuario)
        editButton = findViewById(R.id.btnEditar)
        logoutButton = findViewById(R.id.btnCerrarSesion)

        val nombre = intent.getStringExtra("nombre")
        val apellidoPaterno = intent.getStringExtra("apellidoPaterno")
        val apellidoMaterno = intent.getStringExtra("apellidoMaterno")
        val fechaNacimiento = intent.getStringExtra("fechaNacimiento")
        val username = intent.getStringExtra("username")

        if (nombre != null && apellidoPaterno != null && apellidoMaterno != null && fechaNacimiento != null && username != null) {
            nameView.text = nombre
            lastNameView.text = apellidoPaterno
            middleNameView.text = apellidoMaterno
            birthDateView.text = fechaNacimiento
            usernameView.text = username
        } else {
            val userNameForProfile = username ?: ""
            viewModel.getUserProfile(userNameForProfile)
            viewModel.userLiveData.observe(this, Observer { userProfile ->
                if (userProfile != null) {
                    nameView.text = userProfile.nombre
                    lastNameView.text = userProfile.apaterno
                    middleNameView.text = userProfile.amaterno
                    birthDateView.text = userProfile.cumple
                    usernameView.text = userProfile.username
                } else {
                    Toast.makeText(this, "Error al cargar el perfil", Toast.LENGTH_SHORT).show()
                }
            })
        }

        viewModel.errorMessage.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        val editUserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Recarga el perfil del usuario después de la actualización
                val userNameForProfile = usernameView.text.toString()
                viewModel.getUserProfile(userNameForProfile)
            }
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditUserActivity::class.java).apply {
                putExtra("USERNAME", username)
            }
            editUserLauncher.launch(intent)
        }

        logoutButton.setOnClickListener {
            // Realiza cualquier acción adicional de cierre de sesión aquí, como limpiar datos del usuario

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Finaliza la actividad actual
        }
    }

    companion object {
        const val EDIT_USER_REQUEST = 2
    }
}
