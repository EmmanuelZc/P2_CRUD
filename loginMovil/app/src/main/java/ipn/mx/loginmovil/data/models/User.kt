package ipn.mx.loginmovil.data.models

data class User(
    val id: Int = 0,
    val nombre: String = "",
    val apaterno: String = "",
    val amaterno: String = "",
    val cumple: String = "",
    val username: String,
    val password: String,
    val roles: List<Rol> = emptyList()

)

data class Rol(
    val id: Int = 0,
    val nombre: String
)