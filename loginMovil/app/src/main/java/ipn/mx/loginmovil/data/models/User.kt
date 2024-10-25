package ipn.mx.loginmovil.data.models

data class User(
    val id: Int = 0,
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val fechaNacimiento: String = "",
    val username: String,
    val password: String
)
