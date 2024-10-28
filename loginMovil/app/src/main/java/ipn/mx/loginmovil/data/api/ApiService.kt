package ipn.mx.loginmovil.data.api

import ipn.mx.loginmovil.data.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Obtener nuevo usuario
    @POST("api/auth/registro")
    fun registerUser(@Body user: User): Call<Void>

    // Autenticar usuario (modificado para usar Query)
    @POST("api/auth")
    fun loginUser(@Query("usuario") usuario: String, @Query("password") password: String):Call<User>

    // Obtener perfil del usuario
    @GET("api/auth/perfil/{username}")
    fun getUserProfile(@Path("username") username: String): Call<User>

    // Obtener todos los usuarios
    @POST("api/auth/admin")
    fun getAllUsers(): Call<List<User>>
}
