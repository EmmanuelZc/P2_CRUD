package ipn.mx.loginmovil.data.api

import ipn.mx.loginmovil.data.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/usuarios/registro")
    fun registerUser(@Body user: User): Call<User>

    @POST("api/usuarios/login")
    fun loginUser(@Body user: User): Call<User>

    @GET("api/usuarios/perfil/{username}")
    fun getUserProfile(@Path("username") username: String): Call<User>

    // Nuevo: Obtener todos los usuarios
    @GET("api/usuarios")
    fun getAllUsers(): Call<List<User>>
}
