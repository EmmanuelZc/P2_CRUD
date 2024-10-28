package ipn.mx.loginmovil.data.models

data class User(
    val amaterno: String = "",
    val apaterno: String = "",
    val cumple: String = "",
    val enabled: Boolean,
    val id: Int = 0,
    val nombre: String = "",
    val password: String = "",
    val roles: List<Rol> = emptyList(),
    val username: String = ""

)

data class Rol(
    val id: Int = 0,
    val nombre: String
)


