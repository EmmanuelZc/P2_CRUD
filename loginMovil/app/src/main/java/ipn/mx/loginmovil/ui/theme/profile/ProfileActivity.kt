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
    private lateinit var usernameView: TextView

    // Usa el ViewModel
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_usuario_activity)

        // Referencia a las vistas
        nameView = findViewById(R.id.etNombre)
        lastNameView = findViewById(R.id.etApellidoPaterno)
        usernameView = findViewById(R.id.etUsername)

        // Observa el LiveData del ViewModel para recibir cambios en el perfil del usuario
        viewModel.userLiveData.observe(this, Observer { userProfile ->
            if (userProfile != null) {
                // Actualiza las vistas con la información del perfil
                nameView.text = userProfile.nombre
                lastNameView.text = userProfile.apellidoPaterno
                usernameView.text = userProfile.username
            } else {
                Toast.makeText(this, "Error al cargar el perfil", Toast.LENGTH_SHORT).show()
            }
        })

        // Observa si hay errores
        viewModel.errorMessage.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        // Llama al método para obtener el perfil del usuario
        val username = "tu_usuario"  // Cambia esto por el nombre de usuario real
        viewModel.getUserProfile(username)
    }
}
