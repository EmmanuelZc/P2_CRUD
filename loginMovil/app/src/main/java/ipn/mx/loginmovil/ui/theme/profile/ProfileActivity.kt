package ipn.mx.loginmovil.ui.theme.profile

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ipn.mx.loginmovil.R
import ipn.mx.loginmovil.ui.theme.auth.AuthViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var nameView: TextView
    private lateinit var lastNameView: TextView
    private lateinit var middleNameView: TextView
    private lateinit var birthDateView: TextView
    private lateinit var usernameView: TextView

    // Usa el ViewModel
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_usuario_activity)

        nameView = findViewById(R.id.tvNombre)
        lastNameView = findViewById(R.id.tvApellidoPaterno)
        middleNameView = findViewById(R.id.tvApellidoMaterno)
        birthDateView = findViewById(R.id.tvFechaNacimiento)
        usernameView = findViewById(R.id.tvUsuario)

        // Verificar si los datos están en el Intent
        val nombre = intent.getStringExtra("nombre")
        val apellidoPaterno = intent.getStringExtra("apellidoPaterno")
        val apellidoMaterno = intent.getStringExtra("apellidoMaterno")
        val fechaNacimiento = intent.getStringExtra("fechaNacimiento")
        val username = intent.getStringExtra("username")

        // Si los datos están en el Intent, mostrarlos directamente
        if (nombre != null && apellidoPaterno != null && apellidoMaterno != null && fechaNacimiento != null && username != null) {
            nameView.text = nombre
            lastNameView.text = apellidoPaterno
            middleNameView.text = apellidoMaterno
            birthDateView.text = fechaNacimiento
            usernameView.text = username
        } else {
            // Si no están, entonces llama al ViewModel para obtener el perfil
            val userNameForProfile = username ?: ""
            viewModel.getUserProfile(userNameForProfile)

            // Observa el LiveData del ViewModel para recibir cambios en el perfil del usuario
            viewModel.userLiveData.observe(this, Observer { userProfile ->
                if (userProfile != null) {
                    // Actualiza las vistas con la información del perfil
                    nameView.text = userProfile.nombre
                    lastNameView.text = userProfile.amaterno
                    middleNameView.text = userProfile.amaterno
                    birthDateView.text = userProfile.cumple
                    usernameView.text = userProfile.username
                } else {
                    Toast.makeText(this, "Error al cargar el perfil", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Observa si hay errores
        viewModel.errorMessage.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
